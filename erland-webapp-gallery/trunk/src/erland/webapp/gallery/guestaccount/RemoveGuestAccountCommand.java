package erland.webapp.gallery.guestaccount;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class RemoveGuestAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String guestuser = request.getParameter("guestuser");
        User user = (User) request.getSession().getAttribute("user");
        GuestAccount template = (GuestAccount) environment.getEntityFactory().create("guestaccount");
        template.setGuestUser(guestuser);
        template.setUsername(user.getUsername());
        environment.getEntityStorageFactory().getStorage("guestaccount").delete(template);
        return null;
    }
}
