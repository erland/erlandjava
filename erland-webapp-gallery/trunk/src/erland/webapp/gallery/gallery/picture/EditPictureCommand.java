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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class EditPictureCommand implements CommandInterface, ViewPictureInterface {
    private WebAppEnvironmentInterface environment;
    private Picture picture;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String link = request.getParameter("link");
        String officialString = request.getParameter("official");
        Boolean official = Boolean.FALSE;
        if(officialString!=null && officialString.equalsIgnoreCase("true")) {
            official = Boolean.TRUE;
        }
        String dateString = request.getParameter("date");
        Date date = null;
        if(dateString!=null && dateString.length()>0) {
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        picture = (Picture) environment.getEntityFactory().create("picture");
        if(id!=null && id.length()>0) {
            picture.setId(Integer.valueOf(id));
        }
        picture.setGallery(gallery);
        picture.setTitle(title);
        picture.setDescription(description);
        picture.setImage(image);
        picture.setLink(link);
        picture.setDate(date);
        picture.setOfficial(official);
        environment.getEntityStorageFactory().getStorage("picture").store(picture);
        Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
        picture.setGallery(virtualGalleryId);
        return null;
    }

    public Picture getPicture() {
        return picture;
    }
    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
