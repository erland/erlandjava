package erland.webapp.stocks.bl.entity;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.stocks.bl.entity.Account;
import erland.webapp.stocks.bl.entity.Transaction;
import erland.webapp.stocks.bl.entity.StockAccountStockEntry;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionList;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.account.StockAccountStockEntryList;
import erland.webapp.stocks.bl.logic.account.StockAccountStockEntryListInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseStockAccount extends StockAccount {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected Integer getAccountId() {
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username",getUsername());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-account").search(filter);
        if(entities.length>0) {
            return ((Account)entities[0]).getAccountId();
        }else {
            return null;
        }
    }

    private boolean updateEntry(int type, String broker, String stock, Date date, double value) {
        return updateEntry(type,broker,stock,date,value,0);
    }
    private boolean updateEntry(int type, String broker, String stock, Date date, double value, double price) {
        Integer accountId = getAccountId();
        if(accountId!=null) {
            Transaction transaction = (Transaction) getEnvironment().getEntityFactory().create("stock-transaction");
            transaction.setAccountId(accountId);
            transaction.setBrokerId(broker);
            transaction.setDate(date);
            transaction.setType(new Integer(type));
            transaction.setStockId(stock);
            transaction.setPrice(new Double(price));
            transaction.setValue(new Double(value));
            getEnvironment().getEntityStorageFactory().getStorage("stock-transaction").store(transaction);
            return true;
        }
        return false;
    }

    public boolean addStockContinously(String broker, String stock, Date date, double valueOfStocks, String interval) {
        return updateEntry(4,broker,stock,date,valueOfStocks);
    }

    public boolean addStockSingle(String broker, String stock, Date date, double numberOfStocks) {
        return updateEntry(2,broker,stock,date,numberOfStocks);
    }

    public boolean addStockSingleForValue(String broker, String stock, Date date, double valueOfStocks) {
        return updateEntry(3,broker,stock,date,valueOfStocks);
    }

    public boolean addStockSingleForPrice(String broker, String stock, Date date, double numberOfStocks, double priceOfStocks) {
        return updateEntry(5,broker,stock,date,numberOfStocks, priceOfStocks);
    }

    public boolean addStockPermanent(String broker, String stock, Date date, double number) {
        return updateEntry(1,broker,stock,date,number);
    }

    public boolean addStockPermanentForPrice(String broker, String stock, Date date, double number,double price) {
        return updateEntry(6,broker,stock,date,number,price);
    }

    private StockAccountTransactionList getEntries(String query) {
        Integer accountId = getAccountId();
        StockAccountTransactionList result = new StockAccountTransactionList();
        if(accountId!=null) {
            QueryFilter filter = new QueryFilter(query);
            filter.setAttribute("accountid",accountId);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-transaction").search(filter);
            for (int i = 0; i < entities.length; i++) {
                Transaction transaction = (Transaction) entities[i];
                result.addTransaction(transaction.getBrokerId(),transaction.getStockId(),transaction.getDate(),transaction.getPrice().doubleValue(),transaction.getValue().doubleValue());
            }
        }
        return result;
    }
    private boolean removeEntry(int type,String broker, String stock, Date date) {
        Integer accountId = getAccountId();
        if(accountId!=null) {
            Transaction template = (Transaction) getEnvironment().getEntityFactory().create("stock-transaction");
            template.setAccountId(accountId);
            template.setType(new Integer(type));
            template.setBrokerId(broker);
            template.setStockId(stock);
            template.setDate(date);
            getEnvironment().getEntityStorageFactory().getStorage("stock-transaction").delete(template);
            return true;
        }
        return false;
    }

    public boolean removeStockContinously(String broker, String stock, Date date) {
        return removeEntry(4,broker,stock,date);
    }

    public boolean removeStockSingle(String broker, String stock, Date date) {
        return removeEntry(2,broker,stock,date) && removeEntry(3,broker,stock,date) && removeEntry(5,broker,stock,date);
    }

    public boolean removeStockPermanent(String broker, String stock, Date date) {
        return removeEntry(1,broker,stock,date) && removeEntry(6,broker,stock,date);
    }

    public StockAccountTransactionListInterface getPermanentEntries() {
        //StockAccountTransactionList first = getEntries("select brokerid,stockid,date,value,-1 from transactions where accountid=? and type=? order by brokerid,stockid,date",1);
        //StockAccountTransactionList second = getEntries("select brokerid,stockid,date,value,price from transactions where accountid=? and type=? order by brokerid,stockid,date",6);
        //StockAccountTransactionList result = new StockAccountTransactionList();
        //result.addTransactions(first);
        //result.addTransactions(second);
        return getEntries("permanententriesforaccountid");
    }

    public StockAccountTransactionListInterface getPurchaseOnceEntries() {
        //StockAccountTransactionList first = getEntries("select brokerid,stockid,date,value,-1 from transactions where accountid=? and type=? order by brokerid,stockid,date",2);
        //StockAccountTransactionList second = getEntries("select brokerid,stockid,date,0,value from transactions where accountid=? and type=? order by brokerid,stockid,date",3);
        //StockAccountTransactionList third = getEntries("select brokerid,stockid,date,value,price from transactions where accountid=? and type=? order by brokerid,stockid,date",5);
        //StockAccountTransactionList result = new StockAccountTransactionList();
        //result.addTransactions(first);
        //result.addTransactions(second);
        //result.addTransactions(third);
        return getEntries("purchaseonceentriesforaccountid");
    }

    public StockAccountTransactionListInterface getPurchaseContinouslyEntries() {
        //return getEntries("select brokerid,stockid,date,0,value from transactions where accountid=? and type=? order by brokerid,stockid,date",4);
        return getEntries("purchasecontinouslyentriesforaccountid");
    }

    public StockAccountStockEntry[] getStocks() {
        Integer accountId = getAccountId();
        StockAccountStockEntryList result = new StockAccountStockEntryList();
        if(accountId!=null) {
            QueryFilter filter = new QueryFilter("uniquestocksforaccountid");
            filter.setAttribute("accountid",accountId);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-stockaccountstockentry").search(filter);
            for (int i = 0; i < entities.length; i++) {
                StockAccountStockEntry entry = (StockAccountStockEntry) entities[i];
                result.addStock(entry);

            }
        }
        return result.toArray();
    }
}
