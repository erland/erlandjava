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
import erland.webapp.download.entity.ApplicationVersion;
import erland.webapp.download.entity.Mail;
import erland.webapp.download.entity.Application;
import erland.webapp.common.act.WebAppEnvironmentPlugin;

import java.io.*;
import java.util.Date;

import erland.util.StringUtil;


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

public class DownloadApplication extends Action {
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
                if(StringUtil.asNull(fb.getEmail())!=null && StringUtil.asNull(entity.getMailingList())!=null) {
                    Mail mailEntity = (Mail) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("download-mail");
                    mailEntity.setApplication(entity.getApplicationName());
                    mailEntity.setEmail(fb.getEmail());
                    Mail storedMailEntity = (Mail) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-mail").load(mailEntity);
                    if(storedMailEntity==null) {
                        mailEntity.setDate(new Date());
                        mailEntity.setLastDate(mailEntity.getDate());
                        WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-mail").store(mailEntity);
                    }else {
                        storedMailEntity.setLastDate(new Date());
                        WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-mail").store(storedMailEntity);
                    }

                    Cookie cookie = new Cookie("erland-webapp-download-email",fb.getEmail());
                    cookie.setMaxAge(3600*24*365); // Lets keep the cookie for a year
                    httpServletResponse.addCookie(cookie);
                }
                String filename = entity.getDirectory()+entity.getName();
                InputStream input = new BufferedInputStream(new FileInputStream(filename));
                if(filename.endsWith(".zip")) {
                    httpServletResponse.setContentType("application/zip");
                }else {
                    httpServletResponse.setContentType("application/octet-stream");
                }
                httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" +entity.getName()+ "\";");
                write(input,httpServletResponse.getOutputStream());
                LOG.debug("Loading application "+filename);
            }
            return null;
        }else {
            return actionMapping.findForward("failure");
        }
    }
    private void write(InputStream input, OutputStream output) throws IOException {
        byte[] data = new byte[100000];
        while (true) {
            int length = input.read(data);
            if (length < 0) {
                return;
            }
            output.write(data, 0, length);
        }
    }
}