package erland.webapp.gallery.gallery.category;
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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.BaseEntity;

public class Category extends BaseEntity {
    private Integer gallery;
    private Integer category;
    private Integer parentCategory;
    private String name;
    private Boolean official;
    private Boolean officialVisible;
    private Boolean officialAlways;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Integer parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Boolean getOfficialVisible() {
        return officialVisible;
    }

    public void setOfficialVisible(Boolean officialVisible) {
        this.officialVisible = officialVisible;
    }

    public Boolean getOfficialAlways() {
        return officialAlways;
    }

    public void setOfficialAlways(Boolean officialAlways) {
        this.officialAlways = officialAlways;
    }
}