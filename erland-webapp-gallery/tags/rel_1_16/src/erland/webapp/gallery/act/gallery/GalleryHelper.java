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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.picture.Picture;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

public class GalleryHelper {
    public static Integer getGalleryId(WebAppEnvironmentInterface environment, HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        Integer galleryId = null;
        if (galleryString != null && galleryString.length() > 0) {
            galleryId = Integer.valueOf(galleryString);
        }
        if (galleryId != null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery-gallery");
            template.setId(galleryId);
            Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(template);
            if (entity != null) {
                if (entity.getReferencedGallery() != null && !entity.getReferencedGallery().equals(new Integer(0))) {
                    return entity.getReferencedGallery();
                }
            }
        }
        return galleryId;
    }

    public static Integer getGalleryId(WebAppEnvironmentInterface environment, Integer galleryId) {
        if (galleryId != null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery-gallery");
            template.setId(galleryId);
            Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(template);
            return getGalleryId(entity);
        }
        return galleryId;
    }

    public static Integer getGalleryId(Gallery gallery) {
        if (gallery != null) {
            if (gallery.getReferencedGallery() != null && !gallery.getReferencedGallery().equals(new Integer(0))) {
                return gallery.getReferencedGallery();
            }else {
                return gallery.getId();
            }
        }
        return null;
    }

    public static Gallery getGallery(WebAppEnvironmentInterface environment, Integer galleryId) {
        Gallery gallery = null;
        if (galleryId != null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery-gallery");
            template.setId(galleryId);
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(template);
        }
        return gallery;
    }

    public static Gallery[] searchGalleries(WebAppEnvironmentInterface environment, String entityName, String username, String filterName) {
        QueryFilter filter = new QueryFilter(filterName);
        filter.setAttribute("username", username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entityName).search(filter);
        return (Gallery[]) Arrays.asList(entities).toArray(new Gallery[0]);
    }

    public static void updatePictureVisibility(WebAppEnvironmentInterface environment, Integer galleryId) {
        updatePictureVisibility(environment,getGallery(environment,galleryId));
    }
    public static void updatePictureVisibility(WebAppEnvironmentInterface environment, Gallery gallery) {
        QueryFilter filter = new QueryFilter("calculateofficialforgallery");
        filter.setAttribute("gallery", gallery.getId());
        updatePictures(environment,filter, gallery.getId(), Boolean.TRUE);

        filter = new QueryFilter("calculateunofficialforgallery");
        filter.setAttribute("gallery", gallery.getId());
        updatePictures(environment,filter, gallery.getId(), Boolean.FALSE);
        if(gallery.getOfficialCategory()!=null && gallery.getOfficialCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallwithoutcategory");
            filter.setAttribute("gallery",gallery.getId());
            filter.setAttribute("category", gallery.getOfficialCategory());
            updatePictures(environment,filter, gallery.getId(), Boolean.FALSE);
        }
        if(gallery.getOfficialGuestCategory()!=null && gallery.getOfficialGuestCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallwithcategory");
            filter.setAttribute("gallery",gallery.getId());
            filter.setAttribute("category", gallery.getOfficialGuestCategory());
            updatePictures(environment,filter, gallery.getId(), Boolean.FALSE);
        }

        filter = new QueryFilter("calculateofficialguestforgallery");
        filter.setAttribute("gallery", gallery.getId());
        updatePicturesGuest(environment,filter, gallery.getId(), Boolean.TRUE);

        filter = new QueryFilter("calculateunofficialguestforgallery");
        filter.setAttribute("gallery", gallery.getId());
        updatePicturesGuest(environment,filter, gallery.getId(), Boolean.FALSE);

        if(gallery.getOfficialGuestCategory()!=null && gallery.getOfficialGuestCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallunofficialorwithoutcategory");
            filter.setAttribute("gallery",gallery.getId());
            filter.setAttribute("category", gallery.getOfficialGuestCategory());
            updatePicturesGuest(environment,filter, gallery.getId(), Boolean.FALSE);
        }
    }
    private static void updatePictures(WebAppEnvironmentInterface environment, QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) environment.getEntityFactory().create("gallery-picture");
        entity.setOfficial(official);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(environment,entities, gallery, entity);
    }

    private static void updatePicturesGuest(WebAppEnvironmentInterface environment, QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) environment.getEntityFactory().create("gallery-picture");
        entity.setOfficialGuest(official);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(environment,entities, gallery, entity);
    }

    private static void updatePictures(WebAppEnvironmentInterface environment, EntityInterface[] entities, Integer gallery, Picture picture) {
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture) entities[i]).getId());
        }
        if (pictures.size() > 0) {
	        QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
	        pictureFilter.setAttribute("gallery", gallery);
	        pictureFilter.setAttribute("pictures", pictures);
	        environment.getEntityStorageFactory().getStorage("gallery-picture").update(pictureFilter, picture);
	    }
    }
}