package erland.webapp.usermgmt;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

public class UserApplicationRole implements EntityInterface {
    private WebAppEnvironmentInterface environment;
    private String username;
    private String application;
    private String role;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
