package erland.webapp.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public interface CommandResponseInterface {
    public void makeResponse(HttpServletRequest request, HttpServletResponse response);
}
