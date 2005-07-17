package erland.webapp.tvguide.fb.movie;

import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

public class SelectMovieCoverFB extends BaseFB {
    String id;
    String sizeDisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSizeDisplay() {
        return sizeDisplay;
    }

    public void setSizeDisplay(String sizeDisplay) {
        this.sizeDisplay = sizeDisplay;
    }

    public Integer getSize() {
        return StringUtil.asInteger(sizeDisplay,null);
    }
    public void setSize(Integer size) {
        this.sizeDisplay = StringUtil.asString(size,null);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        id = null;
        sizeDisplay = null;
    }
}
