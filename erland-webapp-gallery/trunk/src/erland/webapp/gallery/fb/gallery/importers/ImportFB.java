package erland.webapp.gallery.fb.gallery.importers;

import org.apache.struts.validator.ValidatorForm;

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
    private String galleryDisplay;
    private String file;
    private Boolean clearCategories;
    private String clearCategoriesDisplay;
    private Boolean clearPictures;
    private String clearPicturesDisplay;
    private Boolean localLinks;
    private String localLinksDisplay;
    private Boolean filenameAsPictureTitle;
    private String filenameAsPictureTitleDisplay;
    private Boolean filenameAsPictureDescription;
    private String filenameAsPictureDescriptionDisplay;
    private Boolean cutLongPictureTitles;
    private String cutLongPictureTitlesDisplay;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return galleryDisplay;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.galleryDisplay = galleryDisplay;
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
        return clearCategoriesDisplay;
    }

    public void setClearCategoriesDisplay(String clearCategoriesDisplay) {
        this.clearCategoriesDisplay = clearCategoriesDisplay;
    }

    public Boolean getClearPictures() {
        return clearPictures;
    }

    public void setClearPictures(Boolean clearPictures) {
        this.clearPictures = clearPictures;
    }

    public String getClearPicturesDisplay() {
        return clearPicturesDisplay;
    }

    public void setClearPicturesDisplay(String clearPicturesDisplay) {
        this.clearPicturesDisplay = clearPicturesDisplay;
    }

    public Boolean getLocalLinks() {
        return localLinks;
    }

    public void setLocalLinks(Boolean localLinks) {
        this.localLinks = localLinks;
    }

    public String getLocalLinksDisplay() {
        return localLinksDisplay;
    }

    public void setLocalLinksDisplay(String localLinksDisplay) {
        this.localLinksDisplay = localLinksDisplay;
    }

    public Boolean getFilenameAsPictureTitle() {
        return filenameAsPictureTitle;
    }

    public void setFilenameAsPictureTitle(Boolean filenameAsPictureTitle) {
        this.filenameAsPictureTitle = filenameAsPictureTitle;
    }

    public String getFilenameAsPictureTitleDisplay() {
        return filenameAsPictureTitleDisplay;
    }

    public void setFilenameAsPictureTitleDisplay(String filenameAsPictureTitleDisplay) {
        this.filenameAsPictureTitleDisplay = filenameAsPictureTitleDisplay;
    }

    public Boolean getFilenameAsPictureDescription() {
        return filenameAsPictureDescription;
    }

    public void setFilenameAsPictureDescription(Boolean filenameAsPictureDescription) {
        this.filenameAsPictureDescription = filenameAsPictureDescription;
    }

    public String getFilenameAsPictureDescriptionDisplay() {
        return filenameAsPictureDescriptionDisplay;
    }

    public void setFilenameAsPictureDescriptionDisplay(String filenameAsPictureDescriptionDisplay) {
        this.filenameAsPictureDescriptionDisplay = filenameAsPictureDescriptionDisplay;
    }

    public Boolean getCutLongPictureTitles() {
        return cutLongPictureTitles;
    }

    public void setCutLongPictureTitles(Boolean cutLongPictureTitles) {
        this.cutLongPictureTitles = cutLongPictureTitles;
    }

    public String getCutLongPictureTitlesDisplay() {
        return cutLongPictureTitlesDisplay;
    }

    public void setCutLongPictureTitlesDisplay(String cutLongPictureTitlesDisplay) {
        this.cutLongPictureTitlesDisplay = cutLongPictureTitlesDisplay;
    }
}