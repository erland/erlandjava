package erland.webapp.diary.fb.inventory;

import org.apache.struts.action.ActionMapping;

import java.util.Date;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;

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

public class InventoryEntryEventFB extends BaseFB {
    private Integer id;
    private Integer eventId;
    private Integer description;
    private String descriptionText;
    private Double size;
    private String sizeText;
    private Date date;
    private String updateLink;
    private String deleteLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventIdDisplay() {
        return ServletParameterHelper.asString(eventId,null);
    }

    public void setEventIdDisplay(String eventIdDisplay) {
        this.eventId = ServletParameterHelper.asInteger(eventIdDisplay,null);
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public String getDescriptionDisplay() {
        return ServletParameterHelper.asString(description,null);
    }

    public void setDescriptionDisplay(String descriptionDisplay) {
        this.description = ServletParameterHelper.asInteger(descriptionDisplay,null);
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getSizeDisplay() {
        return ServletParameterHelper.asString(size,null);
    }

    public void setSizeDisplay(String sizeDisplay) {
        this.size = ServletParameterHelper.asDouble(sizeDisplay,null);
    }

    public String getSizeText() {
        return sizeText;
    }

    public void setSizeText(String sizeText) {
        this.sizeText = sizeText;
    }

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

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getDeleteLink() {
        return deleteLink;
    }

    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        eventId = null;
        description = null;
        size = null;
        sizeText = null;
        date = null;
        updateLink = null;
        deleteLink = null;
    }
}