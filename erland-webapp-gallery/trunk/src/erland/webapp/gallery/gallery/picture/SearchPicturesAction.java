package erland.webapp.gallery.act.gallery.picture;

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
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.entity.gallery.picture.Picture;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class SearchPicturesAction extends SearchPicturesBaseAction {
    protected Collection getCategories(ActionForm form) {
        Integer categoryId = getCategoryId();
        if (categoryId == null && getGalleryId() != null) {
            Gallery template = (Gallery) getEnvironment().getEntityFactory().create("gallery-gallery");
            template.setId(getGalleryId());
            GalleryInterface entity = (GalleryInterface) getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").load(template);
            if (entity != null && !entity.getTopCategory().equals(new Integer(0))) {
                categoryId = entity.getTopCategory();
            }
        }
        if (categoryId != null) {
            Category[] categories = getCategoryTree(getGalleryId(), categoryId);
            Collection result = new ArrayList();
            for (int i = 0; i < categories.length; i++) {
                result.add(categories[i].getCategory());
            }
            return result;
        } else {
            return null;
        }
    }

    protected String getAllFilter() {
        return "allforgallery";
    }

    protected String getCategoryTreeFilter() {
        return "allforgalleryandcategorylist";
    }

    protected Collection getPictures(ActionForm form) {
        if (!getVirtualGalleryId().equals(getGalleryId())) {
            QueryFilter categoryFilter = new QueryFilter("allforgallery");
            categoryFilter.setAttribute("gallery", getVirtualGalleryId());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").search(categoryFilter);
            if (entities.length == 0) {
                return null;
            }
            Collection categories = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                GalleryCategoryAssociation entity = (GalleryCategoryAssociation) entities[i];
                categories.add(entity.getCategory());
            }
            QueryFilter pictureFilter = new QueryFilter("allforgalleryandcategorylistallrequired");
            pictureFilter.setAttribute("gallery", getGalleryId());
            pictureFilter.setAttribute("categories", categories);
            pictureFilter.setAttribute("numberofcategories", new Integer(categories.size()));
            entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(pictureFilter);
            Collection pictures = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                Picture entity = (Picture) entities[i];
                pictures.add(entity.getId());
            }
            return pictures;
        }
        return null;
    }
}
