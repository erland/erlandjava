package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.download.fb.ApplicationVersionFB;
import erland.webapp.download.fb.ApplicationIdFB;
import erland.webapp.download.entity.ApplicationVersion;
import java.util.Collection;
import java.util.ArrayList;

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

public class SearchApplicationVersionsAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ApplicationIdFB fb = (ApplicationIdFB) actionForm;
        QueryFilter filter = new QueryFilter("allforapplication");
        String mainDir = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory");
        filter.setAttribute("directory",mainDir+"/"+fb.getName());
        filter.setAttribute("extensions", ".zip,.exe");
        EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-applicationversion").search(filter);
        if(entities!=null) {
            Collection applicationVersions = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                ApplicationVersion entity = (ApplicationVersion) entities[i];
                ApplicationVersionFB version = new ApplicationVersionFB();

                version.setName(fb.getName());
                version.setFilename(entity.getName());
                version.setDate(entity.getDate());
                version.setVersion(entity.getVersion());
                version.setDescription(entity.getDescription());
                applicationVersions.add(version);
            }
            httpServletRequest.setAttribute("applicationversionsPB",applicationVersions);
            return actionMapping.findForward("success");
        }else {
            return actionMapping.findForward("failure");
        }
    }
}