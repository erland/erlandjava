package erland.webapp.gallery.gallery;
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
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditGalleryCommand implements CommandInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String officialString = request.getParameter("official");
        String topCategoryString = request.getParameter("topcategory");
        String referencedGalleryString = request.getParameter("referencedgallery");
        gallery = (Gallery) environment.getEntityFactory().create("gallery-gallery");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Boolean official = Boolean.FALSE;
        if(officialString!=null && officialString.equals("true")) {
            official = Boolean.TRUE;
        }
        Integer topCategory = null;
        if(topCategoryString!=null && topCategoryString.length()>0) {
            topCategory = Integer.valueOf(topCategoryString);
        }
        if(id!=null && id.length()>0) {
            gallery.setId(Integer.valueOf(id));
        }
        if(referencedGalleryString!=null && referencedGalleryString.length()>0) {
            gallery.setReferencedGallery(Integer.valueOf(referencedGalleryString));
        }
        gallery.setUsername(username);
        gallery.setTitle(title);
        gallery.setDescription(description);
        gallery.setOfficial(official);
        gallery.setTopCategory(topCategory);
        environment.getEntityStorageFactory().getStorage("gallery-gallery").store(gallery);
        if(referencedGalleryString!=null && referencedGalleryString.length()>0) {
            String[] categories = request.getParameterValues("categories");
            if(categories!=null) {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery",gallery.getId());
                environment.getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").delete(filter);
                for (int i = 0; i < categories.length; i++) {
                    if(categories[i].length()>0) {
                        Integer categoryId = Integer.valueOf(categories[i]);
                        GalleryCategoryAssociation category = (GalleryCategoryAssociation) environment.getEntityFactory().create("gallery-gallerycategoryassociation");
                        category.setCategory(categoryId);
                        category.setGallery(gallery.getId());
                        environment.getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").store(category);
                    }
                }
            }
        }
        return null;
    }

    public GalleryInterface getGallery() {
        return gallery;
    }
}
