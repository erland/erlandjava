package erland.webapp.gallery.fb.gallery;

import erland.webapp.gallery.fb.gallery.category.CategoryPB;
import erland.webapp.common.fb.BasePB;
import erland.webapp.common.ServletParameterHelper;
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

public class GalleryPB extends GalleryFB {
    private Boolean virtual;
    private Integer noOfThumnailInfoRows;

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public String getVirtualDisplay() {
        return StringUtil.asString(virtual,null);
    }

    public void setVirtualDisplay(String virtualDisplay) {
        this.virtual = StringUtil.asBoolean(virtualDisplay,Boolean.FALSE);
    }

    public Integer getNoOfThumnailInfoRows() {
        return noOfThumnailInfoRows;
    }

    public void setNoOfThumnailInfoRows(Integer noOfThumnailInfoRows) {
        this.noOfThumnailInfoRows = noOfThumnailInfoRows;
    }

    public String getNoOfThumnailInfoRowsDisplay() {
        return StringUtil.asString(noOfThumnailInfoRows,null);
    }

    public void setNoOfThumnailInfoRowsDisplay(String noOfThumnailInfoRowsDisplay) {
        this.noOfThumnailInfoRows = StringUtil.asInteger(noOfThumnailInfoRowsDisplay,null);
    }
}