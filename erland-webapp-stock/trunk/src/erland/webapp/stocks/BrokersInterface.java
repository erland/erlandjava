package erland.webapp.stocks;

import java.util.Iterator;

public interface BrokersInterface {
    Iterator getBrokers();

    String getBrokerName(String brokerCode);

    Iterator getStocks(String brokerCode);

    String getStockName(String brokerCode, String stockCode);
}
