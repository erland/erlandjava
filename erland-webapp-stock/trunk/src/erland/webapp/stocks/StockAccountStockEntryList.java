package erland.webapp.stocks;

import java.util.Vector;
import java.util.Iterator;

public class StockAccountStockEntryList implements StockAccountStockEntryListInterface {
    private Vector stocks = new Vector();

    public void addStock(StockAccountStockEntry s) {
        stocks.addElement(s);
    }
    public Iterator iterator() {
        return stocks.iterator();
    }
    public int size() {
        return stocks.size();
    }
    public StockAccountStockEntry getStock(int pos) {
        return (StockAccountStockEntry) stocks.elementAt(pos);
    }
}
