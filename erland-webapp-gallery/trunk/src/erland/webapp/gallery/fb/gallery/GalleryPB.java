package erland.webapp.gallery.fb.gallery;

import erland.webapp.gallery.fb.gallery.category.CategoryPB;
import erland.webapp.common.fb.BasePB;
import erland.webapp.common.ServletParameterHelper;

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

public class GalleryPB extends BasePB {
    private Integer id;
    private String title;
    private String description;
    private Boolean virtual;
    private CategoryPB[] categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,"");
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryPB[] getCategories() {
        return categories;
    }

    public void setCategories(CategoryPB[] categories) {
        this.categories = categories;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public String getVirtualDisplay() {
        return ServletParameterHelper.asString(virtual,null);
    }

    public void setVirtualDisplay(String virtualDisplay) {
        this.virtual = ServletParameterHelper.asBoolean(virtualDisplay,Boolean.FALSE);
    }
}