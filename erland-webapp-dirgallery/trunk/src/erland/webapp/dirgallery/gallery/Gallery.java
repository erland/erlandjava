package erland.webapp.dirgallery.gallery;

import erland.webapp.common.BaseEntity;

public class Gallery extends BaseEntity implements GalleryInterface {
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

    public Boolean getIncludeSubDirectories() {
        return includeSubDirectories;
    }

    public void setIncludeSubDirectories(Boolean includeSubDirectories) {
        this.includeSubDirectories = includeSubDirectories;
    }

    public Integer getNumberOfThumbnailsPerRow() {
        return numberOfThumbnailsPerRow;
    }

    public void setNumberOfThumbnailsPerRow(Integer numberOfThumbnailsPerRow) {
        this.numberOfThumbnailsPerRow = numberOfThumbnailsPerRow;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Boolean getShowLogoInGalleryPage() {
        return showLogoInGalleryPage;
    }

    public void setShowLogoInGalleryPage(Boolean showLogoInGalleryPage) {
        this.showLogoInGalleryPage = showLogoInGalleryPage;
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

    public Boolean getShowDownloadLinks() {
        return showDownloadLinks;
    }

    public void setShowDownloadLinks(Boolean showDownloadLinks) {
        this.showDownloadLinks = showDownloadLinks;
    }

    public Boolean getShowPictureNames() {
        return showPictureNames;
    }

    public void setShowPictureNames(Boolean showPictureNames) {
        this.showPictureNames = showPictureNames;
    }

    public Integer getMaxNumberOfThumbnailRows() {
        return maxNumberOfThumbnailRows;
    }

    public void setMaxNumberOfThumbnailRows(Integer maxNumberOfThumbnailRows) {
        this.maxNumberOfThumbnailRows = maxNumberOfThumbnailRows;
    }

    public Boolean getUseLogoSeparator() {
        return useLogoSeparator;
    }

    public void setUseLogoSeparator(Boolean useLogoSeparator) {
        this.useLogoSeparator = useLogoSeparator;
    }

    public Integer getLogoSeparatorHeight() {
        return logoSeparatorHeight;
    }

    public void setLogoSeparatorHeight(Integer logoSeparatorHeight) {
        this.logoSeparatorHeight = logoSeparatorHeight;
    }

    public String getLogoSeparatorColor() {
        return logoSeparatorColor;
    }

    public void setLogoSeparatorColor(String logoSeparatorColor) {
        this.logoSeparatorColor = logoSeparatorColor;
    }

    public Boolean getUseShortPictureNames() {
        return useShortPictureNames;
    }

    public void setUseShortPictureNames(Boolean useShortPictureNames) {
        this.useShortPictureNames = useShortPictureNames;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getUseThumbnailCache() {
        return useThumbnailCache;
    }

    public void setUseThumbnailCache(Boolean useThumbnailCache) {
        this.useThumbnailCache = useThumbnailCache;
    }

    public Double getThumbnailCompression() {
        return thumbnailCompression;
    }

    public void setThumbnailCompression(Double thumbnailCompression) {
        this.thumbnailCompression = thumbnailCompression;
    }

    public Integer getTypeOfFiles() {
        return typeOfFiles;
    }

    public void setTypeOfFiles(Integer typeOfFiles) {
        this.typeOfFiles = typeOfFiles;
    }

    public Integer getNumberOfMovieThumbnailColumns() {
        return numberOfMovieThumbnailColumns;
    }

    public void setNumberOfMovieThumbnailColumns(Integer numberOfOfMovieThumbnailColumns) {
        this.numberOfMovieThumbnailColumns = numberOfOfMovieThumbnailColumns;
    }

    public Integer getNumberOfMovieThumbnailRows() {
        return numberOfMovieThumbnailRows;
    }

    public void setNumberOfMovieThumbnailRows(Integer numberOfOfMovieThumbnailRows) {
        this.numberOfMovieThumbnailRows = numberOfOfMovieThumbnailRows;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMaxPictureNameLength() {
        return maxPictureNameLength;
    }

    public void setMaxPictureNameLength(Integer maxPictureNameLength) {
        this.maxPictureNameLength = maxPictureNameLength;
    }

    public Boolean getShowPictureNameInTooltip() {
        return showPictureNameInTooltip;
    }

    public void setShowPictureNameInTooltip(Boolean showPictureNameInTooltip) {
        this.showPictureNameInTooltip = showPictureNameInTooltip;
    }

    public Boolean getUseTooltip() {
        return useTooltip;
    }

    public void setUseTooltip(Boolean useTooltip) {
        this.useTooltip = useTooltip;
    }

    public Boolean getShowFileSizeBelowPicture() {
        return showFileSizeBelowPicture;
    }

    public void setShowFileSizeBelowPicture(Boolean showFileSizeBelowPicture) {
        this.showFileSizeBelowPicture = showFileSizeBelowPicture;
    }

    public Boolean getShowCommentBelowPicture() {
        return showCommentBelowPicture;
    }

    public void setShowCommentBelowPicture(Boolean showCommentBelowPicture) {
        this.showCommentBelowPicture = showCommentBelowPicture;
    }
}
