package erland.webapp.gallery.account;

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.gallery.Gallery;

public interface ViewUserAccountInterface {
    UserAccount getAccount();
    User getUser();
    Gallery[] getGalleries();
}
