package erland.webapp.homepage.fb.section;

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

import erland.webapp.homepage.fb.account.SelectUserFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SelectSectionFB extends SelectUserFB {
    private String sectionDisplay;

    public Integer getSection() {
        return StringUtil.asInteger(sectionDisplay,null);
    }

    public void setSection(Integer sectionDisplay) {
        this.sectionDisplay = StringUtil.asString(sectionDisplay,null);
    }
    public String getSectionDisplay() {
        return sectionDisplay;
    }

    public void setSectionDisplay(String sectionDisplay) {
        this.sectionDisplay = sectionDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        sectionDisplay = null;
    }
}