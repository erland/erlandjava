package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.download.fb.ApplicationFileFB;
import erland.webapp.download.entity.ApplicationVersion;
import erland.webapp.common.act.WebAppEnvironmentPlugin;

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

public class DownloadApplication extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ApplicationFileFB fb = (ApplicationFileFB) actionForm;

        String directory = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory");
        ApplicationVersion entity = (ApplicationVersion) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("download-applicationversion");
        entity.setDirectory(directory);
        entity.setDirectory(entity.getDirectory()+fb.getName());
        entity.setId(fb.getFilename());

        entity = (ApplicationVersion) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-applicationversion").load(entity);

        if(entity!=null) {
            if(entity.getName()!=null) {
                String filename = entity.getDirectory()+entity.getName();
                InputStream input = new BufferedInputStream(new FileInputStream(filename));
                httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" +entity.getName()+ "\";");
                write(input,httpServletResponse.getOutputStream());
                System.out.println("Loading application "+filename);
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