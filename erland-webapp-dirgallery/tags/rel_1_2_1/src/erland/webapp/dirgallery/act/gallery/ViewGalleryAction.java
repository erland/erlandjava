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
import erland.webapp.dirgallery.fb.gallery.GalleryFB;
import erland.webapp.dirgallery.fb.gallery.picture.ResolutionPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ViewGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        Gallery template = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        template.setId(fb.getId());
        Gallery gallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(template);
        if (gallery == null) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.gallery.view.gallery-dont-exist"}));
            return;
        }
        if (!gallery.getUsername().equals(request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.gallery.view.incorrect-user"}));
            return;
        }
        QueryFilter filter = new QueryFilter("allforgallery");
        filter.setAttribute("gallery", fb.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-friendgallery").search(filter);
        Integer[] friendGalleries = new Integer[entities.length];
        for (int i = 0; i < entities.length; i++) {
            friendGalleries[i] = ((FriendGallery) entities[i]).getFriendGallery();
        }

        PropertyUtils.copyProperties(fb, gallery);
        fb.setFriendGalleries(friendGalleries);

        filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").search(filter);
        GalleryFB[] pb = new GalleryFB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new GalleryFB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.getSession().setAttribute("galleriesPB", pb);

        ResolutionPB[] resolutionsPB = new ResolutionPB[0];
        EntityInterface[] resolutionEntities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-resolution").search(new QueryFilter("all"));
        if (resolutionEntities.length > 0) {
            resolutionsPB = new ResolutionPB[resolutionEntities.length];
            for (int i = 0; i < resolutionsPB.length; i++) {
                resolutionsPB[i] = new ResolutionPB();
                try {
                    PropertyUtils.copyProperties(resolutionsPB[i], resolutionEntities[i]);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                } catch (NoSuchMethodException e) {
                }
            }
        }
        request.getSession().setAttribute("resolutionsPB", resolutionsPB);
    }
}
