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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.act.gallery.category.ViewCategoriesInterface;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.fb.gallery.GalleryFB;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Arrays;

public class ViewGalleryAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFB fb = (GalleryFB) form;
        Gallery template = (Gallery) getEnvironment().getEntityFactory().create("gallery-gallery");
        template.setId(fb.getId());
        GalleryInterface gallery = (GalleryInterface) getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").load(template);
        if (gallery == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.do-not-exist"}));
            return;
        }
        if (request.getRemoteUser()!=null && !gallery.getUsername().equals(request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.unauthorized"}));
            return;
        }
        PropertyUtils.copyProperties(fb,gallery);
    }
}