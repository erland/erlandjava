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
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.gallery.gallery.Gallery;
import erland.webapp.gallery.gallery.GalleryHelper;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.guestaccount.GuestAccountHelper;
import erland.webapp.usermgmt.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private String username;

    public String execute(HttpServletRequest request) {
        String imageString = request.getParameter("image");
        Integer image = null;
        if (imageString != null && imageString.length() > 0) {
            image = Integer.valueOf(imageString);
        }
        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            Picture template = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
            template.setGallery(gallery);
            template.setId(image);
            Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").load(template);
            if (picture != null) {
                String username = request.getParameter("user");
                if (username == null || username.length() == 0) {
                    User user = (User) request.getSession().getAttribute("user");
                    username = user.getUsername();
                }
                setUsername(username);
                if (!picture.getOfficial().booleanValue()) {
                    Gallery templateGallery = (Gallery) getEnvironment().getEntityFactory().create("gallery-gallery");
                    templateGallery.setId(gallery);
                    Gallery entity = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").load(templateGallery);
                    if (entity == null) {
                        return null;
                    }

                    User realUser = (User) request.getSession().getAttribute("user");
                    if (realUser != null && (!username.equals(realUser.getUsername()) || !username.equals(entity.getUsername()))) {
                        return null;
                    } else if (realUser == null) {
                        User guestUser = (User) request.getSession().getAttribute("guestuser");
                        if (guestUser == null || !username.equals(entity.getUsername()) || !GuestAccountHelper.isGuestUser(getEnvironment(), username, guestUser.getUsername())) {
                            return null;
                        }
                    }
                }

                QueryFilter filter = new QueryFilter("allforuser");
                filter.setAttribute("username", username);
                EntityInterface[] storageEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);

                String imageFile = getImageFileName(picture);
                for (int j = 0; j < storageEntities.length; j++) {
                    PictureStorage storage = (PictureStorage) storageEntities[j];
                    if (imageFile.startsWith(storage.getName())) {
                        imageFile = storage.getPath() + imageFile.substring(storage.getName().length());
                        break;
                    }
                }
                setImageFile(imageFile);
            }
        }
        return null;
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1, picture.getLink().length() - 1);
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!ImageWriteHelper.writeImage(environment, getImageFile(), response.getOutputStream())) {
                request.getRequestDispatcher("thumbnailna.gif").forward(request, response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getImageFile() {
        return imageFile;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public String getUsername() {
        return username;
    }
}