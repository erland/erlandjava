package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import erland.webapp.download.fb.ApplicationFileFB;
import erland.webapp.download.fb.ApplicationFilePB;
import erland.webapp.download.entity.ApplicationVersion;
import erland.webapp.download.entity.Application;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.StringUtil;
import erland.webapp.common.ServletParameterHelper;

import java.util.*;
import java.io.*;

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

public class RequestApplication extends Action {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(DownloadApplication.class);

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ApplicationFileFB fb = (ApplicationFileFB) actionForm;

        String language = fb.getLanguage();
        if(StringUtil.asNull(language)==null) {
            language = httpServletRequest.getLocale().getLanguage();
        }

        String directory = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory");
        ApplicationVersion entity = (ApplicationVersion) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("download-applicationversion");
        entity.setDirectory(directory);
        entity.setDirectory(entity.getDirectory()+fb.getName());
        entity.setId(fb.getFilename());
        entity.setLanguage(language);

        entity = (ApplicationVersion) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-applicationversion").load(entity);

        if(entity!=null) {
            if(entity.getName()!=null) {
                ActionForward downloadForward = actionMapping.findForward("download-link");

                Map parameters = new HashMap();
                if(StringUtil.asNull(httpServletRequest.getServerName())!=null) {
                    parameters.put("hostname",httpServletRequest.getServerName());
                    if(httpServletRequest.getServerPort()!=80) {
                        parameters.put("port",new Integer(httpServletRequest.getServerPort()));
                    }
                }
                parameters.put("contextpath",httpServletRequest.getContextPath());
                parameters.put("application",entity.getApplicationName());
                parameters.put("filename",entity.getName());

                String url = null;
                if(downloadForward!=null) {
                    url = ServletParameterHelper.replaceDynamicParameters(downloadForward.getPath(),parameters);
                }

                ApplicationFilePB pb = new ApplicationFilePB(fb.getLanguage(),entity.getName(),entity.getType(),entity.getName(),url);
                pb.setRequestMessage(entity.getRequestMessage());
                pb.setMailingList(entity.getMailingList());
                pb.setApplicationTitle(entity.getApplicationTitle());


                Cookie[] cookies = httpServletRequest.getCookies();
                if(cookies!=null) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = cookies[i];
                        if(cookie.getName().equals("erland-webapp-download-email")) {
                            fb.setEmail(cookie.getValue());
                        }
                    }
                }
                
                httpServletRequest.setAttribute("applicationFilePB",pb);
                return actionMapping.findForward("success");
            }
        }
        return actionMapping.findForward("failure");
    }
}