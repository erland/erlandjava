package erland.webapp.common;
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

import erland.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PathForwardServlet extends HttpServlet {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(PathForwardServlet.class);
    private ParameterValueStorageExInterface resources=null;
    private StorageInterface storage;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    protected StorageInterface getStorage() {
        if(storage==null) {
            LOG.info("Loading configuration from: "+getServletContext().getRealPath("/")+"WEB-INF/resources.xml");
            storage = new FileStorage(getServletContext().getRealPath("/")+"WEB-INF/resources.xml");
        }
        return storage;
    }
    public ParameterValueStorageExInterface getResources() {
        if(resources==null) {
            resources = new ParameterStorageChild("resources.",new ParameterStorageTree(getStorage(),new JarFileStorageFactory()));
        }
        return resources;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean bEnd = false;
        int i = 1;
        while(!bEnd) {
            String pattern = getResources().getParameter("pathforwards."+i+".pattern");
            LOG.debug("Checking path forward "+i+" "+pattern);
            if(pattern!=null) {
                String pathString = request.getPathInfo();
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(pathString);
                if(m.matches()) {
                    LOG.debug("Checking path forward "+i+" matched");
                    String path = getResources().getParameter("pathforwards."+i+".path");

                    if(m.groupCount()>0) {
                        path += "?" + createQueryString(m.group(1));
                    }
                    RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                    LOG.debug("Forwarding to:"+path);
                    dispatcher.forward(request,response);
                    return;
                }
            }else {
                bEnd = true;
            }
            i++;
        }
        LOG.info("No path forward pattern matched request");
    }
    private String createQueryString(String path) {
        StringTokenizer tokens = new StringTokenizer(path,"/");
        StringBuffer sb = new StringBuffer();
        boolean parameter = true;
        while(tokens.hasMoreTokens()) {
            String element = tokens.nextToken();
            if(parameter) {
                if(sb.length()>0) {
                    sb.append("&");
                }
                sb.append(element);
                parameter = false;
            }else {
                sb.append("="+element);
                parameter = true;
            }
        }
        return sb.toString();
    }
}
