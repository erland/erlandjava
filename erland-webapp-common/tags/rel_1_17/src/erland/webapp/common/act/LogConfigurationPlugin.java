package erland.webapp.common.act;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import erland.util.ParameterStorageString;
import erland.util.StreamStorage;
import erland.util.Log;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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

/**
 * @deprecated You should use commons-logging classes instead
 */
public class LogConfigurationPlugin implements PlugIn {
    private String logConfigurationFile = "debug_log.xml";
    public void destroy() {
        //Do nothing
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        InputStream input = getClass().getResourceAsStream("/"+logConfigurationFile);
        if(input==null) {
            try {
                input = new FileInputStream(logConfigurationFile);
            } catch (FileNotFoundException e) {
                // Do Nothing
            }
        }
        if(input!=null) {
            System.out.println("Loading log configuration from: "+logConfigurationFile);
            Log.setLogConfig(new ParameterStorageString(new StreamStorage(input,null),null,"log"));
        }else {
            System.out.println("Failed loading log configuration from: "+logConfigurationFile);
        }
    }

    public void setLogConfigurationFile(String logConfigurationFile) {
        this.logConfigurationFile = logConfigurationFile;
    }
}