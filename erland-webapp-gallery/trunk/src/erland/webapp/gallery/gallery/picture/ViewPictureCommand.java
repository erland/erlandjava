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
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class ViewPictureCommand implements CommandInterface, ViewPictureInterface {
    private Picture picture;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
        String id = request.getParameter("id");
        Picture template = (Picture)environment.getEntityFactory().create("gallery-picture");
        if(gallery!=null &&
                id!=null && id.length()>0) {

            Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
            template.setGallery(gallery);
            template.setId(Integer.valueOf(id));
            picture = (Picture) environment.getEntityStorageFactory().getStorage("gallery-picture").load(template);
            if(picture!=null) {
                picture.setGallery(virtualGalleryId);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public Picture getPicture() {
        return picture;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
