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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.common.image.MetadataHandlerInterface;
import erland.webapp.common.image.FileMetadataHandler;
import erland.webapp.dirgallery.gallery.GalleryHelper;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.dirgallery.gallery.picture.Picture;
import erland.webapp.dirgallery.gallery.picture.PictureComment;
import erland.webapp.dirgallery.gallery.picture.ViewPictureInterface;

import javax.servlet.http.HttpServletRequest;

public class LoadMetadataCommand implements CommandInterface, ViewMetadataInterface, ViewPictureInterface {
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private Picture picture;
    private String comment;
    private MetadataHandlerInterface handler;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String image = request.getParameter("image");
        Boolean showAll = ServletParameterHelper.asBoolean(request.getParameter("showall"), Boolean.FALSE);

        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            GalleryInterface templateGallery = (GalleryInterface) environment.getEntityFactory().create("dirgallery-gallery");
            templateGallery.setId(gallery);
            GalleryInterface entityGallery = (GalleryInterface) environment.getEntityStorageFactory().getStorage("dirgallery-gallery").load(templateGallery);
            if (entityGallery != null) {
                Picture template = (Picture) environment.getEntityFactory().create("dirgallery-picture");
                template.setGallery(gallery);
                template.setDirectory(environment.getConfigurableResources().getParameter("basedirectory")+"/"+entityGallery.getUsername()+"/"+entityGallery.getDirectory());
                template.setId(image);
                picture = (Picture) environment.getEntityStorageFactory().getStorage("dirgallery-picture").load(template);
                if (picture != null) {
                    imageFile = getImageFileName(picture);
                    if(imageFile.toLowerCase().endsWith(".jpg") || imageFile.toLowerCase().endsWith(".jpeg")) {
                        handler = new JPEGMetadataHandler(!showAll.booleanValue());
                    }else {
                        handler = new FileMetadataHandler(!showAll.booleanValue());
                    }
                    handler.load(imageFile);
                }
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
    }

    protected String getImageFile() {
        return imageFile;
    }

    public String[] getMetadataNames() {
        return handler!=null?handler.getNames():new String[0];
    }

    public String getMetadataValue(String name) {
        return handler!=null?handler.getValue(name):null;
    }

    public String getMetadataDescription(String name) {
        return handler!=null?handler.getDescription(name):null;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getComment() {
        if (picture != null && comment == null) {
            PictureComment template = (PictureComment) environment.getEntityFactory().create("dirgallery-picturecomment");
            template.setId(picture.getFullPath());
            PictureComment pictureComment = (PictureComment) environment.getEntityStorageFactory().getStorage("dirgallery-picturecomment").load(template);
            if (pictureComment != null) {
                comment = pictureComment.getComment();
            }
        }
        return comment;
    }
}