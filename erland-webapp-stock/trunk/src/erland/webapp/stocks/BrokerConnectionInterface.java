package erland.webapp.stocks;

import erland.webapp.common.WebAppEnvironmentInterface;

import java.util.Iterator;

public interface BrokerConnectionInterface {
    public void init(WebAppEnvironmentInterface environment);
    public String getName();
    public Iterator getAvailableStocks();
    public String getStock(String stock);
}
