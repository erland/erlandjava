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

import erland.webapp.common.image.*;
import erland.webapp.dirgallery.entity.account.UserAccount;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.loader.ThumbnailImageFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoadThumbnailAction extends LoadImageAction {
    private static final int THUMBNAIL_WIDTH = 150;
    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ThumbnailImageFB fb = (ThumbnailImageFB) form;
        Integer width = getWidth(request, fb);
        Boolean useCache = fb.getUseCache();
        if(useCache==null) {
            if(width==null||width.floatValue()<=THUMBNAIL_WIDTH) {
                useCache=Boolean.TRUE;
            }else {
                useCache=getGallery(request).getUseCacheLargeImages();
            }
        }
        try {
            response.setContentType("image/jpeg");
            if (!ImageWriteHelper.writeThumbnail(getEnvironment(), width, useCache, fb.getCompression(), getUsername(request), getImageFile(request), getCopyright(getGallery(request),getUsername(request)), new ImageThumbnail(), response.getOutputStream())) {
                return findFailure(mapping, form, request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    private UserAccount getUserAccount(String username) {
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("dirgallery-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-useraccount").load(template);
        return account;
    }

    protected CopyrightCreatorInterface getCopyright(Gallery gallery, String username) {
        if(gallery.getUseCopyright().booleanValue()) {
            if(gallery.getCopyrightText()==null|| gallery.getCopyrightText().length()==0) {
                UserAccount account = getUserAccount(username);
                if (account != null) {
                    return new Copyright(account.getCopyrightText(),CopyrightPosition.fromId(gallery.getCopyrightPosition().intValue()),gallery.getCopyrightTransparency().floatValue());
                }
            }else {
                return new Copyright(gallery.getCopyrightText(),CopyrightPosition.fromId(gallery.getCopyrightPosition().intValue()),gallery.getCopyrightTransparency().floatValue());
            }
        }
        return null;
    }

    protected Integer getWidth(HttpServletRequest request, ThumbnailImageFB fb) {
        Integer width = fb.getWidth();
        if (getGallery(request).getMaxWidth() != null && getGallery(request).getMaxWidth().intValue() > 0 && width != null && getGallery(request).getMaxWidth().compareTo(width) < 0) {
            width = getGallery(request).getMaxWidth();
        }
        return width;
    }

    protected boolean isDownloadAllowed(String username, Gallery gallery) {
        return true;
    }
}