package erland.webapp.homepage.logic.service;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.util.*;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.ServletParameterHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

public class TemplateUrlReaderService implements ServiceInterface {
    public String execute(ParameterValueStorageInterface parameters) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(parameters)));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            int pos = sb.indexOf("[service=");
            while(pos>=0) {
                int endPos = sb.indexOf("]",pos);
                if(endPos>=0) {
                    String service = sb.substring(pos+9,endPos);
                    int parameterStart = service.indexOf(':');
                    String data=null;
                    if(parameterStart>=0) {
                        String parameterString = service.substring(parameterStart+1);
                        parameterString = ServletParameterHelper.replaceDynamicParameters(parameterString,new ObjectStorageParameterStorage(parameters));
                        data = ServiceHelper.getServiceData(WebAppEnvironmentPlugin.getEnvironment(),service.substring(0,parameterStart),parameterString);
                    }else {
                        data = ServiceHelper.getServiceData(WebAppEnvironmentPlugin.getEnvironment(),service,"");
                    }
                    sb.replace(pos,endPos+1,StringUtil.asEmpty(data));
                    pos = sb.indexOf("[service=");
                }else {
                    pos=-1;
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    protected InputStream getInputStream(ParameterValueStorageInterface parameters) throws IOException {
        return new URL(parameters.getParameter("url")).openStream();
    }
}