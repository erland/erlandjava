package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.gallery.fb.gallery.category.SelectCategoryFB;
import erland.webapp.common.ServletParameterHelper;

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

public class SelectPictureFB extends SelectCategoryFB {
    private Integer id;
    private Integer start;
    private Integer max;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getStartDisplay() {
        return ServletParameterHelper.asString(start,null);
    }

    public void setStartDisplay(String startDisplay) {
        this.start=ServletParameterHelper.asInteger(startDisplay,null);
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getMaxDisplay() {
        return ServletParameterHelper.asString(max,null);
    }

    public void setMaxDisplay(String maxDisplay) {
        this.max = ServletParameterHelper.asInteger(maxDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        max = new Integer(9);
        start = new Integer(0);
        id=null;
    }
}