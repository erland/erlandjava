package erland.webapp.diary.gallery;
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
        String galleryString = request.getParameter("gallery");
        gallery = (Gallery) environment.getEntityFactory().create("diary-gallery");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Boolean official = Boolean.FALSE;
        if(officialString!=null && officialString.equals("true")) {
            official = Boolean.TRUE;
        }
        if(id!=null && id.length()>0) {
            gallery.setId(Integer.valueOf(id));
        }
        if(galleryString!=null && galleryString.length()>0) {
            gallery.setGallery(Integer.valueOf(galleryString));
        }
        gallery.setUsername(username);
        gallery.setTitle(title);
        gallery.setDescription(description);
        gallery.setOfficial(official);
        environment.getEntityStorageFactory().getStorage("diary-gallery").store(gallery);
        return null;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
