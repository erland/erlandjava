package erland.webapp.gallery.gallery.picture;
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
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class RemovePictureCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
        String id = request.getParameter("id");
        Picture template = (Picture)environment.getEntityFactory().create("picture");
        if(gallery!=null &&
                id!=null && id.length()>0) {

            template.setGallery(gallery);
            template.setId(Integer.valueOf(id));
            environment.getEntityStorageFactory().getStorage("picture").delete(template);
            QueryFilter filter = new QueryFilter("allforgalleryandpicture");
            filter.setAttribute("gallery",template.getGallery());
            filter.setAttribute("picture",template.getId());
            environment.getEntityStorageFactory().getStorage("categorypictureassociation").delete(filter);
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
