package erland.webapp.stocks;

public interface StockStorageInterface {
    public StockInterface getStock(String broker, String stock);
}
