package erland.webapp.gallery.gallery.category;

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.guestaccount.GuestAccountHelper;

import javax.servlet.http.HttpServletRequest;

public class SearchOfficialCategoriesCommand extends SearchCategoriesCommand {
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
    protected String getNoParentFilter() {
        if(official.booleanValue()) {
            return "allofficialforgallerywithoutparent";
        }else {
            return super.getNoParentFilter();
        }
    }

    protected String getParentFilter() {
        if(official.booleanValue()) {
            return "allofficialforgallerywithparent";
        }else {
            return super.getParentFilter();
        }
    }
}