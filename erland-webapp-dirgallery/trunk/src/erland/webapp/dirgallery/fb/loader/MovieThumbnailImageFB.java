package erland.webapp.dirgallery.fb.loader;

import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

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

public class MovieThumbnailImageFB extends ThumbnailImageFB {
    private Integer cols;
    private Integer rows;

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public String getColsDisplay() {
        return StringUtil.asString(cols, null);
    }

    public void setColsDisplay(String colsDisplay) {
        this.cols = StringUtil.asInteger(colsDisplay, null);
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getRowsDisplay() {
        return StringUtil.asString(rows, null);
    }

    public void setRowsDisplay(String rowsDisplay) {
        this.rows = StringUtil.asInteger(rowsDisplay, null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        cols = new Integer(2);
        rows = new Integer(2);
    }
}