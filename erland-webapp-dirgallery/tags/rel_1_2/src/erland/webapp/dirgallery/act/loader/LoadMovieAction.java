package erland.webapp.dirgallery.act.loader;

import javax.servlet.http.HttpServletRequest;

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



public class LoadMovieAction extends LoadImageAction {
    protected String getContentType(HttpServletRequest request) {
        String imageFile = getImageFile(request);
        int pos = imageFile.lastIndexOf(".");
        if(pos>=0) {
            String extension = imageFile.substring(pos);
            if(extension.equalsIgnoreCase(".avi")) {
                return "video/avi";
            }else if(extension.equalsIgnoreCase(".mov")) {
                return "video/quicktime";
            }
        }
        return "video/mpeg";
    }
}