package erland.webapp.diary.fb.inventory;

import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.common.ServletParameterHelper;

import java.util.Date;

import org.apache.struts.action.ActionMapping;

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

public class SelectInventoryFB extends SelectUserFB {
    private Date date;
    private Integer container;
    private String containerDisplay;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return ServletParameterHelper.asString(date,null);
    }

    public void setDateDisplay(String dateDisplay) {
        this.date = ServletParameterHelper.asDate(dateDisplay,null);
    }

    public Integer getContainer() {
        return container;
    }

    public void setContainer(Integer container) {
        this.container = container;
    }

    public String getContainerDisplay() {
        return ServletParameterHelper.asString(container,null);
    }

    public void setContainerDisplay(String containerDisplay) {
        this.container = ServletParameterHelper.asInteger(containerDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        date = null;
        container = null;
    }
}