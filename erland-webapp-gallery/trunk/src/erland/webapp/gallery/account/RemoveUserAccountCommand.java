package erland.webapp.gallery.account;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        UserAccount template = (UserAccount)environment.getEntityFactory().create("galleryuseraccount");
        template.setUsername(username);
        environment.getEntityStorageFactory().getStorage("galleryuseraccount").delete(template);
        return null;
    }
}
