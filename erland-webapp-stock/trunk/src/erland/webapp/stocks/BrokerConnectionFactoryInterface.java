package erland.webapp.stocks;

public interface BrokerConnectionFactoryInterface {
    public BrokerConnectionInterface create(String broker);
}
