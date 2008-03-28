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

import erland.util.StringUtil;
import erland.webapp.datacollection.entity.collection.Collection;
import erland.webapp.datacollection.fb.collection.CollectionMenuItemPB;
import erland.webapp.datacollection.fb.collection.MenuItemPB;
import erland.webapp.datacollection.fb.collection.SelectCollectionFB;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;

public class LoadMenuAction extends BaseAction {
    protected Comparator SORT_BY_NAME = new Comparator() {
        public int compare(Object o1, Object o2) {
            if(o1 instanceof MenuItemPB && o2 instanceof MenuItemPB) {
                return ((MenuItemPB)o1).getName().compareTo(((MenuItemPB)o2).getName());
            }
            return 0;
        }
    };
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectCollectionFB fb = (SelectCollectionFB) form;
        String username = request.getRemoteUser();
        if (fb != null) {
            username = fb.getUser();
        }
        String prefix = StringUtil.asEmpty(mapping.getParameter());
        QueryFilter filter = new QueryFilter(getQueryFilter(request));
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage(getEntityName()).search(filter);
        CollectionMenuItemPB[] pb = new CollectionMenuItemPB[entities.length];
        request.getSession().removeAttribute(prefix+"menuCollectionsPB");
        for (int i = 0; i < entities.length; i++) {
            Collection entity = (Collection)entities[i];
            pb[i] = new CollectionMenuItemPB();
            pb[i].setId(entity.getId());
            pb[i].setName(entity.getTitle());
            pb[i].setUser(username);
            pb[i].setCollection(entity.getId());
            ActionForward collectionForward = mapping.findForward("collection");
            if(collectionForward!=null) {
                pb[i].setPath(collectionForward.getPath());
            }
        }
        request.getSession().setAttribute(prefix+"menuCollectionsPB", pb);

        if(request.isUserInRole("manager")) {
            filter = new QueryFilter("allbesidesuser");
            filter.setAttribute("username", username);
            entities = getEnvironment().getEntityStorageFactory().getStorage(getEntityName()).search(filter);
            pb = new CollectionMenuItemPB[entities.length];
            request.getSession().removeAttribute(prefix+"menuOthersCollectionsPB");
            for (int i = 0; i < entities.length; i++) {
                Collection entity = (Collection)entities[i];
                pb[i] = new CollectionMenuItemPB();
                pb[i].setId(entity.getId());
                pb[i].setName(entity.getTitle()+" ("+entity.getUsername()+")");
                pb[i].setUser(entity.getUsername());
                pb[i].setCollection(entity.getId());
                ActionForward collectionForward = mapping.findForward("otherscollection");
                if(collectionForward!=null) {
                    pb[i].setPath(collectionForward.getPath());
                }
            }
            request.getSession().setAttribute(prefix+"menuOthersCollectionsPB", pb);
        }
    }

    protected String getEntityName() {
        return "datacollection-collection";
    }

    protected String getQueryFilter(HttpServletRequest request) {
        return "allforuser";
    }
}
