package erland.webapp.dirgallery.act.gallery.picture;

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
import erland.webapp.dirgallery.entity.gallery.picture.Picture;
import erland.webapp.dirgallery.entity.gallery.picture.PictureComment;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.gallery.picture.PictureCommentFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewPictureCommentAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PictureCommentFB fb = (PictureCommentFB) form;
        Gallery template = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        template.setId(fb.getGallery());
        Gallery galleryEntity = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(template);
        PictureCommentFB pb = new PictureCommentFB();
        if (galleryEntity != null) {
            Picture pictureTemplate = (Picture) getEnvironment().getEntityFactory().create("dirgallery-picture");
            pictureTemplate.setDirectory(getEnvironment().getConfigurableResources().getParameter("basedirectory") + "/" + galleryEntity.getUsername() + "/" + galleryEntity.getDirectory());
            pictureTemplate.setGallery(galleryEntity.getId());
            pictureTemplate.setId(fb.getId());
            Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picture").load(pictureTemplate);
            if (picture != null) {
                PictureComment commentTemplate = (PictureComment) getEnvironment().getEntityFactory().create("dirgallery-picturecomment");
                commentTemplate.setId(picture.getFullPath());
                PictureComment comment = (PictureComment) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picturecomment").load(commentTemplate);
                if (comment != null) {
                    PropertyUtils.copyProperties(pb, comment);
                }
            }
        }
        request.setAttribute("pictureCommentPB", pb);
    }
}
