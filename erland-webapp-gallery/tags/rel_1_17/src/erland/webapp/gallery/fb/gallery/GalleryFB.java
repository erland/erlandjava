package erland.webapp.gallery.fb.gallery;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

import java.util.ArrayList;

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
    private String titleEnglish;
    private String description;
    private String descriptionEnglish;
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
    private String stylesheet;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;
    private String sortOrder;
    private Integer noOfRows;
    private Integer noOfCols;
    private Boolean allowSearch;
    private Boolean cutLongPictureTitles;
    private Boolean useShortPictureNames;
    private Boolean showPictureTitle;
    private Boolean showResolutionLinks;
    private Boolean forcePictureUpdate;
    private String pictureTitle;
    private String thumbnailPictureTitle;
    private String thumbnailRow1;
    private String thumbnailRow2;
    private String thumbnailRow3;
    private String copyrightText;
    private Boolean useExifThumbnails;
    private Boolean scaleExifThumbnails;
    private Boolean useCacheLargeImages;
    private String nationalNamePattern;
    private String englishNamePattern;

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

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return StringUtil.asString(official,null);
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = StringUtil.asBoolean(officialDisplay,Boolean.FALSE);
    }

    public Integer getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategory = topCategory;
    }

    public String getTopCategoryDisplay() {
        return StringUtil.asString(topCategory,null);
    }

    public void setTopCategoryDisplay(String topCategoryDisplay) {
        this.topCategory = StringUtil.asInteger(topCategoryDisplay,null);
    }

    public Integer getReferencedGallery() {
        return referencedGallery;
    }

    public void setReferencedGallery(Integer referencedGallery) {
        this.referencedGallery = referencedGallery;
    }

    public String getReferencedGalleryDisplay() {
        return StringUtil.asString(referencedGallery,null);
    }

    public void setReferencedGalleryDisplay(String referencedGalleryDisplay) {
        this.referencedGallery = StringUtil.asInteger(referencedGalleryDisplay,null);
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getMaxWidthDisplay() {
        return StringUtil.asString(maxWidth,null);
    }

    public void setMaxWidthDisplay(String maxWidthDisplay) {
        this.maxWidth = StringUtil.asInteger(maxWidthDisplay,null);
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
            ArrayList result = new ArrayList();
            for (int i = 0; i < categories.length; i++) {
                String str = StringUtil.asString(categories[i],null);
                if(str!=null) {
                    result.add(str);
                }
            }
            return (String[]) result.toArray(new String[0]);
        }else {
            return null;
        }
    }
    public void setCategoriesDisplay(String[] categoriesDisplay) {
        if(categoriesDisplay!=null) {
            ArrayList result = new ArrayList();
            for (int i = 0; i < categoriesDisplay.length; i++) {
                Integer value = StringUtil.asInteger(categoriesDisplay[i],null);
                if(value!=null) {
                    result.add(value);
                }
            }
            this.categories = (Integer[]) result.toArray(new Integer[0]);
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
        return StringUtil.asString(thumbnailCompression,null);
    }

    public void setThumbnailCompressionDisplay(String thumbnailCompressionDisplay) {
        this.thumbnailCompression = StringUtil.asFloat(thumbnailCompressionDisplay,null);
    }

    public Float getCompression() {
        return compression;
    }

    public void setCompression(Float compression) {
        this.compression = compression;
    }

    public String getCompressionDisplay() {
        return StringUtil.asString(compression,null);
    }

    public void setCompressionDisplay(String compressionDisplay) {
        this.compression = StringUtil.asFloat(compressionDisplay,null);
    }

    public Integer getOfficialCategory() {
        return officialCategory;
    }

    public void setOfficialCategory(Integer officialCategory) {
        this.officialCategory = officialCategory;
    }

    public String getOfficialCategoryDisplay() {
        return StringUtil.asString(officialCategory,null);
    }

    public void setOfficialCategoryDisplay(String officialCategoryDisplay) {
        this.officialCategory = StringUtil.asInteger(officialCategoryDisplay,null);
    }

    public Integer getOfficialGuestCategory() {
        return officialGuestCategory;
    }

    public void setOfficialGuestCategory(Integer officialGuestCategory) {
        this.officialGuestCategory = officialGuestCategory;
    }

    public String getOfficialGuestCategoryDisplay() {
        return StringUtil.asString(officialGuestCategory,null);
    }

    public void setOfficialGuestCategoryDisplay(String officialGuestCategoryDisplay) {
        this.officialGuestCategory = StringUtil.asInteger(officialGuestCategoryDisplay,null);
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
        return StringUtil.asString(antialias,null);
    }

    public void setAntialiasDisplay(String antialiasDisplay) {
        this.antialias = StringUtil.asBoolean(antialiasDisplay,Boolean.FALSE);
    }

    public Boolean getThumbnailAntialias() {
        return thumbnailAntialias;
    }

    public void setThumbnailAntialias(Boolean thumbnailAntialias) {
        this.thumbnailAntialias = thumbnailAntialias;
    }

    public String getThumbnailAntialiasDisplay() {
        return StringUtil.asString(thumbnailAntialias,null);
    }

    public void setThumbnailAntialiasDisplay(String thumbnailAntialiasDisplay) {
        this.thumbnailAntialias = StringUtil.asBoolean(thumbnailAntialiasDisplay,Boolean.FALSE);
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailWidthDisplay() {
        return StringUtil.asString(thumbnailWidth,null);
    }

    public void setThumbnailWidthDisplay(String thumbnailWidthDisplay) {
        this.thumbnailWidth = StringUtil.asInteger(thumbnailWidthDisplay,null);
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(Integer noOfRows) {
        this.noOfRows = noOfRows;
    }

    public String getNoOfRowsDisplay() {
        return StringUtil.asString(noOfRows,null);
    }

    public void setNoOfRowsDisplay(String noOfRowsDisplay) {
        this.noOfRows = StringUtil.asInteger(noOfRowsDisplay,null);
    }

    public Integer getNoOfCols() {
        return noOfCols;
    }

    public void setNoOfCols(Integer noOfCols) {
        this.noOfCols = noOfCols;
    }

    public String getNoOfColsDisplay() {
        return StringUtil.asString(noOfCols,null);
    }

    public void setNoOfColsDisplay(String noOfColsDisplay) {
        this.noOfCols = StringUtil.asInteger(noOfColsDisplay,null);
    }

    public Boolean getAllowSearch() {
        return allowSearch;
    }

    public void setAllowSearch(Boolean allowSearch) {
        this.allowSearch = allowSearch;
    }

    public String getAllowSearchDisplay() {
        return StringUtil.asString(allowSearch,null);
    }

    public void setAllowSearchDisplay(String allowSearchDisplay) {
        this.allowSearch = StringUtil.asBoolean(allowSearchDisplay,Boolean.FALSE);
    }

    public Boolean getCutLongPictureTitles() {
        return cutLongPictureTitles;
    }

    public void setCutLongPictureTitles(Boolean cutLongPictureTitles) {
        this.cutLongPictureTitles = cutLongPictureTitles;
    }

    public String getCutLongPictureTitlesDisplay() {
        return StringUtil.asString(cutLongPictureTitles,null);
    }

    public void setCutLongPictureTitlesDisplay(String cutLongPictureTitlesDisplay) {
        this.cutLongPictureTitles = StringUtil.asBoolean(cutLongPictureTitlesDisplay,Boolean.FALSE);
    }

    public Boolean getUseShortPictureNames() {
        return useShortPictureNames;
    }

    public void setUseShortPictureNames(Boolean useShortPictureNames) {
        this.useShortPictureNames = useShortPictureNames;
    }

    public String getUseShortPictureNamesDisplay() {
        return StringUtil.asString(useShortPictureNames,null);
    }

    public void setUseShortPictureNamesDisplay(String useShortPictureNamesDisplay) {
        this.useShortPictureNames = StringUtil.asBoolean(useShortPictureNamesDisplay,Boolean.FALSE);
    }

    public Boolean getShowPictureTitle() {
        return showPictureTitle;
    }

    public void setShowPictureTitle(Boolean showPictureTitle) {
        this.showPictureTitle = showPictureTitle;
    }

    public String getShowPictureTitleDisplay() {
        return StringUtil.asString(showPictureTitle,null);
    }

    public void setShowPictureTitleDisplay(String showPictureTitleDisplay) {
        this.showPictureTitle = StringUtil.asBoolean(showPictureTitleDisplay,Boolean.FALSE);
    }

    public Boolean getShowResolutionLinks() {
        return showResolutionLinks;
    }

    public void setShowResolutionLinks(Boolean showResolutionLinks) {
        this.showResolutionLinks = showResolutionLinks;
    }

    public String getShowResolutionLinksDisplay() {
        return StringUtil.asString(showResolutionLinks,null);
    }

    public void setShowResolutionLinksDisplay(String showResolutionLinksDisplay) {
        this.showResolutionLinks = StringUtil.asBoolean(showResolutionLinksDisplay,Boolean.FALSE);
    }

    public Boolean getForcePictureUpdate() {
        return forcePictureUpdate;
    }

    public void setForcePictureUpdate(Boolean forcePictureUpdate) {
        this.forcePictureUpdate = forcePictureUpdate;
    }

    public String getForcePictureUpdateDisplay() {
        return StringUtil.asString(forcePictureUpdate,null);
    }

    public void setForcePictureUpdateDisplay(String forcePictureUpdateDisplay) {
        this.forcePictureUpdate = StringUtil.asBoolean(forcePictureUpdateDisplay,Boolean.FALSE);
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getThumbnailHeightDisplay() {
        return StringUtil.asString(thumbnailHeight,null);
    }

    public void setThumbnailHeightDisplay(String thumbnailHeightDisplay) {
        this.thumbnailHeight = StringUtil.asInteger(thumbnailHeightDisplay,null);
    }

    public String getThumbnailPictureTitle() {
        return thumbnailPictureTitle;
    }

    public void setThumbnailPictureTitle(String thumbnailPictureTitle) {
        this.thumbnailPictureTitle = thumbnailPictureTitle;
    }

    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public String getThumbnailRow1() {
        return thumbnailRow1;
    }

    public void setThumbnailRow1(String thumbnailRow1) {
        this.thumbnailRow1 = thumbnailRow1;
    }

    public String getThumbnailRow2() {
        return thumbnailRow2;
    }

    public void setThumbnailRow2(String thumbnailRow2) {
        this.thumbnailRow2 = thumbnailRow2;
    }

    public String getThumbnailRow3() {
        return thumbnailRow3;
    }

    public void setThumbnailRow3(String thumbnailRow3) {
        this.thumbnailRow3 = thumbnailRow3;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public Boolean getUseExifThumbnails() {
        return useExifThumbnails;
    }

    public void setUseExifThumbnails(Boolean useExifThumbnails) {
        this.useExifThumbnails = useExifThumbnails;
    }

    public Boolean getScaleExifThumbnails() {
        return scaleExifThumbnails;
    }

    public void setScaleExifThumbnails(Boolean scaleExifThumbnails) {
        this.scaleExifThumbnails = scaleExifThumbnails;
    }

    public String getUseExifThumbnailsDisplay() {
        return StringUtil.asString(useExifThumbnails,null);
    }

    public void setUseExifThumbnailsDisplay(String useExifThumbnailsDisplay) {
        this.useExifThumbnails = StringUtil.asBoolean(useExifThumbnailsDisplay,Boolean.FALSE);
    }

    public String getScaleExifThumbnailsDisplay() {
        return StringUtil.asString(scaleExifThumbnails,null);
    }

    public void setScaleExifThumbnailsDisplay(String scaleExifThumbnailsDisplay) {
        this.scaleExifThumbnails = StringUtil.asBoolean(scaleExifThumbnailsDisplay,Boolean.FALSE);
    }

    public Boolean getUseCacheLargeImages() {
        return useCacheLargeImages;
    }

    public void setUseCacheLargeImages(Boolean useCacheLargeImages) {
        this.useCacheLargeImages = useCacheLargeImages;
    }
    public String getUseCacheLargeImagesDisplay() {
        return StringUtil.asString(useCacheLargeImages,null);
    }

    public void setUseCacheLargeImagesDisplay(String useCacheLargeImagesDisplay) {
        this.useCacheLargeImages = StringUtil.asBoolean(useCacheLargeImagesDisplay,Boolean.FALSE);
    }

    public String getNationalNamePattern() {
        return nationalNamePattern;
    }

    public void setNationalNamePattern(String nationalNamePattern) {
        this.nationalNamePattern = nationalNamePattern;
    }

    public String getEnglishNamePattern() {
        return englishNamePattern;
    }

    public void setEnglishNamePattern(String englishNamePattern) {
        this.englishNamePattern = englishNamePattern;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        categories = null;
        description = null;
        descriptionEnglish = null;
        official = Boolean.FALSE;
        referencedGallery = null;
        title = null;
        titleEnglish = null;
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
        stylesheet = null;
        thumbnailWidth = null;
        thumbnailHeight = null;
        sortOrder = null;
        noOfRows = null;
        noOfCols = null;
        allowSearch = Boolean.FALSE;
        cutLongPictureTitles = Boolean.FALSE;
        useShortPictureNames = Boolean.FALSE;
        showPictureTitle = Boolean.FALSE;
        showResolutionLinks = Boolean.FALSE;
        forcePictureUpdate = Boolean.FALSE;
        pictureTitle = null;
        thumbnailPictureTitle = null;
        thumbnailRow1 = null;
        thumbnailRow2 = null;
        thumbnailRow3 = null;
        copyrightText = null;
        useExifThumbnails = Boolean.FALSE;
        scaleExifThumbnails = Boolean.FALSE;
        useCacheLargeImages = Boolean.FALSE;
        nationalNamePattern = null;
        englishNamePattern = null;
    }
}