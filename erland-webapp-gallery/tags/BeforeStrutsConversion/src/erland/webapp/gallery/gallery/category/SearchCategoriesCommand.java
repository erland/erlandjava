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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.Gallery;
import erland.webapp.gallery.gallery.GalleryInterface;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class SearchCategoriesCommand implements CommandInterface, ViewCategoriesInterface {
    private WebAppEnvironmentInterface environment;
    private Category[] categories;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        initCommand(request);
        Integer category = null;
        Integer gallery = getGalleryId(request);
        String categoryString = request.getParameter("category");
        if(categoryString!=null&&categoryString.length()>0) {
            category = Integer.valueOf(categoryString);
        }
        if(gallery!=null) {
            Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
            if(category==null) {
                Gallery template = (Gallery) environment.getEntityFactory().create("gallery-gallery");
                template.setId(virtualGalleryId);
                GalleryInterface entity = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(template);
                if(entity!=null && !entity.getTopCategory().equals(new Integer(0))) {
                    category = entity.getTopCategory();
                }
            }
            QueryFilter filter = null;
            if(category!=null) {
                filter = new QueryFilter(getParentFilter());
                filter.setAttribute("parent",category);
            }else {
                filter = new QueryFilter(getNoParentFilter());
            }
            filter.setAttribute("gallery",gallery);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-category").search(filter);
            categories = new Category[entities.length];
            for (int i = 0; i < entities.length; i++) {
                categories[i] = (Category) entities[i];
                categories[i].setGallery(virtualGalleryId);
            }
        }
        return null;
    }

    protected void initCommand(HttpServletRequest request) {
        //Do nothing
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    protected String getNoParentFilter() {
        return "allforgallerywithoutparent";
    }

    protected String getParentFilter() {
        return "allforgallerywithparent";
    }

    public Category[] getCategories() {
        return categories;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}