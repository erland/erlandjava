package erland.webapp.gallery.act.gallery.category;

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
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.fb.gallery.category.CategoryFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

public class EditCategoryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryFB fb = (CategoryFB) form;
        Integer gallery = GalleryHelper.getGalleryId(getEnvironment(), fb.getGallery());
        Category template = (Category) getEnvironment().getEntityFactory().create("gallery-category");
        template.setGallery(gallery);
        template.setCategory(fb.getCategory());
        Category entity = (Category) getEnvironment().getEntityStorageFactory().getStorage("gallery-category").load(template);
        if (entity != null && !fb.getName().equals(entity.getName())) {
            entity.setName(fb.getName());
            getEnvironment().getEntityStorageFactory().getStorage("gallery-category").store(entity);
        }
        if (entity != null && (fb.getForcedOfficial().booleanValue() || !entity.getOfficial().equals(fb.getOfficial())) || !entity.getOfficialAlways().equals(fb.getOfficialAlways()) || !entity.getOfficialVisible().equals(fb.getVisible())) {
            QueryFilter filter = new QueryFilter("allforgalleryandcategorytree");
            filter.setAttribute("gallery", gallery);
            filter.setAttribute("category", fb.getCategory());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-category").search(filter);
            Collection categories = new ArrayList(entities.length);
            for (int i = 0; i < entities.length; i++) {
                categories.add(((Category) entities[i]).getCategory());
            }
            filter = new QueryFilter("allforgalleryandcategorylist");
            filter.setAttribute("gallery", gallery);
            filter.setAttribute("categories", categories);
            entity = (Category) getEnvironment().getEntityFactory().create("gallery-category");
            entity.setOfficial(fb.getOfficial());
            entity.setOfficialAlways(fb.getOfficialAlways());
            entity.setOfficialVisible(fb.getVisible());
            getEnvironment().getEntityStorageFactory().getStorage("gallery-category").update(filter, entity);

            filter = new QueryFilter("calculateofficialforgallery");
            filter.setAttribute("gallery", gallery);
            updatePictures(filter, gallery, Boolean.TRUE);

            filter = new QueryFilter("calculateunofficialforgallery");
            filter.setAttribute("gallery", gallery);
            updatePictures(filter, gallery, Boolean.FALSE);
        }
    }


    private void updatePictures(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        entity.setOfficial(official);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture) entities[i]).getId());
        }
        QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
        pictureFilter.setAttribute("gallery", gallery);
        pictureFilter.setAttribute("pictures", pictures);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").update(pictureFilter, entity);
    }
}