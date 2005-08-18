package erland.webapp.diary.fb.diary;

import org.apache.struts.validator.ValidatorForm;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

import java.util.Date;

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

public class SelectDiaryEntryFB extends SelectDiaryFB {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return StringUtil.asString(date,null);
    }

    public void setDateDisplay(String dateDisplay) {
        this.date = StringUtil.asDate(dateDisplay,null);
    }

}