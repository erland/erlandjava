package erland.webapp.gallery.guestaccount;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class AddGuestAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String guestuser = request.getParameter("guestuser");
        User user = (User) request.getSession().getAttribute("user");
        User template = (User) environment.getEntityFactory().create("user");
        template.setUsername(guestuser);
        if(environment.getEntityStorageFactory().getStorage("user").load(template)!=null) {
            GuestAccount accountTemplate = (GuestAccount) environment.getEntityFactory().create("guestaccount");
            accountTemplate.setUsername(user.getUsername());
            accountTemplate.setGuestUser(guestuser);
            environment.getEntityStorageFactory().getStorage("guestaccount").store(accountTemplate);
        }
        return null;
    }
}
