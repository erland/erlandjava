package erland.webapp.stocks;

import java.util.Iterator;

public interface StockAccountStockEntryListInterface {
    Iterator iterator();

    int size();

    StockAccountStockEntry getStock(int pos);
}
