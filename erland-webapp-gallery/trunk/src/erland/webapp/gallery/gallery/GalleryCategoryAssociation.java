package erland.webapp.gallery.gallery;

import erland.webapp.common.BaseEntity;

public class GalleryCategoryAssociation extends BaseEntity {
    private Integer gallery;
    private Integer category;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}