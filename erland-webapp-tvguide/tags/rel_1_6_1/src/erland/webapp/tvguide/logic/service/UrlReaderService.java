package erland.webapp.tvguide.logic.service;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReaderService implements ServiceInterface {
    public String execute(ParameterValueStorageInterface parameters) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(parameters)));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    protected InputStream getInputStream(ParameterValueStorageInterface parameters) throws java.io.IOException {
        return new URL(parameters.getParameter("url")).openStream();
    }
}