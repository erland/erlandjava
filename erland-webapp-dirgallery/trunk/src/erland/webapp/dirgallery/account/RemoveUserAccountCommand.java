package erland.webapp.dirgallery.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        UserAccount template = (UserAccount) environment.getEntityFactory().create("dirgalleryuseraccount");
        template.setUsername(username);
        environment.getEntityStorageFactory().getStorage("dirgalleryuseraccount").delete(template);
        return null;
    }
}
