package erland.webapp.stocks;

import erland.webapp.usermgmt.User;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class StockAccountFactory implements StockAccountFactoryInterface {
    private WebAppEnvironmentInterface environment;

    public StockAccountFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public StockAccount getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        StockAccount account = (StockAccount) session.getAttribute("stockaccount");
        if(account==null) {
            User user = (User) session.getAttribute("user");
            if(user!=null && user.isValid()) {
                account = (StockAccount)environment.getEntityFactory().create("stockaccount");
                account.init(user.getUsername(), StockStorage.getInstance(environment));
                session.setAttribute("stockaccount",account);
            }
        }
        return account;
    }
}
