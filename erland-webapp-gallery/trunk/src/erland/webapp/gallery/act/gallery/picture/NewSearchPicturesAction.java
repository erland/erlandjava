package erland.webapp.gallery.act.gallery.picture;

import erland.webapp.gallery.act.gallery.category.SearchCategoriesAction;
import erland.webapp.gallery.act.gallery.category.CategoryHelper;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.fb.gallery.category.SelectCategoryFB;
import erland.webapp.gallery.fb.gallery.category.CategoryPB;
import erland.webapp.gallery.fb.gallery.picture.SearchPictureFB;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.common.act.BaseAction;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class NewSearchPicturesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchPictureFB fb = (SearchPictureFB) form;

        fb.setAllCategories(Boolean.TRUE);

        Integer gallery = GalleryHelper.getGalleryId(getEnvironment(), fb.getGallery());
        Integer virtualGalleryId = fb.getGallery();
        Category[] entities = CategoryHelper.searchCategories(getEnvironment(),gallery,virtualGalleryId,null,getParentFilter(),getNoParentFilter());
        CategoryPB[] categories = new CategoryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            categories[i] = new CategoryPB();
            PropertyUtils.copyProperties(categories[i], entities[i]);
            categories[i].setGallery(virtualGalleryId);
        }
        request.setAttribute("categoriesPB",categories);
    }

    protected String getNoParentFilter() {
        return "allforgalleryorderedbyname";
    }

    protected String getParentFilter() {
        return "allforgalleryorderedbyname";
    }
}