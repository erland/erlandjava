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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.dirgallery.entity.gallery.FriendGallery;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.gallery.GalleryFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        Gallery gallery = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        String username = request.getRemoteUser();
        PropertyUtils.copyProperties(gallery, fb);
        gallery.setUsername(username);
        getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").store(gallery);

        if (fb.getFriendGalleries() != null) {
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery", gallery.getId());
            getEnvironment().getEntityStorageFactory().getStorage("dirgallery-friendgallery").delete(filter);
            for (int i = 0; i < fb.getFriendGalleries().length; i++) {
                FriendGallery entity = (FriendGallery) getEnvironment().getEntityFactory().create("dirgallery-friendgallery");
                entity.setGallery(gallery.getId());
                entity.setFriendGallery(fb.getFriendGalleries()[i]);
                getEnvironment().getEntityStorageFactory().getStorage("dirgallery-friendgallery").store(entity);
            }
        }
        //Set id attribute to make it possible to forward the request to gallery specific page
        request.setAttribute("id", gallery.getId());
    }
}
