package erland.webapp.dirgallery.gallery.picture;
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
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditPictureCommentCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String galleryString = request.getParameter("gallery");
        String comment = request.getParameter("comment");
        Integer gallery = null;
        if (galleryString != null && galleryString.length() > 0) {
            gallery = Integer.valueOf(galleryString);
        }
        if (gallery != null && id != null) {
            GalleryInterface template = (GalleryInterface) environment.getEntityFactory().create("gallery");
            template.setId(gallery);
            GalleryInterface galleryEntity = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            User user = (User) request.getSession().getAttribute("user");
            if (galleryEntity != null && user != null && user.getUsername().equals(galleryEntity.getUsername())) {
                Picture pictureTemplate = (Picture) environment.getEntityFactory().create("picture");
                pictureTemplate.setDirectory(galleryEntity.getDirectory());
                pictureTemplate.setGallery(galleryEntity.getId());
                pictureTemplate.setId(id);
                Picture picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(pictureTemplate);
                if (picture != null) {
                    PictureComment commentTemplate = (PictureComment) environment.getEntityFactory().create("picturecomment");
                    commentTemplate.setId(picture.getFullPath());
                    if (comment == null || comment.length() == 0) {
                        environment.getEntityStorageFactory().getStorage("picturecomment").delete(commentTemplate);
                    } else {
                        commentTemplate.setComment(comment);
                        environment.getEntityStorageFactory().getStorage("picturecomment").store(commentTemplate);
                    }
                }
            }
        }
        return null;
    }
}
