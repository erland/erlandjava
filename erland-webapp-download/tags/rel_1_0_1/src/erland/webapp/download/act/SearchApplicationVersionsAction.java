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
import erland.webapp.download.fb.ApplicationFileFB;
import erland.webapp.download.entity.ApplicationVersion;

import java.util.*;

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
        QueryFilter filter = getFilter(actionForm);
        EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("download-applicationversion").search(filter);
        if(entities!=null) {
            Map applicationVersions = new HashMap();
            for (int i = 0; i < entities.length; i++) {
                ApplicationVersion entity = (ApplicationVersion) entities[i];
                if(applicationVersions.containsKey(entity.getApplicationName()+"-"+entity.getVersion())) {
                    ApplicationVersionFB existingEntry = (ApplicationVersionFB) applicationVersions.get(entity.getApplicationName()+"-"+entity.getVersion());
                    ApplicationFileFB[] existingFiles = existingEntry.getFiles();
                    ApplicationFileFB[] newFiles = new ApplicationFileFB[existingFiles.length+1];
                    boolean bInserted = false;
                    for (int j = 0,k=0; j < newFiles.length; j++) {
                        if(!bInserted && (entity.getType()==null || entity.getType().length()==0 || existingFiles[k].getType().compareTo(entity.getType())>0)) {
                            newFiles[j] = new ApplicationFileFB(entity.getName(),entity.getType(),entity.getName());
                            bInserted = true;
                        }else {
                            newFiles[j] = existingFiles[k++];
                        }
                    }
                    existingEntry.setFiles(newFiles);
                    if(entity.getDescription()!=null && entity.getDescription().length()>0) {
                        if(existingEntry.getDescription()!=null && entity.getDescription().length()>0) {
                            existingEntry.setDescription(existingEntry.getDescription()+'\n');
                        }else {
                            existingEntry.setDescription("");
                        }
                        existingEntry.setDescription(existingEntry.getDescription()+entity.getDescription());
                    }
                }else {
                    ApplicationVersionFB version = new ApplicationVersionFB();
                    version.setName(entity.getApplicationName());
                    version.setTitle(entity.getApplicationTitle());
                    version.setDate(entity.getDate());
                    version.setVersion(entity.getVersion());
                    version.setDescription(entity.getDescription());
                    version.setFiles(new ApplicationFileFB[] {new ApplicationFileFB(entity.getName(),entity.getType(),entity.getName())});

                    applicationVersions.put(entity.getApplicationName()+"-"+entity.getVersion(),version);
                }
            }
            ApplicationVersionFB[] versions = (ApplicationVersionFB[]) applicationVersions.values().toArray(new ApplicationVersionFB[0]);
            Arrays.sort(versions, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return -((ApplicationVersionFB)o1).getDate().compareTo(((ApplicationVersionFB)o2).getDate());
                }
            });
            httpServletRequest.setAttribute("applicationversionsPB",versions);
            return actionMapping.findForward("success");
        }else {
            return actionMapping.findForward("failure");
        }
    }

    protected QueryFilter getFilter(ActionForm actionForm) {
        ApplicationIdFB fb = (ApplicationIdFB) actionForm;
        QueryFilter filter = new QueryFilter("allforapplication");
        String mainDir = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory");
        filter.setAttribute("directory",mainDir+"/"+fb.getName());
        filter.setAttribute("extensions", ".zip,.exe");
        return filter;
    }
}