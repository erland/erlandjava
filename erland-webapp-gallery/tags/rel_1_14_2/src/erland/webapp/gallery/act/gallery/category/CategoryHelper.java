package erland.webapp.gallery.act.gallery.category;

import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.fb.gallery.category.CategoryPB;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Arrays;

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

public class CategoryHelper {
    public static Category[] searchCategories(WebAppEnvironmentInterface environment, Integer gallery, Integer virtualGalleryId, Integer category, String parentFilter, String noParentFilter) {
        if (category == null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery-gallery");
            template.setId(virtualGalleryId);
            Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(template);
            if (entity != null && !entity.getTopCategory().equals(new Integer(0))) {
                category = entity.getTopCategory();
            }
        }
        QueryFilter filter = null;
        if (category != null) {
            filter = new QueryFilter(parentFilter);
            filter.setAttribute("parent", category);
        } else {
            filter = new QueryFilter(noParentFilter);
        }
        filter.setAttribute("gallery", gallery);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-category").search(filter);
        return (Category[]) Arrays.asList(entities).toArray(new Category[0]);
    }

    public static Category[] searchCategories(WebAppEnvironmentInterface environment, Integer gallery, String filterName) {
        QueryFilter filter = null;
        filter = new QueryFilter(filterName);
        filter.setAttribute("gallery", gallery);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-category").search(filter);
        return (Category[]) Arrays.asList(entities).toArray(new Category[0]);
    }
}