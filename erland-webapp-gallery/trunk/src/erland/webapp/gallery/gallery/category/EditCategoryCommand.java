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
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class EditCategoryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Boolean official = asBoolean(request.getParameter("official"));
        Boolean forcedOfficial = asBoolean(request.getParameter("forcedofficial"));
        Boolean alwaysOfficial = asBoolean(request.getParameter("alwaysofficial"));
        Boolean visible = asBoolean(request.getParameter("visible"));
        String categoryString = request.getParameter("category");
        Integer category = null;
        if(categoryString!=null && categoryString.length()>0) {
            category = Integer.valueOf(categoryString);
        }
        Integer gallery = getGalleryId(request);
        String name = request.getParameter("name");
        if(gallery!=null && category!=null) {
            Category template = (Category) environment.getEntityFactory().create("category");
            template.setGallery(gallery);
            template.setCategory(category);
            Category entity = (Category) environment.getEntityStorageFactory().getStorage("category").load(template);
            if(entity!=null && !name.equals(entity.getName())) {
                entity.setName(name);
                environment.getEntityStorageFactory().getStorage("category").store(entity);
            }
            if(entity!=null && (forcedOfficial.booleanValue() || !entity.getOfficial().equals(official)) || !entity.getOfficialAlways().equals(alwaysOfficial) || !entity.getOfficialVisible().equals(visible)) {
                QueryFilter filter = new QueryFilter("allforgalleryandcategorytree");
                filter.setAttribute("gallery",gallery);
                filter.setAttribute("category",category);
                EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("category").search(filter);
                Collection categories = new ArrayList(entities.length);
                for (int i = 0; i < entities.length; i++) {
                    categories.add(((Category)entities[i]).getCategory());
                }
                filter = new QueryFilter("allforgalleryandcategorylist");
                filter.setAttribute("gallery",gallery);
                filter.setAttribute("categories",categories);
                entity = (Category) environment.getEntityFactory().create("category");
                entity.setOfficial(official);
                entity.setOfficialAlways(alwaysOfficial);
                entity.setOfficialVisible(visible);
                environment.getEntityStorageFactory().getStorage("category").update(filter,entity);

                filter = new QueryFilter("calculateofficialforgallery");
                filter.setAttribute("gallery",gallery);
                updatePictures(filter,gallery, Boolean.TRUE);

                filter = new QueryFilter("calculateunofficialforgallery");
                filter.setAttribute("gallery",gallery);
                updatePictures(filter,gallery, Boolean.FALSE);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    private void updatePictures(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) environment.getEntityFactory().create("picture");
        entity.setOfficial(official);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("picture").search(filter);
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture)entities[i]).getId());
        }
        QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
        pictureFilter.setAttribute("gallery",gallery);
        pictureFilter.setAttribute("pictures",pictures);
        environment.getEntityStorageFactory().getStorage("picture").update(pictureFilter,entity);
    }
    private Boolean asBoolean(String parameter) {
        if(parameter!=null && parameter.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}