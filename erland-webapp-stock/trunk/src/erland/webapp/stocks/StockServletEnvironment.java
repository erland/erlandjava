package erland.webapp.stocks;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.WebAppEnvironmentCustomizable;

public class StockServletEnvironment extends WebAppEnvironmentCustomizable implements StockServletEnvironmentInterface {
    private BrokerConnectionFactoryInterface brokerConnectionFactory;
    private StockAccountFactoryInterface stockAccountFactory;
    private BrokersInterface brokers;

    public StockServletEnvironment(WebAppEnvironmentInterface environment) {
        super(environment);
    }

    public BrokerConnectionFactoryInterface getBrokerConnectionFactory() {
        if(brokerConnectionFactory==null) {
            brokerConnectionFactory = new BrokerConnectionFactory(this);
        }
        return brokerConnectionFactory;
    }
    public StockAccountFactoryInterface getStockAccountFactory() {
        if(stockAccountFactory==null) {
            stockAccountFactory = new StockAccountFactory(this);
        }
        return stockAccountFactory;
    }
    public BrokersInterface getBrokers() {
        if(brokers==null) {
            brokers = new Brokers(this);
        }
        return brokers;
    }
}
