package erland.webapp.usermgmt;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements CommandInterface {
    public void init(WebAppEnvironmentInterface environment) {
    }

    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.removeAttribute("user");
        return null;
    }
}
