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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;

import javax.servlet.http.HttpServletRequest;

public class SearchGalleryEntriesCommand implements CommandInterface, ViewGalleryEntriesInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryEntry[] entries;
    private Integer galleryId;
    private Gallery gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        if(galleryString!=null && galleryString.length()>0) {
            galleryId = Integer.valueOf(galleryString);
            Gallery gallery = getGallery();
            if(gallery.getGallery()!=null && gallery.getGallery().intValue()!=0) {
                request.setAttribute("externalgallery",gallery.getGallery());
                return "external";
            }else {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery",galleryId);
                EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diarygalleryentry").search(filter);
                entries = new GalleryEntry[entities.length];
                for (int i = 0; i < entities.length; i++) {
                    entries[i] = (GalleryEntry) entities[i];
                }
            }
        }
        return "internal";
    }

    public GalleryEntry[] getEntries() {
        return entries;
    }

    public Gallery getGallery() {
        if(galleryId!=null && gallery==null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("diarygallery");
            template.setId(galleryId);
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("diarygallery").load(template);
        }
        return gallery;
    }
}
