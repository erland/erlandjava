package erland.webapp.stocks.bl.entity;

import erland.webapp.stocks.bl.entity.StockAccountStockEntry;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionList;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.account.StockAccountStockEntryList;
import erland.webapp.stocks.bl.logic.account.StockAccountStockEntryListInterface;
import erland.webapp.stocks.*;

import java.util.Date;

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


public class SessionStockAccount extends StockAccount {
    private StockAccountTransactionList permanentStocks = new StockAccountTransactionList();
    private StockAccountTransactionList oneTimePurchase = new StockAccountTransactionList();
    private StockAccountTransactionList multiplePurchase = new StockAccountTransactionList();

    public boolean removeStockContinously(String broker, String stock, Date date) {
        clearCache(getAccountId(),broker,stock);
        return multiplePurchase.removeTransaction(broker, stock, date);
    }

    public boolean removeStockSingle(String broker, String stock, Date date) {
        clearCache(getAccountId(),broker,stock);
        return oneTimePurchase.removeTransaction(broker, stock, date);
    }

    public boolean removeStockPermanent(String broker, String stock, Date date) {
        clearCache(getAccountId(),broker,stock);
        return permanentStocks.removeTransaction(broker, stock, date);
    }

    public boolean addStockContinously(String broker, String stock, Date date, double valueOfStocks, String interval) {
        clearCache(getAccountId(),broker,stock);
        return multiplePurchase.addTransaction(broker, stock, date, valueOfStocks, 0);
    }

    public boolean addStockSingle(String broker, String stock, Date date, double numberOfStocks) {
        clearCache(getAccountId(),broker,stock);
        return oneTimePurchase.addTransaction(broker, stock, date, numberOfStocks, -1);
    }

    public boolean addStockSingleForValue(String broker, String stock, Date date, double valueOfStocks) {
        clearCache(getAccountId(),broker,stock);
        return oneTimePurchase.addTransaction(broker,stock,date,0,valueOfStocks);
    }

    public boolean addStockSingleForPrice(String broker, String stock, Date date, double numberOfStocks, double priceOfStocks) {
        clearCache(getAccountId(),broker,stock);
        return oneTimePurchase.addTransaction(broker,stock,date,numberOfStocks,priceOfStocks);
    }

    public boolean addStockPermanent(String broker, String stock, Date date, double number) {
        clearCache(getAccountId(),broker,stock);
        return permanentStocks.addTransaction(broker,stock,date,number,-1);
    }

    public boolean addStockPermanentForPrice(String broker, String stock, Date date, double number,double price) {
        clearCache(getAccountId(),broker,stock);
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
    public StockAccountStockEntry[] getStocks() {
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
        return result.toArray();
    }
}
