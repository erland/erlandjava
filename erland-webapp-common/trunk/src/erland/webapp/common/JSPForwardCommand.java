package erland.webapp.common;

import erland.webapp.common.CommandInterface;

import javax.servlet.http.HttpServletRequest;

public class JSPForwardCommand implements CommandInterface {
    public void init(WebAppEnvironmentInterface environment) {
    }

    public String execute(HttpServletRequest request) {
        String page = request.getParameter("page");
        if(page!=null) {
            return page;
        }else {
            return null;
        }
    }
}
