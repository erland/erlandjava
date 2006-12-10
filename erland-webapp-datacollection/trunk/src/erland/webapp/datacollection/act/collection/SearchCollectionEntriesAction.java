package erland.webapp.datacollection.act.collection;

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

import erland.webapp.datacollection.entity.collection.Collection;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.fb.collection.SelectCollectionFB;
import erland.webapp.datacollection.fb.collection.CollectionPB;
import erland.webapp.datacollection.fb.entry.EntryPB;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class SearchCollectionEntriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectCollectionFB fb = (SelectCollectionFB) form;
        Collection collection = null;
        if (fb.getCollection() == null) {
            saveErrors(request, Arrays.asList(new String[]{"datacollection.collection.view.collection-dont-exist"}));
            return;
        }
        Collection template = (Collection) getEnvironment().getEntityFactory().create("datacollection-collection");
        template.setId(fb.getCollection());
        collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").load(template);
        if(collection==null) {
            saveErrors(request, Arrays.asList(new String[]{"datacollection.collection.view.collection-dont-exist"}));
            return;
        }
        if(!request.isUserInRole("manager") && !isAllowed(collection,request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"datacollection.collection.view.access-denied"}));
            return;
        }
        CollectionPB pb = new CollectionPB();
        PropertyUtils.copyProperties(pb,collection);
        Map parameters = new HashMap();
        if(StringUtil.asNull(request.getServerName())!=null) {
            parameters.put("hostname",request.getServerName());
            if(request.getServerPort()!=80) {
                parameters.put("port",new Integer(request.getServerPort()));
            }
        }
        parameters.put("contextpath",request.getContextPath());
        parameters.put("collection",collection.getId());
        ActionForward forward = mapping.findForward("collection-update-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("collection-remove-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("collection-newentry-link");
        if(forward!=null) {
            pb.setNewEntryLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        ActionForward viewLink = mapping.findForward("entry-view-link");
        ActionForward removeLink = mapping.findForward("entry-remove-link");
        QueryFilter filter = new QueryFilter("allforcollection");
        filter.setAttribute("collection", collection.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").search(filter);
        EntryPB[] pbEntries = new EntryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            Entry entity = (Entry) entities[i];
            pbEntries[i] = new EntryPB();
            pbEntries[i].setId(entity.getId());
            PropertyUtils.copyProperties(pbEntries[i], entity);
            parameters.put("entry",entity.getId());
            if(viewLink!=null) {
                pbEntries[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewLink.getPath(),parameters));
            }
            if(removeLink!=null) {
                pbEntries[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(removeLink.getPath(),parameters));
            }
        }
        pb.setEntries(pbEntries);
        request.setAttribute("collectionPB", pb);
    }

    protected boolean isAllowed(Collection collection, String user) {
        return user!=null && user.equals(collection.getUsername());
    }
}
