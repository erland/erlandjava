package erland.webapp.dirgallery.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.usermgmt.UserApplicationRole;

import javax.servlet.http.HttpServletRequest;

public class NewUserAccountCommand implements CommandInterface, ViewUserAccountInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount account;
    private GalleryInterface[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String welcomeText = request.getParameter("welcometext");
        if (username != null && username.length() > 0 &&
                firstname != null && firstname.length() > 0 &&
                lastname != null && lastname.length() > 0 &&
                password1 != null && password1.length() > 0 &&
                password2 != null && password2.length() > 0) {

            if (password1.equals(password2)) {
                User template = (User) environment.getEntityFactory().create("user");
                template.setUsername(username);
                User user = (User) environment.getEntityStorageFactory().getStorage("user").load(template);
                if (user == null) {
                    template.setFirstName(firstname);
                    template.setLastName(lastname);
                    template.setPassword(password1);
                    environment.getEntityStorageFactory().getStorage("user").store(template);
                    UserApplicationRole templateRole = (UserApplicationRole) environment.getEntityFactory().create("userapplicationrole");
                    templateRole.setUsername(username);
                    templateRole.setApplication("dirgallery");
                    templateRole.setRole("user");
                    environment.getEntityStorageFactory().getStorage("userapplicationrole").store(templateRole);
                    UserAccount templateAccount = (UserAccount) environment.getEntityFactory().create("dirgalleryuseraccount");
                    templateAccount.setUsername(username);
                    templateAccount.setWelcomeText(welcomeText);
                    environment.getEntityStorageFactory().getStorage("dirgalleryuseraccount").store(templateAccount);
                    account = templateAccount;
                    return "success";
                }
            }
        }
        return "failure";
    }

    public UserAccount getAccount() {
        return account;
    }

    public User getUser() {
        User template = (User) environment.getEntityFactory().create("userinfo");
        template.setUsername(account.getUsername());
        User user = (User) environment.getEntityStorageFactory().getStorage("userinfo").load(template);
        return user;
    }

    public GalleryInterface[] getGalleries() {
        if (galleries == null) {
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username", account.getUsername());
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery").search(filter);
            galleries = new GalleryInterface[entities.length];
            for (int i = 0; i < entities.length; i++) {
                galleries[i] = (GalleryInterface) entities[i];
            }
        }
        return galleries;
    }
}
