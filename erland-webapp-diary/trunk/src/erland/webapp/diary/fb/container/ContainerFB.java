package erland.webapp.diary.fb.container;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
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

public class ContainerFB extends BaseFB {
    private Integer id;
    private String name;
    private String model;
    private Integer volume;
    private Integer width;
    private Integer height;
    private Integer length;
    private String description;
    private Integer gallery;
    private String image;
    private String largeImage;
    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getVolumeDisplay() {
        return ServletParameterHelper.asString(volume,null);
    }

    public void setVolumeDisplay(String volumeDisplay) {
        this.volume = ServletParameterHelper.asInteger(volumeDisplay,null);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getWidthDisplay() {
        return ServletParameterHelper.asString(width,null);
    }

    public void setWidthDisplay(String widthDisplay) {
        this.width = ServletParameterHelper.asInteger(widthDisplay,null);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getHeightDisplay() {
        return ServletParameterHelper.asString(height,null);
    }

    public void setHeightDisplay(String heightDisplay) {
        this.height = ServletParameterHelper.asInteger(heightDisplay,null);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getLengthDisplay() {
        return ServletParameterHelper.asString(length,null);
    }

    public void setLengthDisplay(String lengthDisplay) {
        this.length = ServletParameterHelper.asInteger(lengthDisplay,null);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        name = null;
        model = null;
        volume = null;
        width = null;
        height = null;
        length = null;
        description = null;
        gallery = null;
        image = null;
        largeImage = null;
        link = null;
    }
}