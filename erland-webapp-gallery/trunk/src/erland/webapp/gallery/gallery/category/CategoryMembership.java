package erland.webapp.gallery.gallery.category;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.BaseEntity;

public class CategoryMembership extends BaseEntity {
    private Integer gallery;
    private Integer category;
    private Integer memberCategory;

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

    public Integer getMemberCategory() {
        return memberCategory;
    }

    public void setMemberCategory(Integer memberCategory) {
        this.memberCategory = memberCategory;
    }
}