package erland.webapp.datacollection.act.entry;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.datacollection.fb.entry.EntryPB;
import erland.webapp.datacollection.fb.entry.SearchEntryFB;
import erland.webapp.datacollection.fb.collection.CollectionPB;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.collection.Collection;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchEntriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchEntryFB fb = (SearchEntryFB) form;
        QueryFilter filter = null;
        if(StringUtil.asNull(fb.getApplication())==null) {
            filter = new QueryFilter("allofficial");
        }else {
            filter = new QueryFilter("allofficialforapplication");
            filter.setAttribute("application",fb.getApplication());
        }
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").search(filter);
        CollectionPB[] pb = new CollectionPB[entities.length];
        ActionForward viewLink = mapping.findForward("entry-view-link");
        Map parameters = new HashMap();
        if(StringUtil.asNull(request.getServerName())!=null) {
            parameters.put("hostname",request.getServerName());
            if(request.getServerPort()!=80) {
                parameters.put("port",new Integer(request.getServerPort()));
            }
        }
        parameters.put("contextpath",request.getContextPath());

        for (int i = 0; i < entities.length; i++) {
            Collection collection = (Collection) entities[i];
            pb[i] = new CollectionPB();
            PropertyUtils.copyProperties(pb[i],collection);
            QueryFilter entryFilter = new QueryFilter("allforcollection");
            if(fb.getVersion()!=null) {
                entryFilter = new QueryFilter("allforcollectionandversion");
                entryFilter.setAttribute("version",fb.getVersion());
            }
            entryFilter.setAttribute("collection",collection.getId());

            EntityInterface[] entryEntities = getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").search(entryFilter);
            EntryPB[] entryPBs = new EntryPB[entryEntities.length];
            for (int j = 0; j < entryEntities.length; j++) {
                entryPBs[j] = new EntryPB();
                Entry entry = (Entry) entryEntities[j];
                PropertyUtils.copyProperties(entryPBs[j], entry);
                parameters.put("entry",entryPBs[j].getId());
                if(viewLink!=null) {
                    entryPBs[j].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewLink.getPath(),parameters));
                }
            }
            pb[i].setEntries(entryPBs);
        }
        request.setAttribute("collectionsPB", pb);
    }
}
