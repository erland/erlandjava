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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class ViewCategoryCommand implements CommandInterface, ViewCategoryInterface {
    private WebAppEnvironmentInterface environment;
    private Category category;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
        String categoryString = request.getParameter("category");
        Integer categoryId = null;
        if(categoryString!=null && categoryString.length()>0) {
            categoryId = Integer.valueOf(categoryString);
        }
        if(gallery!=null && categoryId!=null) {
            Category template = (Category) environment.getEntityFactory().create("category");
            template.setGallery(gallery);
            template.setCategory(categoryId);
            category = (Category) environment.getEntityStorageFactory().getStorage("category").load(template);
            Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
            if(category!=null) {
                category.setGallery(virtualGalleryId);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public Category getCategory() {
        return category;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}