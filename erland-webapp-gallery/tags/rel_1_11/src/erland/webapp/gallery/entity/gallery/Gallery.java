package erland.webapp.gallery.entity.gallery;

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

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class Gallery extends BaseEntity {
    private Integer id;
    private String username;
    private String title;
    private String titleEnglish;
    private String description;
    private String descriptionEnglish;
    private Integer topCategory;
    private Boolean official;
    private Integer referencedGallery;
    private String defaultResolution;
    private Integer maxWidth;
    private Integer officialCategory;
    private Integer officialGuestCategory;
    private Float thumbnailCompression;
    private Float compression;
    private String skin;
    private Boolean antialias;
    private Boolean thumbnailAntialias;
    private Date cacheDate;
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
    private String pictureTitle;
    private String thumbnailPictureTitle;
    private String thumbnailRow1;
    private String thumbnailRow2;
    private String thumbnailRow3;
    private String copyrightText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategory = topCategory;
    }

    public Integer getReferencedGallery() {
        return referencedGallery;
    }

    public void setReferencedGallery(Integer referencedGallery) {
        this.referencedGallery = referencedGallery;
    }

    public String getDefaultResolution() {
        return defaultResolution;
    }

    public void setDefaultResolution(String defaultResolution) {
        this.defaultResolution = defaultResolution;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getOfficialCategory() {
        return officialCategory;
    }

    public void setOfficialCategory(Integer officialCategory) {
        this.officialCategory = officialCategory;
    }

    public Integer getOfficialGuestCategory() {
        return officialGuestCategory;
    }

    public void setOfficialGuestCategory(Integer officialGuestCategory) {
        this.officialGuestCategory = officialGuestCategory;
    }

    public Float getThumbnailCompression() {
        return thumbnailCompression;
    }

    public void setThumbnailCompression(Float thumbnailCompression) {
        this.thumbnailCompression = thumbnailCompression;
    }

    public Float getCompression() {
        return compression;
    }

    public void setCompression(Float compression) {
        this.compression = compression;
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

    public Boolean getThumbnailAntialias() {
        return thumbnailAntialias;
    }

    public void setThumbnailAntialias(Boolean thumbnailAntialias) {
        this.thumbnailAntialias = thumbnailAntialias;
    }

    public Date getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
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

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
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

    public Integer getNoOfCols() {
        return noOfCols;
    }

    public void setNoOfCols(Integer noOfCols) {
        this.noOfCols = noOfCols;
    }

    public Boolean getAllowSearch() {
        return allowSearch;
    }

    public void setAllowSearch(Boolean allowSearch) {
        this.allowSearch = allowSearch;
    }

    public Boolean getCutLongPictureTitles() {
        return cutLongPictureTitles;
    }

    public void setCutLongPictureTitles(Boolean cutLongPictureTitles) {
        this.cutLongPictureTitles = cutLongPictureTitles;
    }

    public Boolean getUseShortPictureNames() {
        return useShortPictureNames;
    }

    public void setUseShortPictureNames(Boolean useShortPictureNames) {
        this.useShortPictureNames = useShortPictureNames;
    }

    public Boolean getShowPictureTitle() {
        return showPictureTitle;
    }

    public void setShowPictureTitle(Boolean showPictureTitle) {
        this.showPictureTitle = showPictureTitle;
    }

    public Boolean getShowResolutionLinks() {
        return showResolutionLinks;
    }

    public void setShowResolutionLinks(Boolean showResolutionLinks) {
        this.showResolutionLinks = showResolutionLinks;
    }

    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public String getThumbnailPictureTitle() {
        return thumbnailPictureTitle;
    }

    public void setThumbnailPictureTitle(String thumbnailPictureTitle) {
        this.thumbnailPictureTitle = thumbnailPictureTitle;
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
}
