package erland.webapp.dirgallery.loader;

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
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.dirgallery.gallery.GalleryHelper;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.dirgallery.gallery.picture.Picture;
import erland.webapp.usermgmt.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private String username;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String image = request.getParameter("image");
        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            GalleryInterface templateGallery = (GalleryInterface) environment.getEntityFactory().create("dirgallery-gallery");
            templateGallery.setId(gallery);
            GalleryInterface entity = (GalleryInterface) environment.getEntityStorageFactory().getStorage("dirgallery-gallery").load(templateGallery);
            if (entity == null || (getClass().equals(LoadImageCommand.class) && !entity.getOriginalDownloadable().booleanValue())) {
                return null;
            }
            username = entity.getUsername();
            Picture template = (Picture) environment.getEntityFactory().create("dirgallery-picture");
            template.setGallery(gallery);
            template.setDirectory(environment.getConfigurableResources().getParameter("basedirectory")+"/"+entity.getUsername()+"/"+entity.getDirectory());
            template.setId(image);
            Picture picture = (Picture) environment.getEntityStorageFactory().getStorage("dirgallery-picture").load(template);
            if (picture != null) {
                if (!entity.getOfficial().booleanValue()) {
                    User realUser = (User) request.getSession().getAttribute("user");
                    if (realUser != null && !username.equals(entity.getUsername())) {
                        return null;
                    } else if (realUser == null) {
                        return null;
                    }
                }

                imageFile = getImageFileName(picture);
            }
        }
        return null;
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

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
    }

    protected String getImageFile() {
        return imageFile;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public String getUsername() {
        return username;
    }
}