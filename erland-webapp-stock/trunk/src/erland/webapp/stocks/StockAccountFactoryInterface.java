package erland.webapp.stocks;

import javax.servlet.http.HttpServletRequest;

public interface StockAccountFactoryInterface {
    public StockAccount getAccount(HttpServletRequest request);
}
