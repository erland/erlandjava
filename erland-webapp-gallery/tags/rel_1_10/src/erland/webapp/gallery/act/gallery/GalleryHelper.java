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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
}