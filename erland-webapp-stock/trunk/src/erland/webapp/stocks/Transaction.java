package erland.webapp.stocks;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityReadUpdateInterface;

import java.util.Date;

public class Transaction implements EntityInterface, EntityReadUpdateInterface {
    private WebAppEnvironmentInterface environment;
    private Integer accountId;
    private Integer type;
    private String brokerId;
    private String stockId;
    private Date date;
    private Double price;
    private Double value;
    private static final Double ZERO = new Double(0);
    private static final Double NOTUSED = new Double(-1);

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void preReadUpdate() {

    }
    public void postReadUpdate() {
        switch(type.intValue()) {
            case 1:
            case 2:
                setPrice(getValue());
                setValue(NOTUSED);
                break;
            case 3:
            case 4:
                setPrice(ZERO);
                break;
            case 5:
            case 6:
                Double price = getPrice();
                setPrice(getValue());
                setValue(price);
            default:
                break;
        }
    }
}
