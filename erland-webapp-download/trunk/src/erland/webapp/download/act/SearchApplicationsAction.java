package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import erland.webapp.download.fb.ApplicationFB;
import erland.webapp.download.entity.Application;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

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

public class SearchApplicationsAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        QueryFilter filter = new QueryFilter("all");
        filter.setAttribute("directory",WebAppEnvironment.getInstance().getConfigurableResources().getParameter("basedirectory"));
        filter.setAttribute("directoriesonly",Boolean.TRUE);
        EntityInterface[] entities = WebAppEnvironment.getInstance().getEntityStorageFactory().getStorage("download-application").search(filter);
        if(entities!=null) {
            Collection applications = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                ApplicationFB application = new ApplicationFB();
                Application entity = (Application) entities[i];

                application.setTitle(entity.getTitle()!=null?entity.getTitle():entity.getName());
                application.setName(entity.getName());
                application.setDescription(entity.getDescription());
                application.setLogo(entity.getLogo());

                applications.add(application);
            }
            httpServletRequest.getSession().setAttribute("applicationsPB",applications);
            return actionMapping.findForward("success");
        }else {
            return actionMapping.findForward("failure");
        }
    }
}