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
import erland.webapp.datacollection.fb.collection.CollectionPB;
import erland.webapp.datacollection.fb.collection.SelectCollectionFB;
import erland.webapp.datacollection.fb.account.SelectUserFB;
import erland.webapp.datacollection.logic.collection.CollectionHelper;
import erland.webapp.common.act.BaseAction;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchCollectionsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectCollectionFB fb = (SelectCollectionFB) form;
        String username = request.getRemoteUser();
        if (fb != null) {
            username = fb.getUser();
        }
        Collection[] entities = CollectionHelper.searchCollections(getEnvironment(),getEntityName(),username,fb.getApplication(),getQueryFilter(request,fb));
        CollectionPB[] pb = new CollectionPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new CollectionPB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.setAttribute("collectionsPB", pb);
    }

    protected String getEntityName() {
        return "datacollection-collection";
    }

    protected String getQueryFilter(HttpServletRequest request,SelectCollectionFB fb) {
        if(StringUtil.asNull(fb.getApplication())==null) {
            return "allforuser";
        }else {
            return "allforuserandapplication";
        }
    }
}
