package erland.webapp.diary.fb.inventory;

import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

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

public class InventoryEntryAndEventFB extends InventoryEntryFB {
    private Double eventSize;
    private Date eventDate;
    private Integer eventDescription;
    private Integer container;
    private String containerDisplay;

    public Double getEventSize() {
        return eventSize;
    }

    public void setEventSize(Double eventSize) {
        this.eventSize = eventSize;
    }

    public String getEventSizeDisplay() {
        return StringUtil.asString(eventSize,null);
    }

    public void setEventSizeDisplay(String eventSizeDisplay) {
        this.eventSize = StringUtil.asDouble(eventSizeDisplay,null);
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDateDisplay() {
        return StringUtil.asString(eventDate,null);
    }

    public void setEventDateDisplay(String eventDateDisplay) {
        this.eventDate = StringUtil.asDate(eventDateDisplay,null);
    }

    public Integer getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(Integer eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDescriptionDisplay() {
        return StringUtil.asString(eventDescription,null);
    }

    public void setEventDescriptionDisplay(String eventDescriptionDisplay) {
        this.eventDescription = StringUtil.asInteger(eventDescriptionDisplay,null);
    }

    public Integer getContainer() {
        return container;
    }

    public void setContainer(Integer container) {
        this.container = container;
    }

    public String getContainerDisplay() {
        return StringUtil.asString(container,null);
    }

    public void setContainerDisplay(String containerDisplay) {
        this.container = StringUtil.asInteger(containerDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        eventDate = null;
        eventDescription = null;
        eventSize = null;
        container = null;
    }
}