package erland.webapp.diary.fb.gallery;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import erland.webapp.common.ServletParameterHelper;

import javax.servlet.http.HttpServletRequest;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class GalleryPB extends GalleryFB {
    private String updateLink;
    private String removeLink;
    private String newEntryLink;

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getRemoveLink() {
        return removeLink;
    }

    public void setRemoveLink(String removeLink) {
        this.removeLink = removeLink;
    }

    public String getNewEntryLink() {
        return newEntryLink;
    }

    public void setNewEntryLink(String newEntryLink) {
        this.newEntryLink = newEntryLink;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        updateLink = null;
        removeLink = null;
        newEntryLink = null;
    }
}