package erland.webapp.usermgmt;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.util.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginStatusCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session!=null) {
            User user = (User)session.getAttribute("user");
            if(user!=null && user.isValid()) {
                return "user";
            }
        }
        if(req.getParameter("user")!=null) {
            return "guest";
        }
        return "none";
    }
}
