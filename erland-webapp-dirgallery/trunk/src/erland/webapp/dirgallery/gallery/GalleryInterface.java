package erland.webapp.dirgallery.gallery;
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

import erland.webapp.common.EntityInterface;

public interface GalleryInterface extends EntityInterface {
    final static int PICTUREFILES = 0;
    final static int MOVIEFILES = 1;

    Integer getId();

    void setId(Integer id);

    String getUsername();

    void setUsername(String username);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Boolean getOfficial();

    void setOfficial(Boolean official);

    String getDirectory();

    void setDirectory(String directory);

    Boolean getOriginalDownloadable();

    void setOriginalDownloadable(Boolean originalDownloadable);

    Boolean getIncludeSubDirectories();

    void setIncludeSubDirectories(Boolean includeSubDirectories);

    Integer getNumberOfThumbnailsPerRow();

    void setNumberOfThumbnailsPerRow(Integer numberOfThumbnailsPerRow);

    Integer getThumbnailWidth();

    void setThumbnailWidth(Integer thumbnailWidth);

    Boolean getShowLogoInGalleryPage();

    void setShowLogoInGalleryPage(Boolean showLogoInGalleryPage);

    String getLogo();

    void setLogo(String logo);

    Boolean getShowDownloadLinks();

    void setShowDownloadLinks(Boolean showDownloadLinks);

    String getLogoLink();

    void setLogoLink(String logoLink);

    Boolean getShowPictureNames();

    void setShowPictureNames(Boolean showPictureNames);

    Integer getMaxNumberOfThumbnailRows();

    void setMaxNumberOfThumbnailRows(Integer maxNumberOfRows);

    Boolean getUseLogoSeparator();

    void setUseLogoSeparator(Boolean useLogoSeparator);

    String getLogoSeparatorColor();

    void setLogoSeparatorColor(String logoSeparatorColor);

    Integer getLogoSeparatorHeight();

    void setLogoSeparatorHeight(Integer logoSeparatorHeight);

    Boolean getUseShortPictureNames();

    void setUseShortPictureNames(Boolean useShortPictureNames);

    Integer getOrderNumber();

    void setOrderNumber(Integer orderNumber);

    Boolean getUseThumbnailCache();

    void setUseThumbnailCache(Boolean useThumbnailCache);

    Double getThumbnailCompression();

    void setThumbnailCompression(Double thumbnailCompression);

    Integer getTypeOfFiles();

    void setTypeOfFiles(Integer typeOfFiles);

    Integer getNumberOfMovieThumbnailColumns();

    void setNumberOfMovieThumbnailColumns(Integer numberOfOfMovieThumbnailColumns);

    Integer getNumberOfMovieThumbnailRows();

    void setNumberOfMovieThumbnailRows(Integer numberOfOfMovieThumbnailRows);

    String getMenuName();

    void setMenuName(String menuName);

    Integer getMaxPictureNameLength();

    void setMaxPictureNameLength(Integer maxPictureNameLength);

    Boolean getShowPictureNameInTooltip();

    void setShowPictureNameInTooltip(Boolean showPictureNameInTooltip);

    Boolean getUseTooltip();

    void setUseTooltip(Boolean useTooltip);

    Boolean getShowFileSizeBelowPicture();

    void setShowFileSizeBelowPicture(Boolean showFileSizeBelowPicture);

    Boolean getShowCommentBelowPicture();

    void setShowCommentBelowPicture(Boolean showCommentBelowPicture);
}