package erland.webapp.diary.fb.inventory;

import erland.webapp.diary.fb.species.SpeciesPB;

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

public class InventoryEntryPB extends InventoryEntryFB {
    private String typeDescription;
    private String sexDescription;
    private String linkSource;
    private String galleryLink;
    private String updateLink;
    private String deleteLink;
    private String viewLink;
    private String newEventLink;
    private String containerLink;
    private String currentContainerDescription;
    private String currentSizeText;
    private InventoryEntryEventPB[] events;
    private SpeciesPB speciesInfo;

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
    public String getSexDescription() {
        return sexDescription;
    }

    public void setSexDescription(String sexDescription) {
        this.sexDescription = sexDescription;
    }
    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
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

    public InventoryEntryEventPB[] getEvents() {
        return events;
    }

    public void setEvents(InventoryEntryEventPB[] events) {
        this.events = events;
    }

    public SpeciesPB getSpeciesInfo() {
        return speciesInfo;
    }

    public void setSpeciesInfo(SpeciesPB speciesInfo) {
        this.speciesInfo = speciesInfo;
    }
}
