package erland.webapp.gallery.act.gallery;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.fb.gallery.QuickSetupFB;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.entity.gallery.category.Category;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.ArrayList;

public class NewQuickSetupAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QuickSetupFB fb = (QuickSetupFB) form;
        Gallery gallery = GalleryHelper.getGallery(getEnvironment(),fb.getGallery());
        fb.setPublishCategories(Boolean.TRUE);
        if(gallery==null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.quicksetup.gallery-dont-exist"}));
            return;
        }
        fb.setOfficialCategory(gallery.getOfficialCategory());
        fb.setOfficialGuestCategory(gallery.getOfficialGuestCategory());
        fb.setTopCategory(gallery.getTopCategory());
        QueryFilter categoryFilter = new QueryFilter("allforgallery");
        categoryFilter.setAttribute("gallery", gallery.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-category").search(categoryFilter);
        if (entities.length == 0) {
            fb.setHiddenCategories(null);
        }else {
            ArrayList categoryList = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                Category entity = (Category) entities[i];
                if(entity.getOfficialNever().booleanValue()) {
                    categoryList.add(entity.getCategory());
                }
            }
            fb.setHiddenCategories((Integer[])categoryList.toArray(new Integer[0]));
        }
    }
}