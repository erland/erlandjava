package erland.webapp.dirgallery.act.loader;

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

import erland.webapp.dirgallery.entity.gallery.picture.Resolution;
import erland.webapp.dirgallery.fb.loader.ThumbnailImageFB;

import javax.servlet.http.HttpServletRequest;


public class LoadMovieResolutionImageAction extends LoadMovieThumbnailAction {
    protected Integer getWidth(HttpServletRequest request, ThumbnailImageFB fb) {
        Integer width = fb.getWidth();
        if (getGallery(request).getMaxWidth() != null && getGallery(request).getMaxWidth().intValue() > 0 && width != null && getGallery(request).getMaxWidth().compareTo(width) < 0) {

            width = getGallery(request).getMaxWidth();
        } else if (width == null) {
            if (getGallery(request).getMaxWidth() != null && getGallery(request).getMaxWidth().intValue() > 0) {
                width = getGallery(request).getMaxWidth();
            } else if (getGallery(request).getDefaultResolution() != null && getGallery(request).getDefaultResolution().length() > 0) {
                Resolution template = (Resolution) getEnvironment().getEntityFactory().create("dirgallery-resolution");
                template.setId(getGallery(request).getDefaultResolution());
                Resolution resolution = (Resolution) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-resolution").load(template);
                if (resolution != null) {
                    width = resolution.getWidth();
                }
            }
        }
        return width;
    }
}