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

import javax.servlet.http.HttpServletRequest;

public class EditGalleryEntryCommand implements CommandInterface, ViewGalleryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String gallery = request.getParameter("gallery");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String link = request.getParameter("link");
        entry = (GalleryEntry) environment.getEntityFactory().create("diary-galleryentry");
        if(id!=null && id.length()>0) {
            entry.setId(Integer.valueOf(id));
        }
        entry.setGallery(Integer.valueOf(gallery));
        entry.setTitle(title);
        entry.setDescription(description);
        entry.setImage(image);
        entry.setLink(link);
        environment.getEntityStorageFactory().getStorage("diary-galleryentry").store(entry);
        return null;
    }

    public GalleryEntry getEntry() {
        return entry;
    }
}
