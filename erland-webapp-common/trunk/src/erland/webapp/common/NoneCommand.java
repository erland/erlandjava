package erland.webapp.common;

import javax.servlet.http.HttpServletRequest;

public class NoneCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        return null;
    }
}
