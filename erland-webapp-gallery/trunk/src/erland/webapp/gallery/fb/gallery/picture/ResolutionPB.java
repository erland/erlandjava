package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.action.ActionForm;
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

public class ResolutionPB extends BasePB {
    private String id;
    private String description;
    private Integer width;

    public ResolutionPB() {}

    public ResolutionPB(String id, String description, Integer width) {
        this.id = id;
        this.description = description;
        this.width = width;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getWidthDisplay() {
        return ServletParameterHelper.asString(width,null);
    }

    public void setWidthDisplay(String widthDisplay) {
        this.width = ServletParameterHelper.asInteger(widthDisplay,null);
    }
}