package erland.webapp.gallery.fb.loader;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.fb.BasePB;
import erland.webapp.common.ServletParameterHelper;

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

public class MetadataCollectionPB extends BasePB {
    private MetadataPB[] items;
    private Boolean showAll;
    private String showSelectedLink;
    private String showAllLink;

    public MetadataPB[] getItems() {
        return items;
    }

    public void setItems(MetadataPB[] items) {
        this.items = items;
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    public String getShowAllDisplay() {
        return ServletParameterHelper.asString(showAll,null);
    }

    public void setShowAllDisplay(String showAllDisplay) {
        this.showAll = ServletParameterHelper.asBoolean(showAllDisplay,Boolean.FALSE);
    }

    public String getShowSelectedLink() {
        return showSelectedLink;
    }

    public void setShowSelectedLink(String showSelectedLink) {
        this.showSelectedLink = showSelectedLink;
    }

    public String getShowAllLink() {
        return showAllLink;
    }

    public void setShowAllLink(String showAllLink) {
        this.showAllLink = showAllLink;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        showAll = Boolean.FALSE;
        showSelectedLink = null;
        showAllLink = null;
    }
}