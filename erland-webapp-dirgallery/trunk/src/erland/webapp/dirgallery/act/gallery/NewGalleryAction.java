package erland.webapp.dirgallery.act.gallery;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.gallery.GalleryFB;
import erland.webapp.dirgallery.fb.gallery.picture.ResolutionPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class NewGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        fb.setId(null);
        fb.setUsername(request.getRemoteUser());
        fb.setTitle(null);
        fb.setMenuName(null);
        fb.setDescription(null);
        fb.setOfficial(Boolean.TRUE);
        fb.setDirectory(null);
        fb.setOriginalDownloadable(Boolean.FALSE);
        fb.setIncludeSubDirectories(Boolean.TRUE);
        fb.setNumberOfThumbnailsPerRow(new Integer(3));
        fb.setThumbnailWidth(null);
        fb.setShowLogoInGalleryPage(Boolean.FALSE);
        fb.setShowDownloadLinks(Boolean.FALSE);
        fb.setLogo(null);
        fb.setLogoLink(null);
        fb.setUseLogoSeparator(Boolean.TRUE);
        fb.setLogoSeparatorHeight(new Integer(5));
        fb.setLogoSeparatorColor(null);
        fb.setShowPictureNames(Boolean.TRUE);
        fb.setMaxNumberOfThumbnailRows(new Integer(3));
        fb.setUseShortPictureNames(Boolean.TRUE);
        fb.setOrderNumber(new Integer(1));
        fb.setUseThumbnailCache(Boolean.TRUE);
        fb.setThumbnailCompression(null);
        fb.setTypeOfFiles(Gallery.PICTUREFILES);
        fb.setNumberOfMovieThumbnailColumns(new Integer(2));
        fb.setNumberOfMovieThumbnailRows(new Integer(3));
        fb.setMaxPictureNameLength(new Integer(30));
        fb.setShowPictureNameInTooltip(Boolean.FALSE);
        fb.setUseTooltip(Boolean.FALSE);
        fb.setShowFileSizeBelowPicture(Boolean.FALSE);
        fb.setShowCommentBelowPicture(Boolean.FALSE);
        fb.setFriendGalleries(new Integer[0]);
        fb.setDefaultResolution(null);
        fb.setMaxWidth(null);
        fb.setUseCopyright(Boolean.TRUE);
        fb.setCopyrightText(null);
        fb.setCopyrightTransparency(new Double(0.4));
        fb.setCopyrightPosition(new Integer(1));
        fb.setUseCacheLargeImages(Boolean.FALSE);

        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").search(filter);
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