package erland.webapp.gallery.fb.tasks;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class TasksFB extends BaseFB {
    private Boolean importPictures;
    private Boolean externalImportPictures;
    private Boolean thumbnailGeneration;

    public Boolean getImportPictures() {
        return importPictures;
    }

    public void setImportPictures(Boolean importPictures) {
        this.importPictures = importPictures;
    }

    public String getImportPicturesDisplay() {
        return StringUtil.asString(importPictures,null);
    }

    public void setImportPicturesDisplay(String importPicturesDisplay) {
        this.importPictures = StringUtil.asBoolean(importPicturesDisplay,Boolean.FALSE);
    }

    public Boolean getExternalImportPictures() {
        return externalImportPictures;
    }

    public void setExternalImportPictures(Boolean externalImportPictures) {
        this.externalImportPictures = externalImportPictures;
    }

    public String getExternalImportPicturesDisplay() {
        return StringUtil.asString(externalImportPictures,null);
    }

    public void setExternalImportPicturesDisplay(String externalImportPicturesDisplay) {
        this.externalImportPictures = StringUtil.asBoolean(externalImportPicturesDisplay,Boolean.FALSE);
    }

    public Boolean getThumbnailGeneration() {
        return thumbnailGeneration;
    }

    public void setThumbnailGeneration(Boolean thumbnailGeneration) {
        this.thumbnailGeneration = thumbnailGeneration;
    }

    public String getThumbnailGenerationDisplay() {
        return StringUtil.asString(thumbnailGeneration,null);
    }

    public void setThumbnailGenerationDisplay(String thumbnailGenerationDisplay) {
        this.thumbnailGeneration = StringUtil.asBoolean(thumbnailGenerationDisplay,Boolean.FALSE);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        externalImportPictures = Boolean.FALSE;
        importPictures = Boolean.FALSE;
        thumbnailGeneration = Boolean.FALSE;
    }
}