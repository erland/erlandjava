package erland.webapp.gallery.act.gallery;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.fb.gallery.QuickSetupFB;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.category.Category;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

public class QuickSetupAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QuickSetupFB fb = (QuickSetupFB) form;
        Gallery gallery = GalleryHelper.getGallery(getEnvironment(),fb.getGallery());
        if(gallery==null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.quicksetup.gallery-dont-exist"}));
            return;
        }
        QueryFilter categoryFilter = new QueryFilter("allforgallery");
        categoryFilter.setAttribute("gallery", gallery.getId());
        Category category = (Category) getEnvironment().getEntityFactory().create("gallery-category");
        category.setOfficialAlways(Boolean.FALSE);
        category.setOfficialNever(Boolean.FALSE);
        category.setOfficialVisible(Boolean.TRUE);
        category.setOfficial(Boolean.TRUE);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-category").update(categoryFilter,category);

        if(fb.getPublishCategories().booleanValue()) {
            // Setup publish categories and top category
            gallery.setTopCategory(fb.getTopCategory());
            gallery.setOfficialCategory(fb.getOfficialCategory());
            gallery.setOfficialGuestCategory(fb.getOfficialGuestCategory());
            getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").store(gallery);
        }else {
            // Make sure hidden categories are set to hidden
            categoryFilter = new QueryFilter("allforgalleryandcategorylist");
            categoryFilter.setAttribute("gallery", gallery.getId());
            if(fb.getHiddenCategories()!=null && fb.getHiddenCategories().length>0) {
                categoryFilter.setAttribute("categories", Arrays.asList(fb.getHiddenCategories()));
                category = (Category) getEnvironment().getEntityFactory().create("gallery-category");
                category.setOfficialAlways(Boolean.FALSE);
                category.setOfficialNever(Boolean.FALSE);
                category.setOfficialVisible(Boolean.FALSE);
                category.setOfficial(Boolean.FALSE);
                getEnvironment().getEntityStorageFactory().getStorage("gallery-category").update(categoryFilter,category);
            }
            // Reset publish categories and setup top category
            gallery.setTopCategory(fb.getTopCategory());
            gallery.setOfficialCategory(null);
            gallery.setOfficialGuestCategory(null);
            getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").store(gallery);
        }

        // Update visibility on all pictures
        GalleryHelper.updatePictureVisibility(getEnvironment(),gallery);
    }
}