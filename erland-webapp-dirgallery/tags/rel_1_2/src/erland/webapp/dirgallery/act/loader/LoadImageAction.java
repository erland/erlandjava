package erland.webapp.dirgallery.act.loader;

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
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.dirgallery.entity.gallery.picture.Picture;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.loader.ImageFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LoadImageAction extends BaseAction {
    private final static String GALLERY = LoadImageAction.class + "-gallery";
    private final static String PICTURE = LoadImageAction.class + "-picture";
    private final static String IMAGE_FILE = LoadImageAction.class + "-imageFile";
    private final static String USERNAME = LoadImageAction.class + "-username";
    private final static String FORWARD = LoadImageAction.class + "-failure-forward";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImageFB fb = (ImageFB) form;
        Gallery templateGallery = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        templateGallery.setId(fb.getGallery());
        Gallery entity = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(templateGallery);
        setUsername(request, entity.getUsername());
        setGallery(request, entity);
        if (entity == null) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.loader.gallery-dont-exist"}));
            return;
        }
        if (!isDownloadAllowed(request.getRemoteUser(), entity)) {
            setForward(request, "forward-to-thumbnail");
            return;
        }
        Picture template = (Picture) getEnvironment().getEntityFactory().create("dirgallery-picture");
        template.setGallery(fb.getGallery());
        template.setDirectory(getEnvironment().getConfigurableResources().getParameter("basedirectory") + "/" + entity.getUsername() + "/" + entity.getDirectory());
        template.setId(fb.getImage());
        Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picture").load(template);
        if (picture != null) {
            if (!entity.getOfficial().booleanValue()) {
                if (!entity.getUsername().equals(request.getRemoteUser())) {
                    saveErrors(request, Arrays.asList(new String[]{"dirgallery.loader.unauthorized-access"}));
                    return;
                }
            }

            setImageFile(request, getImageFileName(picture));
        }
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        if (getForward(request) != null) {
            ActionForward forward = mapping.findForward(getForward(request));
            if (forward != null) {
                return forward;
            }
        }
        try {
            response.setContentType(getContentType(request));
            if (!ImageWriteHelper.writeImage(getEnvironment(), getImageFile(request), response.getOutputStream())) {
                return findFailure(mapping, form, request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
    }

    protected void setImageFile(HttpServletRequest request, String imageFile) {
        request.setAttribute(IMAGE_FILE, imageFile);
    }

    public String getImageFile(HttpServletRequest request) {
        return (String) request.getAttribute(IMAGE_FILE);
    }

    public String getUsername(HttpServletRequest request) {
        return (String) request.getAttribute(USERNAME);
    }

    public void setUsername(HttpServletRequest request, String username) {
        request.setAttribute(USERNAME, username);
    }

    public Gallery getGallery(HttpServletRequest request) {
        return (Gallery) request.getAttribute(GALLERY);
    }

    public void setGallery(HttpServletRequest request, Gallery gallery) {
        request.setAttribute(GALLERY, gallery);
    }

    public Picture getPicture(HttpServletRequest request) {
        return (Picture) request.getAttribute(PICTURE);
    }

    public void setPicture(HttpServletRequest request, Picture picture) {
        request.setAttribute(PICTURE, picture);
    }

    public String getForward(HttpServletRequest request) {
        return (String) request.getAttribute(FORWARD);
    }

    public void setForward(HttpServletRequest request, String failureForward) {
        request.setAttribute(FORWARD, failureForward);
    }

    protected String getContentType(HttpServletRequest request) {
        return "image/jpeg";
    }

    protected boolean isDownloadAllowed(String username, Gallery gallery) {
        return gallery.getUsername().equals(username) || gallery.getOriginalDownloadable().booleanValue();
    }
}