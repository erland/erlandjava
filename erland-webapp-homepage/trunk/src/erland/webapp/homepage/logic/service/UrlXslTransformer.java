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

import erland.util.ParameterValueStorageInterface;
import erland.util.StringUtil;
import erland.util.ObjectStorageParameterStorage;
import erland.webapp.common.ServletParameterHelper;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class UrlXslTransformer implements TransformerInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(UrlXslTransformer.class);

    public String transform(String data, ParameterValueStorageInterface parameters) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Reader input = new InputStreamReader(getInputStream(parameters));
            Reader inputReader = null;
            if(StringUtil.asBoolean(parameters.getParameter("replacedynamicparameters"),Boolean.FALSE).booleanValue()) {
                String inputString = loadStream(input);
                inputString = ServletParameterHelper.replaceDynamicParameters(inputString,new ObjectStorageParameterStorage(parameters));
                inputReader = new StringReader(inputString);
            }else {
                inputReader = input;
            }
            Transformer transformer = factory.newTransformer(new StreamSource(inputReader));
            StringWriter result = new StringWriter();
            transformer.transform(new StreamSource(new StringReader(data)),new StreamResult(result));
            return result.toString();
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    protected InputStream getInputStream(ParameterValueStorageInterface parameters) throws java.io.IOException {
        return new URL(parameters.getParameter("url")).openStream();
    }
    protected String loadStream(Reader input) {
        StringBuffer sb = new StringBuffer(100000);
        try {
            BufferedReader bufferedInput = new BufferedReader(input);
            int character = bufferedInput.read();
            while(character>=0) {
                sb.append((char)character);
                character = bufferedInput.read();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return sb.toString();
    }
}