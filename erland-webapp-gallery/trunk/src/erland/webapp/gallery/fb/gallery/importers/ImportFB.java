package erland.webapp.gallery.fb.gallery.importers;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class ImportFB extends ValidatorForm {
    private Integer gallery;
    private String file;
    private Boolean clearCategories;
    private Boolean clearPictures;
    private Boolean localLinks;
    private Boolean filenameAsPictureTitle;
    private Boolean filenameAsPictureDescription;
    private Boolean cutLongPictureTitles;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return gallery!=null?gallery.toString():null;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        try {
            this.gallery = Integer.valueOf(galleryDisplay);
        } catch (NumberFormatException e) {
            this.gallery = null;
        }
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Boolean getClearCategories() {
        return clearCategories;
    }

    public void setClearCategories(Boolean clearCategories) {
        this.clearCategories = clearCategories;
    }

    public String getClearCategoriesDisplay() {
        return clearCategories!=null?clearCategories.toString():null;
    }

    public void setClearCategoriesDisplay(String clearCategoriesDisplay) {
        this.clearCategories = Boolean.valueOf(clearCategoriesDisplay);
    }

    public Boolean getClearPictures() {
        return clearPictures;
    }

    public void setClearPictures(Boolean clearPictures) {
        this.clearPictures = clearPictures;
    }

    public String getClearPicturesDisplay() {
        return clearPictures!=null?clearPictures.toString():null;
    }

    public void setClearPicturesDisplay(String clearPicturesDisplay) {
        this.clearPictures = Boolean.valueOf(clearPicturesDisplay);
    }

    public Boolean getLocalLinks() {
        return localLinks;
    }

    public void setLocalLinks(Boolean localLinks) {
        this.localLinks = localLinks;
    }

    public String getLocalLinksDisplay() {
        return localLinks!=null?localLinks.toString():null;
    }

    public void setLocalLinksDisplay(String localLinksDisplay) {
        this.localLinks = Boolean.valueOf(localLinksDisplay);
    }

    public Boolean getFilenameAsPictureTitle() {
        return filenameAsPictureTitle;
    }

    public void setFilenameAsPictureTitle(Boolean filenameAsPictureTitle) {
        this.filenameAsPictureTitle = filenameAsPictureTitle;
    }

    public String getFilenameAsPictureTitleDisplay() {
        return filenameAsPictureTitle!=null?filenameAsPictureTitle.toString():null;
    }

    public void setFilenameAsPictureTitleDisplay(String filenameAsPictureTitleDisplay) {
        this.filenameAsPictureTitle = Boolean.valueOf(filenameAsPictureTitleDisplay);
    }

    public Boolean getFilenameAsPictureDescription() {
        return filenameAsPictureDescription;
    }

    public void setFilenameAsPictureDescription(Boolean filenameAsPictureDescription) {
        this.filenameAsPictureDescription = filenameAsPictureDescription;
    }

    public String getFilenameAsPictureDescriptionDisplay() {
        return filenameAsPictureDescription!=null?filenameAsPictureDescription.toString():null;
    }

    public void setFilenameAsPictureDescriptionDisplay(String filenameAsPictureDescriptionDisplay) {
        this.filenameAsPictureDescription = Boolean.valueOf(filenameAsPictureDescriptionDisplay);
    }

    public Boolean getCutLongPictureTitles() {
        return cutLongPictureTitles;
    }

    public void setCutLongPictureTitles(Boolean cutLongPictureTitles) {
        this.cutLongPictureTitles = cutLongPictureTitles;
    }

    public String getCutLongPictureTitlesDisplay() {
        return cutLongPictureTitles!=null?cutLongPictureTitles.toString():null;
    }

    public void setCutLongPictureTitlesDisplay(String cutLongPictureTitlesDisplay) {
        this.cutLongPictureTitles = Boolean.valueOf(cutLongPictureTitlesDisplay);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        gallery = null;
        file = null;
        clearCategories = Boolean.FALSE;
        clearPictures = Boolean.FALSE;
        localLinks = Boolean.FALSE;
        filenameAsPictureTitle = Boolean.FALSE;
        filenameAsPictureDescription = Boolean.FALSE;
        cutLongPictureTitles = Boolean.FALSE;

    }
}