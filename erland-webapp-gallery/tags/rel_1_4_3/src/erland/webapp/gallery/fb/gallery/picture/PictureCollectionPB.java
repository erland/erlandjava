package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.validator.ValidatorForm;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.webapp.gallery.fb.gallery.category.CategoryPB;

import java.util.Map;

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

public class PictureCollectionPB extends ValidatorForm {
    private PicturePB[] pictures;
    private String prevLink;
    private String nextLink;
    private String searchLink;

    public PicturePB[] getPictures() {
        return pictures;
    }

    public void setPictures(PicturePB[] pictures) {
        this.pictures = pictures;
    }

    public String getPrevLink() {
        return prevLink;
    }

    public void setPrevLink(String prevLink) {
        this.prevLink = prevLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }
}