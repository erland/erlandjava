package erland.webapp.stocks;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

public class Account implements EntityInterface {
    private WebAppEnvironmentInterface environment;
    private Integer accountId;
    private String username;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
