package erland.webapp.stocks;

import java.util.Date;

public class StockAccountTransaction {
    private String broker;
    private String stock;
    private Date date;
    private double value;
    private double number;

    public StockAccountTransaction(String broker, String stock, Date date, double number, double value) {
        this.broker = broker;
        this.stock = stock;
        this.date = date;
        this.number = number;
        this.value = value;
    }

    public String getBroker() {
        return broker;
    }

    public String getStock() {
        return stock;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public double getNumber() {
        return number;
    }
}
