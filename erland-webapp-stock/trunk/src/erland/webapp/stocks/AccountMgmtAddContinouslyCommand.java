package erland.webapp.stocks;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.StockAccount;

import javax.servlet.http.HttpServletRequest;

public class AccountMgmtAddContinouslyCommand implements CommandInterface {
    StockAccount account;

    private StockServletEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = (StockServletEnvironmentInterface) environment;
    }

    public String execute(HttpServletRequest request) {
        account = environment.getStockAccountFactory().getAccount(request);
        String broker = request.getParameter("broker");
        String stock = request.getParameter("stock");
        String value = request.getParameter("value");
        String purchaseDate = request.getParameter("purchasedate");
        String interval = request.getParameter("interval");
        if(broker!=null&&broker.length()>0 &&
                stock!=null&&stock.length()>0 &&
                purchaseDate!=null&&purchaseDate.length()>0) {

            if(value!=null&&value.length()>0) {
                double valueOfStocks = Double.valueOf(value).doubleValue();
                account.addStockContinously(broker,stock,purchaseDate,valueOfStocks,interval);
            }
        }
        return null;
    }
}
