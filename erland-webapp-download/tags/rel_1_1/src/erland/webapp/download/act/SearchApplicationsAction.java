package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import erland.webapp.download.fb.ApplicationPB;
import erland.webapp.download.entity.Application;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.WebAppEnvironmentPlugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Arrays;

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
        filter.setAttribute("directory",WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory"));
        filter.setAttribute("directoriesonly",Boolean.TRUE);
        filter.setAttribute("language",httpServletRequest.getLocale().getLanguage());
        EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-application").search(filter);
        if(entities!=null) {
            Collection applications = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                ApplicationPB application = new ApplicationPB();
                Application entity = (Application) entities[i];

                application.setTitle(entity.getTitle()!=null?entity.getTitle():entity.getName());
                application.setCategory(entity.getCategory());
                application.setName(entity.getName());
                application.setDescription(entity.getDescription());
                application.setLogo(entity.getLogo());

                applications.add(application);
            }
            ApplicationPB[] pb = (ApplicationPB[]) applications.toArray(new ApplicationPB[0]);
            Arrays.sort(pb,new Comparator() {
                public int compare(Object o1, Object o2) {
                    String category1 = ((ApplicationPB)o1).getCategory()!=null?((ApplicationPB)o1).getCategory():"";
                    String category2 = ((ApplicationPB)o2).getCategory()!=null?((ApplicationPB)o2).getCategory():"";
                    int order = category1.compareTo(category2);
                    if(order!=0) {
                        return order;
                    }else {
                        return ((ApplicationPB)o1).getTitle().compareTo(((ApplicationPB)o2).getTitle());
                    }
                }
            });
            httpServletRequest.getSession().setAttribute("applicationsPB",pb);
            return actionMapping.findForward("success");
        }else {
            return actionMapping.findForward("failure");
        }
    }
}