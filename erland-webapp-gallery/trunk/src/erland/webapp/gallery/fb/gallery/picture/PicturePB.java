package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.validator.ValidatorForm;

import java.util.Map;

import erland.webapp.common.fb.BasePB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

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

public class PicturePB extends BasePB {
    private Integer gallery;
    private Integer id;
    private String title;
    private String description;
    private String image;
    private String link;
    private String updateLink;
    private String removeLink;
    private ResolutionLinkPB[] resolutionLinks;
    private String row1Info;
    private String row2Info;
    private String row3Info;
    private String currentLink;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return StringUtil.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = StringUtil.asInteger(galleryDisplay,null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return StringUtil.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = StringUtil.asInteger(idDisplay,null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getRemoveLink() {
        return removeLink;
    }

    public void setRemoveLink(String removeLink) {
        this.removeLink = removeLink;
    }

    public ResolutionLinkPB[] getResolutionLinks() {
        return resolutionLinks;
    }

    public void setResolutions(ResolutionLinkPB[] resolutionLinks) {
        this.resolutionLinks = resolutionLinks;
    }

    public String getRow1Info() {
        return row1Info;
    }

    public void setRow1Info(String row1Info) {
        this.row1Info = row1Info;
    }

    public String getRow2Info() {
        return row2Info;
    }

    public void setRow2Info(String row2Info) {
        this.row2Info = row2Info;
    }

    public String getRow3Info() {
        return row3Info;
    }

    public void setRow3Info(String row3Info) {
        this.row3Info = row3Info;
    }

    public String getCurrentLink() {
        return currentLink;
    }

    public void setCurrentLink(String currentLink) {
        this.currentLink = currentLink;
    }
}