package erland.webapp.common.fb;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BasePB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class DescriptionIdPB extends BasePB {
    private String idDisplay;
    private String description;

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}