package erland.webapp.download.ws;

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.StringUtil;

import java.net.URL;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.apache.axis.MessageContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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

public class DownloadSoapBindingImpl implements erland.webapp.download.ws.Download{
    public java.lang.String getAllApplicationVersions(java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "?language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getallapplicationversions"+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

}
