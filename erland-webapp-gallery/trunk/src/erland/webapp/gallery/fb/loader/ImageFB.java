package erland.webapp.gallery.fb.loader;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

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

public class ImageFB extends ValidatorForm {
    private Integer image;
    private Integer gallery;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getImageDisplay() {
        return image!=null?image.toString():null;
    }

    public void setImageDisplay(String imageDisplay) {
        try {
            this.image = Integer.valueOf(imageDisplay);
        } catch (NumberFormatException e) {
            this.image = null;
        }
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return gallery!=null?gallery.toString():null;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        try {
            this.gallery = Integer.valueOf(galleryDisplay);
        } catch (NumberFormatException e) {
            this.gallery = null;
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        gallery = null;
        image = null;
    }
}