package erland.webapp.common;

import javax.servlet.http.HttpServletRequest;

public class MenuCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;
    private String menu;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        menu = request.getParameter("menu");
        return null;
    }

    public boolean isExpanded(String menu) {
        return (this.menu!=null && this.menu.startsWith(menu));
    }
}
