package erland.webapp.gallery.guestaccount;

import erland.webapp.common.WebAppEnvironmentInterface;

public class GuestAccountHelper {
    public static boolean isGuestUser(WebAppEnvironmentInterface environment, String username, String guestuser) {
        GuestAccount template = (GuestAccount) environment.getEntityFactory().create("guestaccount");
        template.setUsername(username);
        template.setGuestUser(guestuser);
        GuestAccount account = (GuestAccount) environment.getEntityStorageFactory().getStorage("guestaccount").load(template);
        if(account!=null) {
            return true;
        }else {
            return false;
        }
    }
}
