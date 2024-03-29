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

public class RemoveGalleryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            Gallery gallery = (Gallery) environment.getEntityFactory().create("gallery-gallery");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            gallery.setId(Integer.valueOf(id));
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery-gallery").load(gallery);
            if(gallery.getUsername().equals(username)) {
                environment.getEntityStorageFactory().getStorage("gallery-gallery").delete(gallery);
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery",gallery.getId());
                environment.getEntityStorageFactory().getStorage("gallery-picture").delete(filter);
                environment.getEntityStorageFactory().getStorage("gallery-categorypictureassociation").delete(filter);
                environment.getEntityStorageFactory().getStorage("gallery-categorymembership").delete(filter);
                environment.getEntityStorageFactory().getStorage("gallery-category").delete(filter);
                environment.getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").delete(filter);
            }
        }
        return null;
    }
}
