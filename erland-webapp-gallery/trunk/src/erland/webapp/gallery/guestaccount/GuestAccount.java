package erland.webapp.gallery.guestaccount;

import erland.webapp.common.BaseEntity;

public class GuestAccount extends BaseEntity {
    private String username;
    private String guestUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGuestUser() {
        return guestUser;
    }

    public void setGuestUser(String guestUser) {
        this.guestUser = guestUser;
    }
}
