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

import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.fb.gallery.category.CategoryFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewCategoryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryFB fb = (CategoryFB) form;
        Integer gallery = GalleryHelper.getGalleryId(getEnvironment(), fb.getGallery());
        Category template = (Category) getEnvironment().getEntityFactory().create("gallery-category");
        template.setGallery(gallery);
        template.setCategory(fb.getCategory());
        Category category = (Category) getEnvironment().getEntityStorageFactory().getStorage("gallery-category").load(template);
        if (category != null) {
            category.setGallery(fb.getGallery());
            PropertyUtils.copyProperties(fb, category);
            if(Boolean.TRUE.equals(category.getOfficial()) && !Boolean.TRUE.equals(category.getOfficialNever())) {
                fb.setOfficial(Boolean.TRUE);
                fb.setOfficialGuest(Boolean.TRUE);
            }else if(Boolean.TRUE.equals(category.getOfficialNever())) {
                fb.setOfficial(Boolean.FALSE);
                fb.setOfficialGuest(Boolean.FALSE);
            }else {
                fb.setOfficial(Boolean.FALSE);
                fb.setOfficialGuest(Boolean.TRUE);
            }
        }
    }
}