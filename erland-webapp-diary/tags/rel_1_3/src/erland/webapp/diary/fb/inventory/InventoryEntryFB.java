package erland.webapp.diary.fb.inventory;

import org.apache.struts.action.ActionMapping;
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

public class InventoryEntryFB extends BaseFB {
    private Integer id;
    private Integer type;
    private String typeDescription;
    private Integer sex;
    private String sexDescription;
    private String name;
    private String description;
    private String image;
    private String largeImage;
    private String link;
    private String linkSource;
    private Integer gallery;
    private String galleryLink;
    private String updateLink;
    private String deleteLink;
    private String viewLink;
    private String newEventLink;
    private String containerLink;
    private String currentContainerDescription;
    private String currentSizeText;
    private InventoryEntryEventFB[] events;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDisplay() {
        return ServletParameterHelper.asString(type,null);
    }

    public void setTypeDisplay(String typeDisplay) {
        this.type = ServletParameterHelper.asInteger(typeDisplay,null);
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexDisplay() {
        return ServletParameterHelper.asString(sex,null);
    }

    public void setSexDisplay(String sexDisplay) {
        this.sex = ServletParameterHelper.asInteger(sexDisplay,null);
    }

    public String getSexDescription() {
        return sexDescription;
    }

    public void setSexDescription(String sexDescription) {
        this.sexDescription = sexDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return ServletParameterHelper.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = ServletParameterHelper.asInteger(galleryDisplay,null);
    }

    public String getGalleryLink() {
        return galleryLink;
    }

    public void setGalleryLink(String galleryLink) {
        this.galleryLink = galleryLink;
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

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getNewEventLink() {
        return newEventLink;
    }

    public void setNewEventLink(String newEventLink) {
        this.newEventLink = newEventLink;
    }

    public String getContainerLink() {
        return containerLink;
    }

    public void setContainerLink(String containerLink) {
        this.containerLink = containerLink;
    }

    public InventoryEntryEventFB[] getEvents() {
        return events;
    }

    public void setEvents(InventoryEntryEventFB[] events) {
        this.events = events;
    }

    public String getCurrentContainerDescription() {
        return currentContainerDescription;
    }

    public void setCurrentContainerDescription(String currentContainerDescription) {
        this.currentContainerDescription = currentContainerDescription;
    }

    public String getCurrentSizeText() {
        return currentSizeText;
    }

    public void setCurrentSizeText(String currentSizeText) {
        this.currentSizeText = currentSizeText;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        type = null;
        typeDescription = null;
        sex = null;
        sexDescription = null;
        name = null;
        description = null;
        image = null;
        largeImage = null;
        link = null;
        linkSource = null;
        gallery = null;
        events = null;
        galleryLink = null;
        updateLink = null;
        deleteLink = null;
        viewLink = null;
        newEventLink = null;
        containerLink = null;
        currentContainerDescription = null;
        currentSizeText = null;
    }
}