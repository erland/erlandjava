package erland.webapp.diary.fb.diary;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.diary.fb.account.SelectUserFB;

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

public class SelectYearMonthFB extends SelectUserFB {
    Integer year;
    String yearDisplay;
    Integer month;
    String monthDisplay;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getYearDisplay() {
        return ServletParameterHelper.asString(year,null);
    }

    public void setYearDisplay(String yearDisplay) {
        this.year = ServletParameterHelper.asInteger(yearDisplay,null);
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getMonthDisplay() {
        return ServletParameterHelper.asString(month,null);
    }

    public void setMonthDisplay(String monthDisplay) {
        this.month = ServletParameterHelper.asInteger(monthDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        year = null;
        month = null;
    }
}