package erland.webapp.dirgallery.fb.gallery;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;
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

public class GalleryFB extends BaseFB {
    private Integer id;
    private String username;
    private String title;
    private String menuName;
    private String description;
    private Boolean official;
    private String directory;
    private Boolean originalDownloadable;
    private Boolean includeSubDirectories;
    private Integer numberOfThumbnailsPerRow;
    private Integer thumbnailWidth;
    private Boolean showLogoInGalleryPage;
    private Boolean showDownloadLinks;
    private String logo;
    private String logoLink;
    private Boolean useLogoSeparator;
    private Integer logoSeparatorHeight;
    private String logoSeparatorColor;
    private Boolean showPictureNames;
    private Integer maxNumberOfThumbnailRows;
    private Boolean useShortPictureNames;
    private Integer orderNumber;
    private Boolean useThumbnailCache;
    private Double thumbnailCompression;
    private Integer typeOfFiles;
    private Integer numberOfMovieThumbnailColumns;
    private Integer numberOfMovieThumbnailRows;
    private Integer maxPictureNameLength;
    private Boolean showPictureNameInTooltip;
    private Boolean useTooltip;
    private Boolean showFileSizeBelowPicture;
    private Boolean showCommentBelowPicture;
    private Integer maxWidth;
    private String defaultResolution;
    private Integer[] friendGalleries;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return StringUtil.asString(id, null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = StringUtil.asInteger(idDisplay, null);
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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
        return StringUtil.asString(official, null);
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = StringUtil.asBoolean(officialDisplay, Boolean.FALSE);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Boolean getOriginalDownloadable() {
        return originalDownloadable;
    }

    public void setOriginalDownloadable(Boolean originalDownloadable) {
        this.originalDownloadable = originalDownloadable;
    }

    public String getOriginalDownloadableDisplay() {
        return StringUtil.asString(originalDownloadable, null);
    }

    public void setOriginalDownloadableDisplay(String originalDownloadableDisplay) {
        this.originalDownloadable = StringUtil.asBoolean(originalDownloadableDisplay, Boolean.FALSE);
    }

    public Boolean getIncludeSubDirectories() {
        return includeSubDirectories;
    }

    public void setIncludeSubDirectories(Boolean includeSubDirectories) {
        this.includeSubDirectories = includeSubDirectories;
    }

    public String getIncludeSubDirectoriesDisplay() {
        return StringUtil.asString(includeSubDirectories, null);
    }

    public void setIncludeSubDirectoriesDisplay(String includeSubDirectoriesDisplay) {
        this.includeSubDirectories = StringUtil.asBoolean(includeSubDirectoriesDisplay, Boolean.FALSE);
    }

    public Integer getNumberOfThumbnailsPerRow() {
        return numberOfThumbnailsPerRow;
    }

    public void setNumberOfThumbnailsPerRow(Integer numberOfThumbnailsPerRow) {
        this.numberOfThumbnailsPerRow = numberOfThumbnailsPerRow;
    }

    public String getNumberOfThumbnailsPerRowDisplay() {
        return StringUtil.asString(numberOfThumbnailsPerRow, null);
    }

    public void setNumberOfThumbnailsPerRowDisplay(String numberOfThumbnailsPerRowDisplay) {
        this.numberOfThumbnailsPerRow = StringUtil.asInteger(numberOfThumbnailsPerRowDisplay, null);
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailWidthDisplay() {
        return StringUtil.asString(thumbnailWidth, null);
    }

    public void setThumbnailWidthDisplay(String thumbnailWidthDisplay) {
        this.thumbnailWidth = StringUtil.asInteger(thumbnailWidthDisplay, null);
    }

    public Boolean getShowLogoInGalleryPage() {
        return showLogoInGalleryPage;
    }

    public void setShowLogoInGalleryPage(Boolean showLogoInGalleryPage) {
        this.showLogoInGalleryPage = showLogoInGalleryPage;
    }

    public String getShowLogoInGalleryPageDisplay() {
        return StringUtil.asString(showLogoInGalleryPage, null);
    }

    public void setShowLogoInGalleryPageDisplay(String showLogoInGalleryPageDisplay) {
        this.showLogoInGalleryPage = StringUtil.asBoolean(showLogoInGalleryPageDisplay, Boolean.FALSE);
    }

    public Boolean getShowDownloadLinks() {
        return showDownloadLinks;
    }

    public void setShowDownloadLinks(Boolean showDownloadLinks) {
        this.showDownloadLinks = showDownloadLinks;
    }

    public String getShowDownloadLinksDisplay() {
        return StringUtil.asString(showDownloadLinks, null);
    }

    public void setShowDownloadLinksDisplay(String showDownloadLinksDisplay) {
        this.showDownloadLinks = StringUtil.asBoolean(showDownloadLinksDisplay, Boolean.FALSE);
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public Boolean getUseLogoSeparator() {
        return useLogoSeparator;
    }

    public void setUseLogoSeparator(Boolean useLogoSeparator) {
        this.useLogoSeparator = useLogoSeparator;
    }

    public String getUseLogoSeparatorDisplay() {
        return StringUtil.asString(useLogoSeparator, null);
    }

    public void setUseLogoSeparatorDisplay(String useLogoSeparatorDisplay) {
        this.useLogoSeparator = StringUtil.asBoolean(useLogoSeparatorDisplay, Boolean.FALSE);
    }

    public Integer getLogoSeparatorHeight() {
        return logoSeparatorHeight;
    }

    public void setLogoSeparatorHeight(Integer logoSeparatorHeight) {
        this.logoSeparatorHeight = logoSeparatorHeight;
    }

    public String getLogoSeparatorHeightDisplay() {
        return StringUtil.asString(logoSeparatorHeight, null);
    }

    public void setLogoSeparatorHeightDisplay(String logoSeparatorHeightDisplay) {
        this.logoSeparatorHeight = StringUtil.asInteger(logoSeparatorHeightDisplay, null);
    }

    public String getLogoSeparatorColor() {
        return logoSeparatorColor;
    }

    public void setLogoSeparatorColor(String logoSeparatorColor) {
        this.logoSeparatorColor = logoSeparatorColor;
    }

    public Boolean getShowPictureNames() {
        return showPictureNames;
    }

    public void setShowPictureNames(Boolean showPictureNames) {
        this.showPictureNames = showPictureNames;
    }

    public String getShowPictureNamesDisplay() {
        return StringUtil.asString(showPictureNames, null);
    }

    public void setShowPictureNamesDisplay(String showPictureNamesDisplay) {
        this.showPictureNames = StringUtil.asBoolean(showPictureNamesDisplay, Boolean.FALSE);
    }

    public Integer getMaxNumberOfThumbnailRows() {
        return maxNumberOfThumbnailRows;
    }

    public void setMaxNumberOfThumbnailRows(Integer maxNumberOfThumbnailRows) {
        this.maxNumberOfThumbnailRows = maxNumberOfThumbnailRows;
    }

    public String getMaxNumberOfThumbnailRowsDisplay() {
        return StringUtil.asString(maxNumberOfThumbnailRows, null);
    }

    public void setMaxNumberOfThumbnailRowsDisplay(String maxNumberOfThumbnailRowsDisplay) {
        this.maxNumberOfThumbnailRows = StringUtil.asInteger(maxNumberOfThumbnailRowsDisplay, null);
    }

    public Boolean getUseShortPictureNames() {
        return useShortPictureNames;
    }

    public void setUseShortPictureNames(Boolean useShortPictureNames) {
        this.useShortPictureNames = useShortPictureNames;
    }

    public String getUseShortPictureNamesDisplay() {
        return StringUtil.asString(useShortPictureNames, null);
    }

    public void setUseShortPictureNamesDisplay(String useShortPictureNamesDisplay) {
        this.useShortPictureNames = StringUtil.asBoolean(useShortPictureNamesDisplay, Boolean.FALSE);
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumberDisplay() {
        return StringUtil.asString(orderNumber, null);
    }

    public void setOrderNumberDisplay(String orderNumberDisplay) {
        this.orderNumber = StringUtil.asInteger(orderNumberDisplay, null);
    }

    public Boolean getUseThumbnailCache() {
        return useThumbnailCache;
    }

    public void setUseThumbnailCache(Boolean useThumbnailCache) {
        this.useThumbnailCache = useThumbnailCache;
    }

    public String getUseThumbnailCacheDisplay() {
        return StringUtil.asString(useThumbnailCache, null);
    }

    public void setUseThumbnailCacheDisplay(String useThumbnailCacheDisplay) {
        this.useThumbnailCache = StringUtil.asBoolean(useThumbnailCacheDisplay, Boolean.FALSE);
    }

    public Double getThumbnailCompression() {
        return thumbnailCompression;
    }

    public void setThumbnailCompression(Double thumbnailCompression) {
        this.thumbnailCompression = thumbnailCompression;
    }

    public String getThumbnailCompressionDisplay() {
        return StringUtil.asString(thumbnailCompression, null);
    }

    public void setThumbnailCompressionDisplay(String thumbnailCompressionDisplay) {
        this.thumbnailCompression = StringUtil.asDouble(thumbnailCompressionDisplay, null);
    }

    public Integer getTypeOfFiles() {
        return typeOfFiles;
    }

    public void setTypeOfFiles(Integer typeOfFiles) {
        this.typeOfFiles = typeOfFiles;
    }

    public String getTypeOfFilesDisplay() {
        return StringUtil.asString(typeOfFiles, null);
    }

    public void setTypeOfFilesDisplay(String typeOfFilesDisplay) {
        this.typeOfFiles = StringUtil.asInteger(typeOfFilesDisplay, null);
    }

    public Integer getNumberOfMovieThumbnailColumns() {
        return numberOfMovieThumbnailColumns;
    }

    public void setNumberOfMovieThumbnailColumns(Integer numberOfMovieThumbnailColumns) {
        this.numberOfMovieThumbnailColumns = numberOfMovieThumbnailColumns;
    }

    public String getNumberOfMovieThumbnailColumnsDisplay() {
        return StringUtil.asString(numberOfMovieThumbnailColumns, null);
    }

    public void setNumberOfMovieThumbnailColumnsDisplay(String numberOfMovieThumbnailColumnsDisplay) {
        this.numberOfMovieThumbnailColumns = StringUtil.asInteger(numberOfMovieThumbnailColumnsDisplay, null);
    }

    public Integer getNumberOfMovieThumbnailRows() {
        return numberOfMovieThumbnailRows;
    }

    public void setNumberOfMovieThumbnailRows(Integer numberOfMovieThumbnailRows) {
        this.numberOfMovieThumbnailRows = numberOfMovieThumbnailRows;
    }

    public String getNumberOfMovieThumbnailRowsDisplay() {
        return StringUtil.asString(numberOfMovieThumbnailRows, null);
    }

    public void setNumberOfMovieThumbnailRowsDisplay(String numberOfMovieThumbnailRowsDisplay) {
        this.numberOfMovieThumbnailRows = StringUtil.asInteger(numberOfMovieThumbnailRowsDisplay, null);
    }

    public Integer getMaxPictureNameLength() {
        return maxPictureNameLength;
    }

    public void setMaxPictureNameLength(Integer maxPictureNameLength) {
        this.maxPictureNameLength = maxPictureNameLength;
    }

    public String getMaxPictureNameLengthDisplay() {
        return StringUtil.asString(maxPictureNameLength, null);
    }

    public void setMaxPictureNameLengthDisplay(String maxPictureNameLengthDisplay) {
        this.maxPictureNameLength = StringUtil.asInteger(maxPictureNameLengthDisplay, null);
    }

    public Boolean getShowPictureNameInTooltip() {
        return showPictureNameInTooltip;
    }

    public void setShowPictureNameInTooltip(Boolean showPictureNameInTooltip) {
        this.showPictureNameInTooltip = showPictureNameInTooltip;
    }

    public String getShowPictureNameInTooltipDisplay() {
        return StringUtil.asString(showPictureNameInTooltip, null);
    }

    public void setShowPictureNameInTooltipDisplay(String showPictureNameInTooltipDisplay) {
        this.showPictureNameInTooltip = StringUtil.asBoolean(showPictureNameInTooltipDisplay, Boolean.FALSE);
    }

    public Boolean getUseTooltip() {
        return useTooltip;
    }

    public void setUseTooltip(Boolean useTooltip) {
        this.useTooltip = useTooltip;
    }

    public String getUseTooltipDisplay() {
        return StringUtil.asString(useTooltip, null);
    }

    public void setUseTooltipDisplay(String useTooltipDisplay) {
        this.useTooltip = StringUtil.asBoolean(useTooltipDisplay, Boolean.FALSE);
    }

    public Boolean getShowFileSizeBelowPicture() {
        return showFileSizeBelowPicture;
    }

    public void setShowFileSizeBelowPicture(Boolean showFileSizeBelowPicture) {
        this.showFileSizeBelowPicture = showFileSizeBelowPicture;
    }

    public String getShowFileSizeBelowPictureDisplay() {
        return StringUtil.asString(showFileSizeBelowPicture, null);
    }

    public void setShowFileSizeBelowPictureDisplay(String showFileSizeBelowPictureDisplay) {
        this.showFileSizeBelowPicture = StringUtil.asBoolean(showFileSizeBelowPictureDisplay, Boolean.FALSE);
    }

    public Boolean getShowCommentBelowPicture() {
        return showCommentBelowPicture;
    }

    public void setShowCommentBelowPicture(Boolean showCommentBelowPicture) {
        this.showCommentBelowPicture = showCommentBelowPicture;
    }

    public String getShowCommentBelowPictureDisplay() {
        return StringUtil.asString(showCommentBelowPicture, null);
    }

    public void setShowCommentBelowPictureDisplay(String showCommentBelowPictureDisplay) {
        this.showCommentBelowPicture = StringUtil.asBoolean(showCommentBelowPictureDisplay, Boolean.FALSE);
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getMaxWidthDisplay() {
        return StringUtil.asString(maxWidth, null);
    }

    public void setMaxWidthDisplay(String maxWidthDisplay) {
        this.maxWidth = StringUtil.asInteger(maxWidthDisplay, null);
    }

    public String getDefaultResolution() {
        return defaultResolution;
    }

    public void setDefaultResolution(String defaultResolution) {
        this.defaultResolution = defaultResolution;
    }

    public Integer[] getFriendGalleries() {
        return friendGalleries;
    }

    public void setFriendGalleries(Integer[] friendGalleries) {
        this.friendGalleries = friendGalleries;
    }

    public String[] getFriendGalleriesDisplay() {
        if (friendGalleries != null) {
            String[] result = new String[friendGalleries.length];
            for (int i = 0; i < friendGalleries.length; i++) {
                result[i] = friendGalleries[i] != null ? friendGalleries[i].toString() : null;
            }
            return result;
        } else {
            return null;
        }
    }

    public void setFriendGalleriesDisplay(String[] friendGalleriesDisplay) {
        if (friendGalleriesDisplay != null) {
            this.friendGalleries = new Integer[friendGalleriesDisplay.length];
            for (int i = 0; i < friendGalleriesDisplay.length; i++) {
                this.friendGalleries[i] = StringUtil.asInteger(friendGalleriesDisplay[i], null);
            }
        } else {
            this.friendGalleries = null;
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        username = null;
        title = null;
        menuName = null;
        description = null;
        official = Boolean.FALSE;
        directory = null;
        originalDownloadable = Boolean.FALSE;
        includeSubDirectories = Boolean.FALSE;
        numberOfThumbnailsPerRow = null;
        thumbnailWidth = null;
        showLogoInGalleryPage = Boolean.FALSE;
        showDownloadLinks = Boolean.FALSE;
        logo = null;
        logoLink = null;
        useLogoSeparator = Boolean.FALSE;
        logoSeparatorHeight = null;
        logoSeparatorColor = null;
        showPictureNames = Boolean.FALSE;
        maxNumberOfThumbnailRows = null;
        useShortPictureNames = Boolean.FALSE;
        orderNumber = null;
        useThumbnailCache = Boolean.FALSE;
        thumbnailCompression = null;
        typeOfFiles = null;
        numberOfMovieThumbnailColumns = null;
        numberOfMovieThumbnailRows = null;
        maxPictureNameLength = null;
        showPictureNameInTooltip = Boolean.FALSE;
        useTooltip = Boolean.FALSE;
        showFileSizeBelowPicture = Boolean.FALSE;
        showCommentBelowPicture = Boolean.FALSE;
        maxWidth = null;
        defaultResolution = null;
        friendGalleries = null;
    }
}