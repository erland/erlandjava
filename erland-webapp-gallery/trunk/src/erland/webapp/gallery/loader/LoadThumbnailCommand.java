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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.image.ImageThumbnail;
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.gallery.account.UserAccount;
import erland.webapp.gallery.gallery.picture.Picture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoadThumbnailCommand extends LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private UserAccount account;

    protected String getImageFileName(Picture picture) {
        return picture.getImage().substring(1, picture.getImage().length() - 1);
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        Integer requestedWidth = ServletParameterHelper.asInteger(request.getParameter("width"), null);
        Boolean useCache = ServletParameterHelper.asBoolean(request.getParameter("usecache"), Boolean.TRUE);
        Float requestedCompression = ServletParameterHelper.asFloat(request.getParameter("compression"), null);
        try {
            if (!ImageWriteHelper.writeThumbnail(getEnvironment(), requestedWidth, useCache, requestedCompression, getUsername(), getImageFile(), getCopyrightText(), new ImageThumbnail(), response.getOutputStream())) {
                request.getRequestDispatcher("thumbnailna.gif").forward(request, response);
            }
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private UserAccount getUserAccount() {
        if (account == null) {
            UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
            template.setUsername(getUsername());
            account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").load(template);
        }
        return account;
    }

    protected String getCopyrightText() {
        UserAccount account = getUserAccount();
        if (account != null) {
            return account.getCopyrightText();
        }
        return null;
    }
}