package erland.webapp.diary.fb.inventory;

import org.apache.struts.action.ActionMapping;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;

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

public class InventoryEntryFB extends BaseFB {
    private Integer id;
    private Integer type;
    private Integer sex;
    private String speciesDisplay;
    private String name;
    private String description;
    private String image;
    private String largeImage;
    private String link;
    private Integer gallery;

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

    public Integer getSpecies() {
        return StringUtil.asInteger(speciesDisplay,null);
    }

    public void setSpecies(Integer species) {
        this.speciesDisplay = StringUtil.asString(species,null);
    }

    public String getSpeciesDisplay() {
        return speciesDisplay;
    }

    public void setSpeciesDisplay(String speciesDisplay) {
        this.speciesDisplay = speciesDisplay;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDisplay() {
        return StringUtil.asString(type,null);
    }

    public void setTypeDisplay(String typeDisplay) {
        this.type = StringUtil.asInteger(typeDisplay,null);
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexDisplay() {
        return StringUtil.asString(sex,null);
    }

    public void setSexDisplay(String sexDisplay) {
        this.sex = StringUtil.asInteger(sexDisplay,null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        type = null;
        sex = null;
        name = null;
        description = null;
        image = null;
        largeImage = null;
        link = null;
        gallery = null;
        speciesDisplay = null;
    }
}