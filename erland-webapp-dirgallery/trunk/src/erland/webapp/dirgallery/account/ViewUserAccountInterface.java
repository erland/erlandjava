package erland.webapp.dirgallery.account;

import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.usermgmt.User;

public interface ViewUserAccountInterface {
    UserAccount getAccount();

    User getUser();

    GalleryInterface[] getGalleries();
}
