package erland.webapp.stocks.bl.logic.transaction;
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

import erland.util.Log;

import java.util.Vector;
import java.util.Date;
import java.util.Iterator;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StockAccountTransactionList implements StockAccountTransactionListInterface {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Vector vector = new Vector();

    public static Date getDateFromString(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return null;
        }
    }

    private int getStockEntryPosition(Vector stocks, String broker, String stock, Date date) {
        boolean bFound = false;
        int pos=0;
        for(pos=0;pos<stocks.size();pos++) {
            StockAccountTransaction entry = (StockAccountTransaction) stocks.elementAt(pos);
            if(broker.equals(entry.getBroker()) &&
                    stock.equals(entry.getStock()) &&
                    date.equals(entry.getDate())) {

                bFound = true;
                break;
            }
        }
        if(bFound) {
            return pos;
        }else {
            return -1;
        }
    }

    public boolean addTransaction(String broker, String stock, Date date, double number, double value) {
        if(date==null) {
            return false;
        }
        int pos = getStockEntryPosition(vector,broker,stock,date);
        if(pos<0) {
            vector.addElement(new StockAccountTransaction(broker,stock,date,number,value));
        }else {
            vector.set(pos,new StockAccountTransaction(broker,stock, date, number, value));
        }
        return true;
    }

    public boolean removeTransaction(String broker, String stock, Date date) {
        if(date==null) {
            return false;
        }
        int pos = getStockEntryPosition(vector,broker,stock,date);
        if(pos>=0) {
            vector.removeElementAt(pos);
        }
        return true;
    }

    public void addTransaction(StockAccountTransaction transaction) {
        vector.addElement(transaction);
    }
    public void addTransactions(StockAccountTransactionListInterface transactionList) {
        Iterator it = transactionList.iterator();
        while(it.hasNext()) {
            vector.addElement(it.next());
        }
    }
    public Iterator iterator() {
        return vector.iterator();
    }

    public StockAccountTransaction[] toArray() {
        return (StockAccountTransaction[]) vector.toArray(new StockAccountTransaction[0]);
    }

    public StockAccountTransaction getTransaction(int i) {
        return (StockAccountTransaction) vector.elementAt(i);
    }

    public int size() {
        return vector.size();
    }
    public StockAccountTransaction getTransaction(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter) {
        for(int i=0;i<vector.size();i++) {
            StockAccountTransaction entry = getTransaction(i);
            if(entry.getBroker().equals(broker) &&
                    entry.getStock().equals(stock) &&
                    entry.getDate().getTime()==date.getTime() &&
                    (filter==null || filter.isMatching(entry))) {

                return entry;
            }
        }
        return null;
    }
    public StockAccountTransactionListInterface getTransactions(String broker, String stock, Date fromDate, Date toDate, StockAccountTransactionFilterInterface filter) {
        StockAccountTransactionList result = new StockAccountTransactionList();
        for(int i=0;i<vector.size();i++) {
            StockAccountTransaction entry = getTransaction(i);
            if(entry.getBroker().equals(broker) &&
                    entry.getStock().equals(stock) &&
                    entry.getDate().getTime()<=toDate.getTime() &&
                    entry.getDate().getTime()>=fromDate.getTime() &&
                    (filter==null || filter.isMatching(entry))) {

                result.addTransaction(entry);
            }
        }
        return result;
    }
    public StockAccountTransaction getTransactionBefore(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter) {
        Log.println(this,"getLastBeforDate "+date+" vector.size()="+vector.size());
        Date currentDate = new Date(0);
        int currentPos = -1;
        for(int i=0;i<vector.size();i++) {
            StockAccountTransaction entry = getTransaction(i);
            Log.println(this,"getLastBeforeDate validating "+entry.getBroker()+" "+entry.getStock()+" "+entry.getDate()+" < "+date);
            if(entry.getBroker().equals(broker) &&
                    entry.getStock().equals(stock) &&
                    entry.getDate().getTime()<=date.getTime()) {

                Log.println(this,"getLastBeforeDate validating "+entry.getDate()+" >= "+currentDate);
                if(entry.getDate().getTime()>=currentDate.getTime() &&
                        (filter==null || filter.isMatching(entry))) {
                    Log.println(this,"getLastBeforeDate found "+entry.getDate());
                    currentDate=entry.getDate();
                    currentPos=i;
                }
            }
        }
        if(currentPos>=0) {
            return getTransaction(currentPos);
        }else {
            return null;
        }
    }
    public StockAccountTransaction getTransactionAfter(String broker, String stock, Date date, StockAccountTransactionFilterInterface filter) {
        Log.println(this,"getNextAfterDate "+date+" vector.size()="+vector.size());
        Date currentDate = null;
        int currentPos = -1;
        for(int i=0;i<vector.size();i++) {
            StockAccountTransaction entry = getTransaction(i);
            Log.println(this,"getNextAfterDate validating "+entry.getBroker()+" "+entry.getStock()+" "+entry.getDate()+" > "+date);
            if(entry.getBroker().equals(broker) &&
                    entry.getStock().equals(stock) &&
                    entry.getDate().getTime()>date.getTime()) {

                Log.println(this,"getNextAfterDate validating "+entry.getDate()+" < "+currentDate);
                if(currentDate==null || entry.getDate().getTime()<currentDate.getTime()) {
                    if((filter==null || filter.isMatching(entry))) {
                        Log.println(this,"getNextAfterDate found "+entry.getDate());
                        currentDate=entry.getDate();
                        currentPos=i;
                    }
                }
            }
        }
        if(currentPos>=0) {
            return getTransaction(currentPos);
        }else {
            return null;
        }
    }
}
