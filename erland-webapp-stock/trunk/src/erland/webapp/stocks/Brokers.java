package erland.webapp.stocks;

import java.util.Iterator;
import java.util.Vector;

public class Brokers implements BrokersInterface {
    private StockServletEnvironmentInterface environment;

    public Brokers(StockServletEnvironmentInterface environment) {
        this.environment = environment;
    }
    public Iterator getBrokers() {
        Vector availableBrokers = new Vector();
        availableBrokers.addElement("robur");
        availableBrokers.addElement("sb");
        return availableBrokers.iterator();
    }
    public String getBrokerName(String brokerCode) {
        BrokerConnectionInterface brokerCls = environment.getBrokerConnectionFactory().create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getName();
        }
        return "";
    }
    public Iterator getStocks(String brokerCode) {
        BrokerConnectionInterface brokerCls = environment.getBrokerConnectionFactory().create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getAvailableStocks();
        }
        return new Vector().iterator();
    }
    public String getStockName(String brokerCode, String stockCode) {
        Iterator it = getStocks(brokerCode);
        while(it.hasNext()) {
            BrokersStockEntry stock = (BrokersStockEntry) it.next();
            if(stock.getCode().equals(stockCode)) {
                return stock.getName();
            }
        }
        return "???";
    }
}
