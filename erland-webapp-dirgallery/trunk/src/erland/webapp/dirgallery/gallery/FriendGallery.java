package erland.webapp.dirgallery.gallery;

import erland.webapp.common.BaseEntity;

public class FriendGallery extends BaseEntity {
    private Integer gallery;
    private Integer friendGallery;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public Integer getFriendGallery() {
        return friendGallery;
    }

    public void setFriendGallery(Integer friendGallery) {
        this.friendGallery = friendGallery;
    }
}
