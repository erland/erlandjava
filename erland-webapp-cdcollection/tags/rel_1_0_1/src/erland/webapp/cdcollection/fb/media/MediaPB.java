package erland.webapp.cdcollection.fb.media;

import erland.webapp.cdcollection.fb.media.disc.DiscPB;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

public class MediaPB extends MediaFB {
    private String coverLink;
    private String viewLink;
    private String updateLink;
    private String removeLink;
    private String newDiscLink;
    private String importDiscLink;
    private String importLink;
    private DiscPB[] discs;

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getRemoveLink() {
        return removeLink;
    }

    public void setRemoveLink(String removeLink) {
        this.removeLink = removeLink;
    }

    public String getNewDiscLink() {
        return newDiscLink;
    }

    public void setNewDiscLink(String newDiscLink) {
        this.newDiscLink = newDiscLink;
    }

    public String getImportDiscLink() {
        return importDiscLink;
    }

    public void setImportDiscLink(String importDiscLink) {
        this.importDiscLink = importDiscLink;
    }

    public DiscPB[] getDiscs() {
        return discs;
    }

    public void setDiscs(DiscPB[] discs) {
        this.discs = discs;
    }

    public String getImportLink() {
        return importLink;
    }

    public void setImportLink(String importLink) {
        this.importLink = importLink;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }
}