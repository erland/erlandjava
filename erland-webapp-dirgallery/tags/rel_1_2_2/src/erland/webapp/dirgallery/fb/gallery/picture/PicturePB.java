package erland.webapp.dirgallery.fb.gallery.picture;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BasePB;
import erland.webapp.dirgallery.entity.gallery.picture.Picture;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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

public class PicturePB extends BasePB {
    private String id;
    private Integer gallery;
    private String name;
    private String description;
    private Long fileSize;
    private String comment;
    private String image;
    private String link;
    private String resolutionLink;
    private String updateCommentLink;
    private ResolutionPB[] resolutions;

    public static final int TYPEOFFILE_PICTUREFILE = Picture.PICTUREFILE;
    public static final int TYPEOFFILE_MOVIEFILE = Picture.MOVIEFILE;
    private static final NumberFormat FORMAT_NO_DECIMAL = new DecimalFormat("#");
    private static final NumberFormat FORMAT_ONE_DECIMAL = new DecimalFormat("#.##");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return StringUtil.asString(gallery, null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = StringUtil.asInteger(galleryDisplay, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSizeDisplay() {
        return StringUtil.asString(fileSize, null);
    }

    public void setFileSizeDisplay(String fileSizeDisplay) {
        this.fileSize = StringUtil.asLong(fileSizeDisplay, null);
    }

    public String getFileSizeText() {
        Long size = getFileSize();
        if (size != null) {
            if (size.longValue() < 1024) {
                return FORMAT_NO_DECIMAL.format(size) + " b";
            } else if (size.longValue() < 1024 * 1024) {
                return FORMAT_NO_DECIMAL.format((size.longValue() / 1024)) + " kb";
            } else {
                return FORMAT_ONE_DECIMAL.format((double) size.longValue() / (1024 * 1024)) +" Mb";
            }
        }
        return null;
    }

    public void setFileSizeText(String fileSizeText) {
        // Not used, must be set with fileSize attribute
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResolutionLink() {
        return resolutionLink;
    }

    public void setResolutionLink(String resolutionLink) {
        this.resolutionLink = resolutionLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResolutionPB[] getResolutions() {
        return resolutions;
    }

    public void setResolutions(ResolutionPB[] resolutions) {
        this.resolutions = resolutions;
    }

    public String getUpdateCommentLink() {
        return updateCommentLink;
    }

    public void setUpdateCommentLink(String updateCommentLink) {
        this.updateCommentLink = updateCommentLink;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        gallery = null;
        name = null;
        description = null;
        fileSize = null;
        comment = null;
        image = null;
        link = null;
        resolutionLink = null;
        resolutions = null;
        updateCommentLink = null;
    }
}