package erland.webapp.tvguide.fb.program;

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

import erland.webapp.tvguide.fb.account.SelectUserFB;
import erland.util.StringUtil;

import java.util.Date;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SelectProgramFB extends SelectUserFB {
    private String dateDisplay;
    private String dayOffsetDisplay;

    public Date getDate() {
        return StringUtil.asDate(dateDisplay,null);
    }

    public void setDate(Date date) {
        this.dateDisplay = StringUtil.asString(date,null);
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public Integer getDayOffset() {
        return StringUtil.asInteger(dayOffsetDisplay,null);
    }

    public void setDayOffset(Integer dayOffset) {
        this.dayOffsetDisplay = StringUtil.asString(dayOffset,null);
    }

    public String getDayOffsetDisplay() {
        return dayOffsetDisplay;
    }

    public void setDayOffsetDisplay(String dayOffsetDisplay) {
        this.dayOffsetDisplay = dayOffsetDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        dateDisplay = null;
        dayOffsetDisplay = null;
    }
}
