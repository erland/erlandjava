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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.fb.gallery.GalleryFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditGalleryAction extends BaseAction {
    private final static String ID = EditGalleryAction.class+"-id";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        setId(request,fb.getId());
        String username = request.getRemoteUser();
        Gallery gallery = (Gallery) getEnvironment().getEntityFactory().create("gallery-gallery");
        gallery.setUsername(username);
        PropertyUtils.copyProperties(gallery, fb);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").store(gallery);

        if (fb.getReferencedGallery() != null && fb.getReferencedGallery().intValue()!=0) {
            String[] categories = fb.getCategories();
            if (categories != null) {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery", gallery.getId());
                getEnvironment().getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").delete(filter);
                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].length() > 0) {
                        Integer categoryId = Integer.valueOf(categories[i]);
                        GalleryCategoryAssociation category = (GalleryCategoryAssociation) getEnvironment().getEntityFactory().create("gallery-gallerycategoryassociation");
                        category.setCategory(categoryId);
                        category.setGallery(gallery.getId());
                        getEnvironment().getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").store(category);
                    }
                }
            }
        }
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        if(getId(request)!=null) {
            return mapping.findForward("success-edit");
        }else {
            return mapping.findForward("success-new");
        }
    }

    public Integer getId(HttpServletRequest request) {
        return (Integer) request.getAttribute(ID);
    }
    public void setId(HttpServletRequest request, Integer id) {
        request.setAttribute(ID,id);
    }
}
