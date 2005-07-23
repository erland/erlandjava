package erland.webapp.dirgallery.act.gallery;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.dirgallery.entity.gallery.FriendGallery;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.gallery.MenuItemPB;
import erland.webapp.dirgallery.fb.gallery.SelectGalleryFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

public class LoadMenuGalleriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectGalleryFB fb = (SelectGalleryFB) form;
        String username = request.getRemoteUser();
        if (username == null) {
            username = fb.getUser();
        }
        QueryFilter filter = new QueryFilter("allforgallery");
        filter.setAttribute("gallery", fb.getGallery());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-friendgallery").search(filter);
        MenuItemPB[] pb = new MenuItemPB[0];
        if (entities.length > 0) {
            Collection requestedGalleries = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                requestedGalleries.add(((FriendGallery) entities[i]).getFriendGallery());
            }
            filter = new QueryFilter(getGalleryListQuery());
            filter.setAttribute("galleries", requestedGalleries);
            entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").search(filter);
            pb = new MenuItemPB[entities.length];
            ActionForward forward = mapping.findForward("view-gallery-link");
            for (int i = 0; i < entities.length; i++) {
                pb[i] = new MenuItemPB();
                pb[i].setId(((Gallery) entities[i]).getId());
                if (((Gallery) entities[i]).getMenuName() == null || ((Gallery) entities[i]).getMenuName().length() == 0) {
                    pb[i].setName(((Gallery) entities[i]).getTitle());
                } else {
                    pb[i].setName(((Gallery) entities[i]).getMenuName());
                }
                pb[i].setUser(username);
                if (forward != null) {
                    pb[i].setPath(forward.getPath());
                }
            }
        }
        request.getSession().setAttribute("menuGalleriesPB", pb);
    }

    protected String getGalleryListQuery() {
        return "allforgallerylist";
    }
}
