package erland.webapp.common.act;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import erland.util.ParameterStorageString;
import erland.util.StreamStorage;
import erland.webapp.common.html.*;

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

public class HtmlEncoderPlugin implements PlugIn {
    private Class[] replaceRoutines = null;
    public void destroy() {
        //Do nothing
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        if(replaceRoutines!=null) {
            for (int i = 0; i < replaceRoutines.length; i++) {
                Class routine = replaceRoutines[i];
                try {
                    Object obj = routine.newInstance();
                    if(obj instanceof StringReplaceInterface) {
                        HTMLEncoder.addReplaceRoutine((StringReplaceInterface)obj);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
    }

    public void setReplaceRoutines(String routines) {
        List elements = new ArrayList();
        StringTokenizer tokens = new StringTokenizer(routines,",");
        while(tokens.hasMoreElements()) {
            String token = (String) tokens.nextElement();
            try {
                elements.add(getClass().getClassLoader().loadClass(token.trim()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        replaceRoutines = (Class[]) elements.toArray(new Class[0]);
    }

}