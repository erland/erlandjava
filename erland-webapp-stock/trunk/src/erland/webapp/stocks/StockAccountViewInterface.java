package erland.webapp.stocks;


public interface StockAccountViewInterface {
    public StockAccountTransactionListInterface getPermanentEntries();
    public StockAccountTransactionListInterface getPurchaseOnceEntries();
    public StockAccountTransactionListInterface getPurchaseContinouslyEntries();
}
