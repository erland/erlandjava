package erland.webapp.common;

import javax.servlet.http.HttpServletRequest;

public interface CommandInterface {
    public void init(WebAppEnvironmentInterface environment);
    public String execute(HttpServletRequest request);
}
