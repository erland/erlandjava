package erland.webapp.stocks;

import erland.webapp.common.WebAppEnvironmentInterface;

public interface StockServletEnvironmentInterface extends WebAppEnvironmentInterface {
    BrokerConnectionFactoryInterface getBrokerConnectionFactory();

    StockAccountFactoryInterface getStockAccountFactory();

    BrokersInterface getBrokers();
}
