package erland.webapp.gallery.fb.gallery;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.gallery.fb.account.SelectUserFB;
import erland.util.StringUtil;

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

public class SelectGalleryFB extends SelectUserFB {
    private String guestUser;
    private Integer gallery;

    public String getGuestUser() {
        return guestUser;
    }

    public void setGuestUser(String guestUser) {
        this.guestUser = guestUser;
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return StringUtil.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = StringUtil.asInteger(galleryDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {
        super.reset(actionMapping, servletRequest);
        gallery=null;
        guestUser = null;
    }
}