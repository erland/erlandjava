package erland.webapp.dirgallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditPictureCommentCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String galleryString = request.getParameter("gallery");
        String comment = request.getParameter("comment");
        Integer gallery = null;
        if (galleryString != null && galleryString.length() > 0) {
            gallery = Integer.valueOf(galleryString);
        }
        if (gallery != null && id != null) {
            GalleryInterface template = (GalleryInterface) environment.getEntityFactory().create("gallery");
            template.setId(gallery);
            GalleryInterface galleryEntity = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            User user = (User) request.getSession().getAttribute("user");
            if (galleryEntity != null && user != null && user.getUsername().equals(galleryEntity.getUsername())) {
                Picture pictureTemplate = (Picture) environment.getEntityFactory().create("picture");
                pictureTemplate.setDirectory(galleryEntity.getDirectory());
                pictureTemplate.setGallery(galleryEntity.getId());
                pictureTemplate.setId(id);
                Picture picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(pictureTemplate);
                if (picture != null) {
                    PictureComment commentTemplate = (PictureComment) environment.getEntityFactory().create("picturecomment");
                    commentTemplate.setId(picture.getFullPath());
                    if (comment == null || comment.length() == 0) {
                        environment.getEntityStorageFactory().getStorage("picturecomment").delete(commentTemplate);
                    } else {
                        commentTemplate.setComment(comment);
                        environment.getEntityStorageFactory().getStorage("picturecomment").store(commentTemplate);
                    }
                }
            }
        }
        return null;
    }
}
