package erland.webapp.stocks;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

public class StockAccountStockEntry implements EntityInterface {
    private String broker;
    private String stock;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String getBroker() {
        return broker;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
