package erland.webapp.gallery.gallery;

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.guestaccount.GuestAccountHelper;

import javax.servlet.http.HttpServletRequest;

public class SearchOfficialGalleriesCommand extends SearchGalleriesCommand {
    private Boolean official;
    protected void initCommand(HttpServletRequest request) {
        official=Boolean.TRUE;
        String username = request.getParameter("user");
        User user = (User) request.getSession().getAttribute("guestuser");
        if(user!=null && username!=null) {
            if(GuestAccountHelper.isGuestUser(getEnvironment(),username,user.getUsername())) {
                official = Boolean.FALSE;
            }
        }
    }
    protected String getQueryFilter() {
        if(official.booleanValue()) {
            return "allofficialforuser";
        }else {
            return super.getQueryFilter();
        }
    }
}
