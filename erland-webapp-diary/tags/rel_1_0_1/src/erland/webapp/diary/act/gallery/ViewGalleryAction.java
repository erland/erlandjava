package erland.webapp.diary.act.gallery;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.gallery.Gallery;
import erland.webapp.diary.fb.gallery.GalleryFB;
import erland.webapp.usermgmt.User;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        Gallery template = (Gallery) getEnvironment().getEntityFactory().create("diary-gallery");
        template.setId(fb.getId());
        Gallery gallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").load(template);
        PropertyUtils.copyProperties(fb,gallery);

        GalleryInterface[] galleries = GalleryHelper.searchGalleries(getEnvironment(),"gallery-gallery",request.getRemoteUser(),"allforuser");
        GalleryPB[] pbGalleries = new GalleryPB[galleries.length];
        for (int i = 0; i < galleries.length; i++) {
            pbGalleries[i] = new GalleryPB();
            PropertyUtils.copyProperties(pbGalleries[i],galleries[i]);
        }
        request.getSession().setAttribute("externalGalleriesPB",pbGalleries);
    }
}
