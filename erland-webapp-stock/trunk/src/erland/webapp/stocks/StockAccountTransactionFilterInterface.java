package erland.webapp.stocks;

public interface StockAccountTransactionFilterInterface {
    boolean isMatching(StockAccountTransaction transaction);
}
