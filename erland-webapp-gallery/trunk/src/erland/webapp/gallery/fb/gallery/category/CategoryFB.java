package erland.webapp.gallery.fb.gallery.category;

import org.apache.struts.validator.ValidatorForm;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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

public class CategoryFB extends ValidatorForm {
    private Boolean official;
    private String officialDisplay;
    private Boolean forcedOfficial;
    private String forcedOfficialDisplay;
    private Boolean officialAlways;
    private String officialAlwaysDisplay;
    private Boolean visible;
    private String visibleDisplay;
    private Integer category;
    private String categoryDisplay;
    private Integer gallery;
    private String galleryDisplay;
    private String name;

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return officialDisplay;
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.officialDisplay = officialDisplay;
    }

    public Boolean getOfficialAlways() {
        return officialAlways;
    }

    public void setOfficialAlways(Boolean officialAlways) {
        this.officialAlways = officialAlways;
    }

    public String getOfficialAlwaysDisplay() {
        return officialAlwaysDisplay;
    }

    public void setOfficialAlwaysDisplay(String officialAlwaysDisplay) {
        this.officialAlwaysDisplay = officialAlwaysDisplay;
    }

    public Boolean getForcedOfficial() {
        return forcedOfficial;
    }

    public void setForcedOfficial(Boolean forcedOfficial) {
        this.forcedOfficial = forcedOfficial;
    }

    public String getForcedOfficialDisplay() {
        return forcedOfficialDisplay;
    }

    public void setForcedOfficialDisplay(String forcedOfficialDisplay) {
        this.forcedOfficialDisplay = forcedOfficialDisplay;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getVisibleDisplay() {
        return visibleDisplay;
    }

    public void setVisibleDisplay(String visibleDisplay) {
        this.visibleDisplay = visibleDisplay;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDisplay() {
        return categoryDisplay;
    }

    public void setCategoryDisplay(String categoryDisplay) {
        this.categoryDisplay = categoryDisplay;
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return galleryDisplay;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.galleryDisplay = galleryDisplay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}