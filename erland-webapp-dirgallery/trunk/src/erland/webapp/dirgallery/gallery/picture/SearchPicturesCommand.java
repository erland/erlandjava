package erland.webapp.dirgallery.gallery.picture;
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
import erland.webapp.dirgallery.gallery.GalleryHelper;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.dirgallery.gallery.ViewGalleryInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SearchPicturesCommand implements CommandInterface, ViewPicturesInterface, ViewGalleryInterface, ViewPicturePageInterface {
    private WebAppEnvironmentInterface environment;
    private Picture[] pictures;
    private GalleryInterface gallery;
    private Boolean hasNextPage;
    private Integer start;
    private Integer max;
    private Map comments;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer galleryId = getGalleryId(request);
        start = asInteger(request.getParameter("start"));
        if (start == null) {
            start = new Integer(0);
        }
        if (galleryId != null) {
            GalleryInterface template = (GalleryInterface) environment.getEntityFactory().create("dirgallery-gallery");
            template.setId(galleryId);
            gallery = (GalleryInterface) environment.getEntityStorageFactory().getStorage("dirgallery-gallery").load(template);
            if (gallery != null) {
                int thumbnailsPerRow = gallery.getNumberOfThumbnailsPerRow().intValue();
                if (thumbnailsPerRow == 0) {
                    thumbnailsPerRow = 3;
                }
                max = new Integer(gallery.getMaxNumberOfThumbnailRows().intValue() * thumbnailsPerRow);
                QueryFilter filter = new QueryFilter("allfordirectoryandextensions");
                filter.setAttribute("directory", gallery.getDirectory());
                if (gallery.getTypeOfFiles().intValue() == GalleryInterface.PICTUREFILES) {
                    filter.setAttribute("extensions", ".jpg,.jpeg");
                } else if (gallery.getTypeOfFiles().intValue() == GalleryInterface.MOVIEFILES) {
                    filter.setAttribute("extensions", ".mpg,.mpeg");
                }
                filter.setAttribute("tree", gallery.getIncludeSubDirectories());
                EntityInterface[] entities = new EntityInterface[0];
                entities = environment.getEntityStorageFactory().getStorage("dirgallery-picture").search(filter);

                if (max.intValue() != 0) {
                    int length = max.intValue();
                    hasNextPage = Boolean.TRUE;
                    if (max.intValue() >= entities.length - start.intValue()) {
                        length = entities.length - start.intValue();
                        hasNextPage = Boolean.FALSE;
                    }
                    pictures = new Picture[length];
                } else {
                    pictures = new Picture[entities.length];
                }
                for (int i = start.intValue(),j = 0; j < pictures.length; i++, j++) {
                    pictures[j] = (Picture) entities[i];
                    pictures[j].setGallery(galleryId);
                }
                loadPictureComments(pictures);
            }
        }
        return null;
    }

    public Picture[] getPictures() {
        return pictures;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    public GalleryInterface getGallery() {
        return gallery;
    }

    private Integer asInteger(String value) {
        Integer intValue = null;
        if (value != null && value.length() > 0) {
            intValue = Integer.valueOf(value);
        }
        return intValue;
    }

    public void loadPictureComments(Picture[] pictures) {
        Collection commentIds = new ArrayList();
        for (int i = 0; i < pictures.length; i++) {
            commentIds.add(pictures[i].getFullPath());
        }
        comments = new HashMap();
        if(commentIds.size()>0) {
            QueryFilter filter = new QueryFilter("allforcommentlist");
            filter.setAttribute("comments", commentIds);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("dirgallery-picturecomment").search(filter);
            for (int i = 0; i < entities.length; i++) {
                PictureComment comment = (PictureComment) entities[i];
                comments.put(comment.getId(), comment.getComment());
            }
        }
    }

    public Integer getPreviousPage() {
        if (max != null && max.intValue() != 0 && start != null) {
            if (start.intValue() > 0) {
                if (start.intValue() - max.intValue() < 0) {
                    return new Integer(0);
                } else {
                    return new Integer(start.intValue() - max.intValue());
                }
            }
        }
        return null;
    }

    public Integer getNextPage() {
        if (max != null && max.intValue() != 0 && start != null) {
            if (max.intValue() == getPictures().length && hasNextPage != null && hasNextPage.booleanValue()) {
                return new Integer(start.intValue() + max.intValue());
            }
        }
        return null;
    }

    public String getPictureComment(Picture picture) {
        return (String) comments.get(picture.getFullPath());
    }
}
