package erland.webapp.gallery.fb.gallery;

import org.apache.struts.validator.ValidatorForm;
import erland.webapp.gallery.fb.gallery.category.CategoryPB;

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

public class CategoryMenuItemPB extends GalleryMenuItemPB {
    private Integer category;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDisplay() {
        return category!=null?category.toString():null;
    }

    public void setCategoryDisplay(String categoryDisplay) {
        try {
            this.category = Integer.valueOf(categoryDisplay);
        } catch (NumberFormatException e) {
            this.category = null;
        }
    }
}