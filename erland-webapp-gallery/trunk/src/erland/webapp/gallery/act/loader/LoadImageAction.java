package erland.webapp.gallery.act.loader;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.entity.guestaccount.GuestAccount;
import erland.webapp.gallery.fb.loader.ImageFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LoadImageAction extends BaseAction {
    private final static String GALLERY = LoadImageAction.class+"-gallery";
    private final static String PICTURE = LoadImageAction.class+"-picture";
    private final static String IMAGE_FILE = LoadImageAction.class+"-imageFile";
    private final static String USERNAME = LoadImageAction.class+"-username";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImageFB fb = (ImageFB) form;
        GalleryInterface gallery = GalleryHelper.getGallery(getEnvironment(),fb.getGallery());
        setGallery(request,gallery);
        Integer galleryId = GalleryHelper.getGalleryId(gallery);
        Picture template = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        template.setGallery(galleryId);
        template.setId(fb.getImage());
        Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").load(template);
        setPicture(request,picture);
        if (picture != null) {
            /*
            String username = request.getParameter("user");
            if (username == null || username.length() == 0) {
                User user = (User) request.getSession().getAttribute("user");
                username = user.getUsername();
            }
            setUsername(username);
            */
            if (gallery == null) {
                saveErrors(request, Arrays.asList(new String[]{"gallery.loader.gallery-dont-exist"}));
                return;
            }
            setUsername(request,gallery.getUsername());
            if (!picture.getOfficial().booleanValue()) {
                if (request.getRemoteUser() != null && request.getRemoteUser().equals(gallery.getUsername())) {
                    //OK, correct user logged in
                }else if(request.getRemoteUser() != null) {
                    GuestAccount guestTemplate = (GuestAccount) getEnvironment().getEntityFactory().create("gallery-guestaccount");
                    guestTemplate.setUsername(gallery.getUsername());
                    guestTemplate.setGuestUser(request.getRemoteUser());
                    EntityInterface entity = getEnvironment().getEntityStorageFactory().getStorage("gallery-guestaccount").load(guestTemplate);
                    if(entity!=null) {
                        //OK, authorized guest user logged in
                    }else {
                        saveErrors(request, Arrays.asList(new String[]{"gallery.loader.invalid-user"}));
                        return;
                    }
                }else {
                    saveErrors(request, Arrays.asList(new String[]{"gallery.loader.invalid-user"}));
                    return;
                }
                /*
                TODO: Implement handling for guest users
                else if (request.getRemoteUser() == null) {
                    User guestUser = (User) request.getSession().getAttribute("guestuser");
                    if (guestUser == null || !username.equals(gallery.getUsername()) || !GuestAccountHelper.isGuestUser(getEnvironment(), username, guestUser.getUsername())) {
                        return null;
                    }
                }
                */
            }
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username", getUsername(request));
            EntityInterface[] storageEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);

            String imageFile = getImageFileName(picture);
            for (int j = 0; j < storageEntities.length; j++) {
                PictureStorage storage = (PictureStorage) storageEntities[j];
                if (imageFile.startsWith(storage.getName())) {
                    imageFile = storage.getPath() + imageFile.substring(storage.getName().length());
                    break;
                }
            }
            setImageFile(request,imageFile);
        }
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1, picture.getLink().length() - 1);
    }

    protected void setImageFile(HttpServletRequest request, String imageFile) {
        request.setAttribute(IMAGE_FILE,imageFile);
    }

    public String getImageFile(HttpServletRequest request) {
        return (String)request.getAttribute(IMAGE_FILE);
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            if (!ImageWriteHelper.writeImage(getEnvironment(), getImageFile(request), response.getOutputStream())) {
                return findFailure(mapping,form,request,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUsername(HttpServletRequest request) {
        return (String)request.getAttribute(USERNAME);
    }

    public void setUsername(HttpServletRequest request, String username) {
        request.setAttribute(USERNAME,username);
    }

    public GalleryInterface getGallery(HttpServletRequest request) {
        return (GalleryInterface) request.getAttribute(GALLERY);
    }

    public void setGallery(HttpServletRequest request, GalleryInterface gallery) {
        request.setAttribute(GALLERY,gallery);
    }
    public Picture getPicture(HttpServletRequest request) {
        return (Picture) request.getAttribute(PICTURE);
    }
    public void setPicture(HttpServletRequest request, Picture picture) {
        request.setAttribute(PICTURE,picture);
    }
}