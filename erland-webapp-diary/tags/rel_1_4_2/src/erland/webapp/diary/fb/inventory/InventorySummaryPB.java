package erland.webapp.diary.fb.inventory;

import org.apache.struts.action.ActionMapping;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.fb.BasePB;
import erland.util.StringUtil;

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

public class InventorySummaryPB extends BasePB {
    private String name;
    private String viewLink;
    private Integer numberOf;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(Integer numberOf) {
        this.numberOf = numberOf;
    }

    public String getNumberOfDisplay() {
        return StringUtil.asString(numberOf,null);
    }

    public void setNumberOfDisplay(String numberOfDisplay) {
        this.numberOf = StringUtil.asInteger(numberOfDisplay,null);
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }
}