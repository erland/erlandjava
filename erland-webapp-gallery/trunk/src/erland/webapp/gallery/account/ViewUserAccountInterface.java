package erland.webapp.gallery.account;

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.gallery.GalleryInterface;

public interface ViewUserAccountInterface {
    UserAccount getAccount();
    User getUser();
    GalleryInterface[] getGalleries();
}
