package erland.webapp.common;
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

public class ForwardServlet extends HttpServlet {
    private ParameterValueStorageExInterface resources=null;
    private StorageInterface storage;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    protected StorageInterface getStorage() {
        if(storage==null) {
            Log.println(this,"Loading configuration from: "+getServletContext().getRealPath("/")+"WEB-INF/resources.xml");
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
            String pattern = getResources().getParameter("forwards."+i+".pattern");
            Log.println(this,"Checking forward "+i+" "+pattern);
            String queryString = request.getQueryString();
            if(pattern!=null && Pattern.matches(pattern,request.getPathInfo()+(queryString!=null?"?"+queryString:""))) {
                Log.println(this,"Checking forward "+i+" matched");
                String path = getResources().getParameter("forwards."+i+".path");
                path = ServletParameterHelper.replaceDynamicParameters(path,request.getParameterMap());
                RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                Log.println(this,"Forwarding to:"+path);
                dispatcher.forward(request,response);
                return;
            }
            if(pattern==null) {
                bEnd = true;
            }
            i++;
        }
        Log.println(this,"No forward pattern matched request");
    }
}
