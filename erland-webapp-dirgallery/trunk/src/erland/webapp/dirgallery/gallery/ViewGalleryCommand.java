package erland.webapp.dirgallery.gallery;
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
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class ViewGalleryCommand implements CommandInterface, ViewGalleryInterface, ViewGalleriesInterface, ViewFriendGalleriesInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryInterface gallery;
    private GalleryInterface[] galleries;
    private String username;
    private Integer[] friendGalleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("user");
        username = user.getUsername();
        if (id != null && id.length() > 0) {
            GalleryInterface template = (GalleryInterface) environment.getEntityFactory().create("gallery");
            template.setId(Integer.valueOf(id));
            gallery = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            if (gallery != null && !gallery.getUsername().equals(username)) {
                gallery = null;
            }
        }
        return null;
    }

    public GalleryInterface getGallery() {
        return gallery;
    }

    public GalleryInterface[] getGalleries() {
        if (galleries == null) {
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username", username);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery").search(filter);
            galleries = new GalleryInterface[entities.length];
            for (int i = 0; i < entities.length; i++) {
                galleries[i] = (GalleryInterface) entities[i];
            }
        }
        return galleries;
    }

    public Integer[] getFriendGalleries() {
        if (friendGalleries == null) {
            if (gallery != null) {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery", gallery.getId());
                EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("friendgallery").search(filter);
                friendGalleries = new Integer[entities.length];
                for (int i = 0; i < entities.length; i++) {
                    friendGalleries[i] = ((FriendGallery) entities[i]).getFriendGallery();
                }
            } else {
                friendGalleries = new Integer[0];
            }
        }
        return friendGalleries;
    }
}
