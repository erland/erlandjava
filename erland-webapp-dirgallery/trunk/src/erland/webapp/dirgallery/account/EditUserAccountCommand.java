package erland.webapp.dirgallery.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditUserAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            if (user.hasRole("manager")) {
                username = request.getParameter("username");
                if (username == null || username.length() == 0) {
                    username = user.getUsername();
                }
            }
            String welcomeText = request.getParameter("welcometext");
            String copyright = request.getParameter("copyright");
            String description = request.getParameter("description");
            String logo = request.getParameter("logo");
            Boolean official = ServletParameterHelper.asBoolean(request.getParameter("official"));
            Integer defaultGallery = ServletParameterHelper.asInteger(request.getParameter("defaultgallery"));
            UserAccount account = (UserAccount) environment.getEntityFactory().create("dirgalleryuseraccount");
            account.setUsername(username);
            account.setWelcomeText(welcomeText);
            account.setDescription(description);
            account.setLogo(logo);
            account.setOfficial(official);
            account.setDefaultGallery(defaultGallery);
            account.setCopyrightText(copyright);
            environment.getEntityStorageFactory().getStorage("dirgalleryuseraccount").store(account);
        }
        return null;
    }
}
