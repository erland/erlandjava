package erland.webapp.diary.account;

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
        UserAccount template = (UserAccount)environment.getEntityFactory().create("diaryuseraccount");
        template.setUsername(username);
        environment.getEntityStorageFactory().getStorage("diaryuseraccount").delete(template);
        return null;
    }
}
