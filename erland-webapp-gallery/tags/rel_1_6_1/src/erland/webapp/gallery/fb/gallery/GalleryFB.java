package erland.webapp.gallery.fb.gallery;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;

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

public class GalleryFB extends BaseFB {
    private Integer id;
    private String title;
    private String description;
    private Boolean official;
    private Integer topCategory;
    private Integer referencedGallery;
    private String defaultResolution;
    private Integer maxWidth;
    private Float thumbnailCompression;
    private Float compression;
    private Integer officialCategory;
    private Integer officialGuestCategory;
    private Integer[] categories;
    private String skin;
    private Boolean antialias;
    private Boolean thumbnailAntialias;

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

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return ServletParameterHelper.asString(official,null);
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = ServletParameterHelper.asBoolean(officialDisplay,Boolean.FALSE);
    }

    public Integer getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategory = topCategory;
    }

    public String getTopCategoryDisplay() {
        return ServletParameterHelper.asString(topCategory,null);
    }

    public void setTopCategoryDisplay(String topCategoryDisplay) {
        this.topCategory = ServletParameterHelper.asInteger(topCategoryDisplay,null);
    }

    public Integer getReferencedGallery() {
        return referencedGallery;
    }

    public void setReferencedGallery(Integer referencedGallery) {
        this.referencedGallery = referencedGallery;
    }

    public String getReferencedGalleryDisplay() {
        return ServletParameterHelper.asString(referencedGallery,null);
    }

    public void setReferencedGalleryDisplay(String referencedGalleryDisplay) {
        this.referencedGallery = ServletParameterHelper.asInteger(referencedGalleryDisplay,null);
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getMaxWidthDisplay() {
        return ServletParameterHelper.asString(maxWidth,null);
    }

    public void setMaxWidthDisplay(String maxWidthDisplay) {
        this.maxWidth = ServletParameterHelper.asInteger(maxWidthDisplay,null);
    }

    public String getDefaultResolution() {
        return defaultResolution;
    }

    public void setDefaultResolution(String defaultResolution) {
        this.defaultResolution = defaultResolution;
    }

    public Integer[] getCategories() {
        return categories;
    }

    public void setCategories(Integer[] categories) {
        this.categories = categories;
    }

    public String[] getCategoriesDisplay() {
        if(categories!=null) {
            String[] result = new String[categories.length];
            for (int i = 0; i < categories.length; i++) {
                result[i] = ServletParameterHelper.asString(categories[i],null);
            }
            return result;
        }else {
            return null;
        }
    }
    public void setCategoriesDisplay(String[] categoriesDisplay) {
        if(categoriesDisplay!=null) {
            this.categories = new Integer[categoriesDisplay.length];
            for (int i = 0; i < categoriesDisplay.length; i++) {
                this.categories[i] = ServletParameterHelper.asInteger(categoriesDisplay[i],null);
            }
        }else {
            this.categories = null;
        }
    }

    public Float getThumbnailCompression() {
        return thumbnailCompression;
    }

    public void setThumbnailCompression(Float thumbnailCompression) {
        this.thumbnailCompression = thumbnailCompression;
    }

    public String getThumbnailCompressionDisplay() {
        return ServletParameterHelper.asString(thumbnailCompression,null);
    }

    public void setThumbnailCompressionDisplay(String thumbnailCompressionDisplay) {
        this.thumbnailCompression = ServletParameterHelper.asFloat(thumbnailCompressionDisplay,null);
    }

    public Float getCompression() {
        return compression;
    }

    public void setCompression(Float compression) {
        this.compression = compression;
    }

    public String getCompressionDisplay() {
        return ServletParameterHelper.asString(compression,null);
    }

    public void setCompressionDisplay(String compressionDisplay) {
        this.compression = ServletParameterHelper.asFloat(compressionDisplay,null);
    }

    public Integer getOfficialCategory() {
        return officialCategory;
    }

    public void setOfficialCategory(Integer officialCategory) {
        this.officialCategory = officialCategory;
    }

    public String getOfficialCategoryDisplay() {
        return ServletParameterHelper.asString(officialCategory,null);
    }

    public void setOfficialCategoryDisplay(String officialCategoryDisplay) {
        this.officialCategory = ServletParameterHelper.asInteger(officialCategoryDisplay,null);
    }

    public Integer getOfficialGuestCategory() {
        return officialGuestCategory;
    }

    public void setOfficialGuestCategory(Integer officialGuestCategory) {
        this.officialGuestCategory = officialGuestCategory;
    }

    public String getOfficialGuestCategoryDisplay() {
        return ServletParameterHelper.asString(officialGuestCategory,null);
    }

    public void setOfficialGuestCategoryDisplay(String officialGuestCategoryDisplay) {
        this.officialGuestCategory = ServletParameterHelper.asInteger(officialGuestCategoryDisplay,null);
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public Boolean getAntialias() {
        return antialias;
    }

    public void setAntialias(Boolean antialias) {
        this.antialias = antialias;
    }

    public String getAntialiasDisplay() {
        return ServletParameterHelper.asString(antialias,null);
    }

    public void setAntialiasDisplay(String antialiasDisplay) {
        this.antialias = ServletParameterHelper.asBoolean(antialiasDisplay,Boolean.FALSE);
    }

    public Boolean getThumbnailAntialias() {
        return thumbnailAntialias;
    }

    public void setThumbnailAntialias(Boolean thumbnailAntialias) {
        this.thumbnailAntialias = thumbnailAntialias;
    }

    public String getThumbnailAntialiasDisplay() {
        return ServletParameterHelper.asString(thumbnailAntialias,null);
    }

    public void setThumbnailAntialiasDisplay(String thumbnailAntialiasDisplay) {
        this.thumbnailAntialias = ServletParameterHelper.asBoolean(thumbnailAntialiasDisplay,Boolean.FALSE);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        categories = null;
        description = null;
        official = Boolean.FALSE;
        referencedGallery = null;
        title = null;
        topCategory = null;
        defaultResolution = null;
        maxWidth = null;
        thumbnailCompression = null;
        compression = null;
        officialCategory = null;
        officialGuestCategory = null;
        skin = null;
        antialias = Boolean.FALSE;
        thumbnailAntialias = Boolean.FALSE;
    }
}