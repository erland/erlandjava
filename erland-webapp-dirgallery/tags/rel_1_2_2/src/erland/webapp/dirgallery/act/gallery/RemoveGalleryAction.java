package erland.webapp.dirgallery.act.gallery;

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
import erland.webapp.dirgallery.fb.gallery.GalleryFB;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class RemoveGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        Gallery gallery = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        gallery.setId(fb.getId());
        gallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(gallery);
        if (!gallery.getUsername().equals(request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.gallery.remove.incorrect-user"}));
            return;
        }
        getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").delete(gallery);
        QueryFilter filter = new QueryFilter("allforgallery");
        filter.setAttribute("gallery", gallery.getId());
        getEnvironment().getEntityStorageFactory().getStorage("dirgallery-friendgallery").delete(filter);
    }
}
