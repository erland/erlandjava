package erland.webapp.usermgmt;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandOptionsInterface;
import erland.util.ParameterValueStorageExInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements CommandInterface, CommandOptionsInterface {
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface options;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public void setOptions(ParameterValueStorageExInterface options) {
        this.options = options;
    }
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String userAttribute = options.getParameter("userattribute");
        if(userAttribute==null) {
            userAttribute = "user";
        }
        session.removeAttribute(userAttribute);
        String invalidateSession = options.getParameter("invalidatesession");
        if(invalidateSession==null || invalidateSession.equalsIgnoreCase("true")) {
            session.invalidate();
        }
        return null;
    }
}
