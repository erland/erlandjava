package erland.webapp.dirgallery.gallery.picture;

import erland.webapp.common.BaseEntity;

public class PictureComment extends BaseEntity {
    private String id;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
