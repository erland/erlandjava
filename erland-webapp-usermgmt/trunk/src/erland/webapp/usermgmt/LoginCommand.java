package erland.webapp.usermgmt;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandOptionsInterface;
import erland.util.Log;
import erland.util.ParameterValueStorageExInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements CommandInterface, CommandOptionsInterface {
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
        User user = (User)environment.getEntityFactory().create("user");
        user.setUsername(req.getParameter("name"));
        user.setPassword(req.getParameter("password"));
        Log.println(this,"Trying to login as "+user.getUsername()+","+user.getPassword());
        if(user.login(req.getParameter("application"))) {
            Log.println(this,"Successfully login as "+user.getUsername());
            String userAttribute = options.getParameter("userattribute");
            if(userAttribute==null) {
                userAttribute = "user";
            }
            session.setAttribute(userAttribute,user);
            return "success";
        }else {
            Log.println(this,"Fail to login as "+user.getUsername());
            return "failure";
        }
    }
}
