package erland.webapp.stocks;


public class SessionStockAccount extends StockAccount {
    private StockAccountTransactionList permanentStocks = new StockAccountTransactionList();
    private StockAccountTransactionList oneTimePurchase = new StockAccountTransactionList();
    private StockAccountTransactionList multiplePurchase = new StockAccountTransactionList();

    public boolean removeStockContinously(String broker, String stock, String date) {
        return multiplePurchase.removeTransaction(broker, stock, date);
    }

    public boolean removeStockSingle(String broker, String stock, String date) {
        return oneTimePurchase.removeTransaction(broker, stock, date);
    }

    public boolean removeStockPermanent(String broker, String stock, String date) {
        return permanentStocks.removeTransaction(broker, stock, date);
    }

    public boolean addStockContinously(String broker, String stock, String date, double valueOfStocks, String interval) {
        return multiplePurchase.addTransaction(broker, stock, date, valueOfStocks, 0);
    }

    public boolean addStockSingle(String broker, String stock, String date, double numberOfStocks) {
        return oneTimePurchase.addTransaction(broker, stock, date, numberOfStocks, -1);
    }

    public boolean addStockSingleForValue(String broker, String stock, String date, double valueOfStocks) {
        return oneTimePurchase.addTransaction(broker,stock,date,0,valueOfStocks);
    }

    public boolean addStockSingleForPrice(String broker, String stock, String date, double numberOfStocks, double priceOfStocks) {
        return oneTimePurchase.addTransaction(broker,stock,date,numberOfStocks,priceOfStocks);
    }

    public boolean addStockPermanent(String broker, String stock, String date, double number) {
        return permanentStocks.addTransaction(broker,stock,date,number,-1);
    }

    public boolean addStockPermanentForPrice(String broker, String stock, String date, double number,double price) {
        return permanentStocks.addTransaction(broker,stock,date,number,price);
    }

    public StockAccountTransactionListInterface getPermanentEntries() {
        return permanentStocks;
    }

    public StockAccountTransactionListInterface getPurchaseOnceEntries() {
        return oneTimePurchase;
    }

    public StockAccountTransactionListInterface getPurchaseContinouslyEntries() {
        return multiplePurchase;
    }

    private boolean inStockBrokerList(StockAccountStockEntryListInterface v, String broker, String stock) {
        for(int i=0;i<v.size();i++) {
            StockAccountStockEntry entry = v.getStock(i);
            if(entry.getBroker().equals(broker) &&
                    entry.getStock().equals(stock)) {
                return true;
            }
        }
        return false;
    }
    public StockAccountStockEntryListInterface getStocks() {
        StockAccountStockEntryList result = new StockAccountStockEntryList();

        for(int i=0;i<permanentStocks.size();i++) {
            StockAccountTransaction entry = permanentStocks.getTransaction(i);
            if(!inStockBrokerList(result,entry.getBroker(), entry.getStock())) {
                StockAccountStockEntry stock = new StockAccountStockEntry();
                stock.setBroker(entry.getBroker());
                stock.setStock(entry.getStock());
                result.addStock(stock);
            }
        }
        for(int i=0;i<oneTimePurchase.size();i++) {
            StockAccountTransaction entry = oneTimePurchase.getTransaction(i);
            if(!inStockBrokerList(result,entry.getBroker(), entry.getStock())) {
                StockAccountStockEntry stock = new StockAccountStockEntry();
                stock.setBroker(entry.getBroker());
                stock.setStock(entry.getStock());
                result.addStock(stock);
            }
        }
        for(int i=0;i<multiplePurchase.size();i++) {
            StockAccountTransaction entry = multiplePurchase.getTransaction(i);
            if(!inStockBrokerList(result,entry.getBroker(), entry.getStock())) {
                StockAccountStockEntry stock = new StockAccountStockEntry();
                stock.setBroker(entry.getBroker());
                stock.setStock(entry.getStock());
                result.addStock(stock);
            }
        }
        return result;
    }
}
