package erland.webapp.gallery.guestaccount;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchGuestAccountsCommand implements CommandInterface, ViewGuestAccountsInterface {
    private WebAppEnvironmentInterface environment;
    private GuestAccount[] accounts;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username",user.getUsername());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("guestaccount").search(filter);
        accounts = new GuestAccount[entities.length];
        for (int i = 0; i < entities.length; i++) {
            accounts[i] = (GuestAccount) entities[i];
        }
        return null;
    }

    public GuestAccount[] getAccounts() {
        return accounts;
    }
}
