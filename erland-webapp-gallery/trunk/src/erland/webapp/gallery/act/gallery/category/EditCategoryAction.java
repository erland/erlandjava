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
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.fb.gallery.category.CategoryFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class EditCategoryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryFB fb = (CategoryFB) form;
        Gallery galleryEntity = GalleryHelper.getGallery(getEnvironment(), fb.getGallery());
        Integer gallery = GalleryHelper.getGalleryId(galleryEntity);
        Category template = (Category) getEnvironment().getEntityFactory().create("gallery-category");
        template.setGallery(gallery);
        template.setCategory(fb.getCategory());
        Category entity = (Category) getEnvironment().getEntityStorageFactory().getStorage("gallery-category").load(template);
        if (entity == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.category.edit.category-dont-exist"}));
            return;
        }
        entity.setName(fb.getName());
        entity.setNameEnglish(fb.getNameEnglish());
        getEnvironment().getEntityStorageFactory().getStorage("gallery-category").store(entity);

        Boolean official = Boolean.FALSE;
        Boolean officialNever = Boolean.FALSE;
        if(Boolean.TRUE.equals(fb.getOfficial())) {
            official = Boolean.TRUE;
        }else if(!Boolean.TRUE.equals(fb.getOfficial()) && !Boolean.TRUE.equals(fb.getOfficialGuest())) {
            officialNever = Boolean.TRUE;
        }

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
        entity.setOfficial(official);
        entity.setOfficialAlways(fb.getOfficialAlways());
        entity.setOfficialVisible(fb.getOfficialVisible());
        entity.setOfficialNever(officialNever);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-category").update(filter, entity);

        filter = new QueryFilter("calculateofficialforgallery");
        filter.setAttribute("gallery", gallery);
        updatePictures(filter, gallery, Boolean.TRUE);

        filter = new QueryFilter("calculateunofficialforgallery");
        filter.setAttribute("gallery", gallery);
        updatePictures(filter, gallery, Boolean.FALSE);
        if(galleryEntity.getOfficialCategory()!=null && galleryEntity.getOfficialCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallwithoutcategory");
            filter.setAttribute("gallery",gallery);
            filter.setAttribute("category", galleryEntity.getOfficialCategory());
            updatePictures(filter, gallery, Boolean.FALSE);
        }
        if(galleryEntity.getOfficialGuestCategory()!=null && galleryEntity.getOfficialGuestCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallwithcategory");
            filter.setAttribute("gallery",gallery);
            filter.setAttribute("category", galleryEntity.getOfficialGuestCategory());
            updatePictures(filter, gallery, Boolean.FALSE);
        }

        filter = new QueryFilter("calculateofficialguestforgallery");
        filter.setAttribute("gallery", gallery);
        updatePicturesGuest(filter, gallery, Boolean.TRUE);

        filter = new QueryFilter("calculateunofficialguestforgallery");
        filter.setAttribute("gallery", gallery);
        updatePicturesGuest(filter, gallery, Boolean.FALSE);

        if(galleryEntity.getOfficialGuestCategory()!=null && galleryEntity.getOfficialGuestCategory().intValue()!=0) {
            filter = new QueryFilter("calculateallunofficialorwithoutcategory");
            filter.setAttribute("gallery",gallery);
            filter.setAttribute("category", galleryEntity.getOfficialGuestCategory());
            updatePicturesGuest(filter, gallery, Boolean.FALSE);
        }
    }


    private void updatePictures(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        entity.setOfficial(official);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(entities, gallery, entity);
    }

    private void updatePicturesGuest(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        entity.setOfficialGuest(official);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(entities, gallery, entity);
    }

    private void updatePictures(EntityInterface[] entities, Integer gallery, Picture picture) {
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture) entities[i]).getId());
        }
		if (pictures.size() > 0) {
			QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
			pictureFilter.setAttribute("gallery", gallery);
			pictureFilter.setAttribute("pictures", pictures);
			getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").update(pictureFilter, picture);
		}
    }
}