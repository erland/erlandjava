package erland.webapp.gallery.fb.gallery.importers;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;

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

public class ImportFB extends BaseFB {
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
        return ServletParameterHelper.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = ServletParameterHelper.asInteger(galleryDisplay,null);
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
        return ServletParameterHelper.asString(clearCategories,null);
    }

    public void setClearCategoriesDisplay(String clearCategoriesDisplay) {
        this.clearCategories = ServletParameterHelper.asBoolean(clearCategoriesDisplay,Boolean.FALSE);
    }

    public Boolean getClearPictures() {
        return clearPictures;
    }

    public void setClearPictures(Boolean clearPictures) {
        this.clearPictures = clearPictures;
    }

    public String getClearPicturesDisplay() {
        return ServletParameterHelper.asString(clearPictures,null);
    }

    public void setClearPicturesDisplay(String clearPicturesDisplay) {
        this.clearPictures = ServletParameterHelper.asBoolean(clearPicturesDisplay,Boolean.FALSE);
    }

    public Boolean getLocalLinks() {
        return localLinks;
    }

    public void setLocalLinks(Boolean localLinks) {
        this.localLinks = localLinks;
    }

    public String getLocalLinksDisplay() {
        return ServletParameterHelper.asString(localLinks,null);
    }

    public void setLocalLinksDisplay(String localLinksDisplay) {
        this.localLinks = ServletParameterHelper.asBoolean(localLinksDisplay,Boolean.FALSE);
    }

    public Boolean getFilenameAsPictureTitle() {
        return filenameAsPictureTitle;
    }

    public void setFilenameAsPictureTitle(Boolean filenameAsPictureTitle) {
        this.filenameAsPictureTitle = filenameAsPictureTitle;
    }

    public String getFilenameAsPictureTitleDisplay() {
        return ServletParameterHelper.asString(filenameAsPictureTitle,null);
    }

    public void setFilenameAsPictureTitleDisplay(String filenameAsPictureTitleDisplay) {
        this.filenameAsPictureTitle = ServletParameterHelper.asBoolean(filenameAsPictureTitleDisplay,Boolean.FALSE);
    }

    public Boolean getFilenameAsPictureDescription() {
        return filenameAsPictureDescription;
    }

    public void setFilenameAsPictureDescription(Boolean filenameAsPictureDescription) {
        this.filenameAsPictureDescription = filenameAsPictureDescription;
    }

    public String getFilenameAsPictureDescriptionDisplay() {
        return ServletParameterHelper.asString(filenameAsPictureDescription,null);
    }

    public void setFilenameAsPictureDescriptionDisplay(String filenameAsPictureDescriptionDisplay) {
        this.filenameAsPictureDescription = ServletParameterHelper.asBoolean(filenameAsPictureDescriptionDisplay,Boolean.FALSE);
    }

    public Boolean getCutLongPictureTitles() {
        return cutLongPictureTitles;
    }

    public void setCutLongPictureTitles(Boolean cutLongPictureTitles) {
        this.cutLongPictureTitles = cutLongPictureTitles;
    }

    public String getCutLongPictureTitlesDisplay() {
        return ServletParameterHelper.asString(cutLongPictureTitles,null);
    }

    public void setCutLongPictureTitlesDisplay(String cutLongPictureTitlesDisplay) {
        this.cutLongPictureTitles = ServletParameterHelper.asBoolean(cutLongPictureTitlesDisplay,Boolean.FALSE);
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