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

import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.common.image.MovieThumbnail;
import erland.webapp.common.image.ThumbnailCreatorInterface;
import erland.webapp.common.image.ImageThumbnail;
import erland.webapp.dirgallery.fb.loader.MovieThumbnailImageFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class LoadMovieThumbnailAction extends LoadThumbnailAction {
    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        MovieThumbnailImageFB fb = (MovieThumbnailImageFB) form;
        Integer width = getWidth(request, fb);
        try {
            response.setContentType("image/jpeg");
            String imageFile = getImageFile(request);
            ThumbnailCreatorInterface thumbnailCreator = new ImageThumbnail();
            int pos = imageFile.lastIndexOf(".");
            if(pos>=0) {
                imageFile = imageFile.substring(0,pos)+".jpg";
                File file = new File(imageFile);
                if (!file.exists()) {
                    try {
                        URLConnection connection = new URL(imageFile).openConnection();
                    } catch (IOException e) {
                        imageFile = getImageFile(request);
                        thumbnailCreator = new MovieThumbnail(fb.getCols().intValue(), fb.getRows().intValue());
                    }
                }
            }
            if (!ImageWriteHelper.writeThumbnail(getEnvironment(), width, fb.getUseCache(), fb.getCompression(), getUsername(request), imageFile, getCopyrightText(getUsername(request)), thumbnailCreator, response.getOutputStream())) {
                return findFailure(mapping, form, request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }
}