package erland.webapp.dirgallery.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchUserAccountsCommand implements CommandInterface, ViewUserAccountsInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount[] accounts;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(getQueryFilter());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("dirgalleryuseraccount").search(filter);
        accounts = new UserAccount[entities.length];
        for (int i = 0; i < entities.length; i++) {
            accounts[i] = (UserAccount) entities[i];
        }
        return null;
    }

    public UserAccount[] getAccounts() {
        return accounts;
    }

    public User getUser(UserAccount account) {
        User template = (User) environment.getEntityFactory().create("userinfo");
        template.setUsername(account.getUsername());
        User user = (User) environment.getEntityStorageFactory().getStorage("userinfo").load(template);
        return user;
    }

    protected String getQueryFilter() {
        return "all";
    }
}
