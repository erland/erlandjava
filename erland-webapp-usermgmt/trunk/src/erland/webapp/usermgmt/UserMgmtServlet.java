package erland.webapp.usermgmt;

import erland.webapp.common.BaseServlet;
import erland.util.Log;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class UserMgmtServlet extends BaseServlet {
    protected boolean isCommandAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String cmd = getCommandClassName(request);
        if(session!=null) {
            User user = (User) session.getAttribute("user");
            if(user!=null && user.isValid()) {
                String role = getEnvironment().getResources().getParameter("commands."+cmd+".role");
                if(role==null || user.hasRole(role)) {
                    Log.println(this,"isCommandAllowed: allowed user and role is valid");
                    return true;
                }else {
                    Log.println(this,"isCommandAllowed: not allowed role is not valid");
                    return false;
                }
            }
        }else {
            Log.println(this,"isCommandAllowed: no session");
        }
        if(getEnvironment().getResources().getParameter("commands."+cmd+".role")==null) {
            Log.println(this,"isCommandAllowed: command is valid"+cmd);
            return true;
        }
        Log.println(this,"isCommandAllowed: command is not valid "+cmd);
        return false;
    }

    protected String getNotAllowedPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null) {
            User user = (User)session.getAttribute("user");
            if(user!=null && user.isValid()) {
                return "accessdenied";
            }
        }
        return super.getNotAllowedPage(request);
    }
}
