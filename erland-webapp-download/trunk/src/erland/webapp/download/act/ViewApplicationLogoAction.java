package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.download.fb.ApplicationFB;
import erland.webapp.download.fb.ApplicationIdFB;
import erland.webapp.download.entity.Application;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.image.ImageWriteHelper;

import java.io.PipedOutputStream;
import java.io.FileInputStream;

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

public class ViewApplicationLogoAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ApplicationIdFB fb = (ApplicationIdFB) actionForm;

        Application entity = (Application) WebAppEnvironment.getInstance().getEntityFactory().create("download-application");

        entity.setDirectory(WebAppEnvironment.getInstance().getConfigurableResources().getParameter("basedirectory"));
        entity.setId(fb.getName());

        entity = (Application) WebAppEnvironment.getInstance().getEntityStorageFactory().getStorage("download-application").load(entity);
        if(entity!=null) {
            if(entity.getLogo()!=null) {
                String filename = entity.getDirectory()+entity.getLogo();
                ImageWriteHelper.writeImage(WebAppEnvironment.getInstance(),filename,httpServletResponse.getOutputStream());
                System.out.println("Loading image "+filename);
            }
            return null;
        }else {
            return actionMapping.findForward("failure");
        }
    }
}