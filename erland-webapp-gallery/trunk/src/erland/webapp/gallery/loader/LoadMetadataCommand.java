package erland.webapp.gallery.loader;

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

import erland.webapp.common.*;
import erland.webapp.common.image.MetadataHandlerInterface;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.gallery.gallery.GalleryHelper;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.picture.ViewPictureInterface;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class LoadMetadataCommand implements CommandInterface, ViewMetadataInterface, ViewPictureInterface {
    private Picture picture;
    private WebAppEnvironmentInterface environment;
    private MetadataHandlerInterface handler;


    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Boolean showAll = ServletParameterHelper.asBoolean(request.getParameter("showall"), Boolean.FALSE);
        Integer image = ServletParameterHelper.asInteger(request.getParameter("image"), null);
        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            Picture template = (Picture) environment.getEntityFactory().create("gallery-picture");
            template.setGallery(gallery);
            template.setId(image);
            picture = (Picture) environment.getEntityStorageFactory().getStorage("gallery-picture").load(template);
            if (picture != null) {
                String username = request.getParameter("user");
                if (username == null || username.length() == 0) {
                    User user = (User) request.getSession().getAttribute("user");
                    username = user.getUsername();
                }
                QueryFilter filter = new QueryFilter("allforuser");
                filter.setAttribute("username", username);
                EntityInterface[] storageEntities = environment.getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);

                String imageFile = getImageFileName(picture);
                for (int j = 0; j < storageEntities.length; j++) {
                    PictureStorage storage = (PictureStorage) storageEntities[j];
                    if (imageFile.startsWith(storage.getName())) {
                        imageFile = storage.getPath() + imageFile.substring(storage.getName().length());
                        break;
                    }
                }
                handler = new JPEGMetadataHandler(!showAll.booleanValue());
            }
        }
        return null;
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1, picture.getLink().length() - 1);
    }

    public Picture getPicture() {
        return picture;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    public String[] getMetadataNames() {
        return handler != null ? handler.getNames() : new String[0];
    }

    public String getMetadataValue(String name) {
        return handler != null ? handler.getValue(name) : null;
    }

    public String getMetadataDescription(String name) {
        return handler != null ? handler.getDescription(name) : null;
    }
}