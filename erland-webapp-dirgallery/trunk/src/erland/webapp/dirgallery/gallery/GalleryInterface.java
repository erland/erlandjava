package erland.webapp.dirgallery.gallery;

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
}