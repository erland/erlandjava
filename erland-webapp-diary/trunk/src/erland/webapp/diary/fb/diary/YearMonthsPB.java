package erland.webapp.diary.fb.diary;

import org.apache.struts.action.ActionForm;

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

public class YearMonthsPB extends ActionForm {
    KeyLinkPB[] years;
    KeyLinkPB[] months;

    public KeyLinkPB[] getYears() {
        return years;
    }

    public void setYears(KeyLinkPB[] years) {
        this.years = years;
    }

    public KeyLinkPB[] getMonths() {
        return months;
    }

    public void setMonths(KeyLinkPB[] months) {
        this.months = months;
    }
}