package erland.webapp.gallery.act.gallery;

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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.act.gallery.category.CategoryHelper;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.fb.gallery.GalleryFB;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.webapp.gallery.fb.gallery.category.CategoryPB;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;
import erland.webapp.gallery.fb.gallery.picture.ResolutionPB;
import erland.webapp.gallery.fb.skin.SkinFB;

import java.util.Arrays;

public class NewGalleryAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        fb.setCompression(null);
        fb.setThumbnailCompression(null);
        fb.setAntialias(Boolean.FALSE);
        fb.setThumbnailAntialias(Boolean.FALSE);
        fb.setThumbnailWidth(null);
        fb.setSortOrder("bydatedesc");
        fb.setNoOfCols(new Integer(3));
        fb.setNoOfRows(new Integer(3));
        fb.setAllowSearch(Boolean.TRUE);
        fb.setCutLongPictureTitles(Boolean.TRUE);
        fb.setUseShortPictureNames(Boolean.TRUE);
        fb.setShowPictureTitle(Boolean.TRUE);
        fb.setShowResolutionLinks(Boolean.TRUE);
        fb.setForcePictureUpdate(Boolean.FALSE);
        fb.setSkin(null);

        Gallery[] galleries = GalleryHelper.searchGalleries(getEnvironment(),"gallery-gallery",request.getRemoteUser(),"allrealforuser");
        GalleryPB[] pbGalleries = new GalleryPB[galleries.length];
        for (int i = 0; i < galleries.length; i++) {
            pbGalleries[i] = new GalleryPB();
            PropertyUtils.copyProperties(pbGalleries[i], galleries[i]);
        }
        request.getSession().setAttribute("galleriesPB", pbGalleries);

        EntityInterface[] resolutions = getEnvironment().getEntityStorageFactory().getStorage("gallery-resolution").search(new QueryFilter("all"));
        ResolutionPB[] pbResolutions = new ResolutionPB[resolutions.length];
        for (int i = 0; i < pbResolutions.length; i++) {
            pbResolutions[i] = new ResolutionPB();
            PropertyUtils.copyProperties(pbResolutions[i], resolutions[i]);
        }
        request.getSession().setAttribute("resolutionsPB", pbResolutions);

        EntityInterface[] skins = getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").search(new QueryFilter("all"));
        SkinFB[] pbSkins = new SkinFB[skins.length];
        for (int i = 0; i < pbSkins.length; i++) {
            pbSkins[i] = new SkinFB();
            PropertyUtils.copyProperties(pbSkins[i], skins[i]);
        }
        request.getSession().setAttribute("skinsPB",pbSkins);
    }
}
