package erland.webapp.usermgmt;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandOptionsInterface;
import erland.util.Log;
import erland.util.ParameterValueStorageExInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginStatusCommand implements CommandInterface, CommandOptionsInterface {
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface options;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public void setOptions(ParameterValueStorageExInterface options) {
        this.options = options;
    }
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        String userAttribute = options.getParameter("userattribute");
        if(userAttribute==null) {
            userAttribute = "user";
        }
        if(session!=null) {
            User user = (User)session.getAttribute(userAttribute);
            if(user!=null && user.isValid()) {
                return "user";
            }
        }
        if(req.getParameter(userAttribute)!=null) {
            return "guest";
        }
        return "none";
    }
}
