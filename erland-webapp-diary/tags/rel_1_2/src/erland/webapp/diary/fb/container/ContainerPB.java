package erland.webapp.diary.fb.container;

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

public class ContainerPB extends ContainerFB {
    private String updateLink;
    private String deleteLink;
    private String galleryLink;
    private String inventoryLink;

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

    public String getGalleryLink() {
        return galleryLink;
    }

    public void setGalleryLink(String galleryLink) {
        this.galleryLink = galleryLink;
    }

    public String getInventoryLink() {
        return inventoryLink;
    }

    public void setInventoryLink(String inventoryLink) {
        this.inventoryLink = inventoryLink;
    }
}