package erland.webapp.gallery.gallery.picture;

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.guestaccount.GuestAccountHelper;

import javax.servlet.http.HttpServletRequest;

public class SearchOfficialPicturesCommand extends SearchPicturesCommand {
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

    protected String getAllFilter() {
        if(official.booleanValue()) {
            return "allofficialforgallery";
        }else {
            return super.getAllFilter();
        }
    }

    protected String getCategoryTreeFilter() {
        if(official.booleanValue()) {
            return "allofficialforgalleryandcategorylist";
        }else {
            return super.getCategoryTreeFilter();
        }
    }
}