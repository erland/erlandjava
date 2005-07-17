package erland.webapp.tvguide.fb.exclusion;

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

import erland.util.StringUtil;
import erland.webapp.tvguide.fb.account.SelectUserFB;

import java.util.Date;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SelectExclusionFB extends SelectUserFB {
    private String exclusionDisplay;

    public Integer getExclusion() {
        return StringUtil.asInteger(exclusionDisplay,null);
    }

    public void setExclusion(Integer exclusion) {
        this.exclusionDisplay = StringUtil.asString(exclusion,null);
    }

    public String getExclusionDisplay() {
        return exclusionDisplay;
    }

    public void setExclusionDisplay(String exclusionDisplay) {
        this.exclusionDisplay = exclusionDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        exclusionDisplay = null;
    }
}
