package erland.webapp.stocks;

import java.util.Iterator;
import java.util.Date;

public interface StockAccountTransactionListInterface {
    public Iterator iterator();
    public StockAccountTransaction getTransaction(int i);
    public int size();
    public StockAccountTransaction getTransaction(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
    public StockAccountTransactionListInterface getTransactions(String broker, String stock, Date fromDate, Date toDate, StockAccountTransactionFilterInterface filter);
    public StockAccountTransaction getTransactionBefore(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
    public StockAccountTransaction getTransactionAfter(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter);
}
