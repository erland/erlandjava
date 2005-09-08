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

import erland.webapp.diagram.*;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.bl.entity.StockAccountStockEntry;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionFilterInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionList;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.bl.service.StockStorageInterface;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class StockAccount implements EntityInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(StockAccount.class);
    private StockStorageInterface stockStorage;
    private String username;
    private WebAppEnvironmentInterface environment;
    private BrokerManagerInterface brokerManager;

    static class SerieType {
        private SerieType() {}
        public static final SerieType PURCHASE_VALUES = new SerieType();
        public static final SerieType PURCHASEDIFF_VALUES = new SerieType();
        public static final SerieType STOCK_VALUES = new SerieType();
        public static final SerieType STOCKNUMBER_VALUES = new SerieType();
        public static final SerieType STOCKNUMBERDIFF_VALUES = new SerieType();
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
        this.stockStorage = ((StockStorageInterface)environment.getServiceFactory().create("stock-stockstorage"));
    }
    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
    protected String getUsername() {
        return username;
    }
    abstract public StockAccountTransactionListInterface getPermanentEntries();

    abstract public StockAccountTransactionListInterface getPurchaseOnceEntries();

    abstract public StockAccountTransactionListInterface getPurchaseContinouslyEntries();

    abstract public boolean addStockContinously(String broker, String stock, Date date, double valueOfStocks, String interval);

    abstract public boolean addStockSingle(String broker, String stock, Date date, double numberOfStocks);

    abstract public boolean addStockSingleForValue(String broker, String stock, Date date, double valueOfStocks);

    abstract public boolean addStockSingleForPrice(String broker, String stock, Date date, double numberOfStocks, double priceOfStocks);

    abstract public boolean addStockPermanent(String broker, String stock, Date date, double number);

    abstract public boolean addStockPermanentForPrice(String broker, String stock, Date date, double number, double price);

    abstract public boolean removeStockContinously(String broker, String stock, Date date);

    abstract public boolean removeStockSingle(String broker, String stock, Date date);

    abstract public boolean removeStockPermanent(String broker, String stock, Date date);

    abstract public StockAccountStockEntry[] getStocks();

    public double getStockNumber(String broker, String stock, String date) {
        StockInterface s = stockStorage.getStock(broker,stock);
        return getStockValue(s.getRates(),broker,stock,StockAccountTransactionList.getDateFromString(date),SerieType.STOCKNUMBER_VALUES);
    }

    private double getStockContinously(DateValueSerieInterface values, StockAccountTransaction entry, Date fromDate, Date toDate, SerieType type, boolean includeFromDate, boolean includeToDate) {
        LOG.debug("getStockContinously start "+entry.getValue()+" "+fromDate+" "+toDate);
        Date date;
        if(includeFromDate) {
            date = fromDate;
        }else {
            date = getNextMultiple(entry,fromDate);
        }
        double number=0;
        int oldPos=0;
        while(date.getTime()<=toDate.getTime()) {
            if(includeToDate || date.getTime()<toDate.getTime()) {
                int pos = values.indexOf(date,oldPos);
                if(pos>=0) {
                    oldPos=pos;
                    DateValue dv = (DateValue) values.getDateValue(pos);
                    LOG.debug("getStockContinously add "+entry.getValue()+" "+dv.getDate());
                    if(type==SerieType.PURCHASE_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                        number += entry.getValue();
                    }else {
                        number += entry.getValue()/dv.getValue();
                    }
                }
            }
            date = getNextMultiple(entry,date);
        }
        LOG.debug("getStockContinously end number="+number+" "+entry.getValue()+" "+fromDate+" "+toDate);
        return number;
    }

    /**
     * Calculate the change of stock value for a specified type of result and transation
     * Observe that the value returned indicates how much the calculation should be changed due to the specified transation,
     * it does not return a calculated value in which previously calcation has been taken into account
     * @param values The stock value serie to get values from
     * @param entry The transaction to use in the calculation
     * @param pos The position in the stock value serie
     * @param type The type of result/stock value to calculate
     * @param isPermanent Indicates if this is a permanent transaction
     * @return The calculated stock value, -1 for a permanent transaction means no calculation has been made
     */
    private double getValue(DateValueSerieInterface values, StockAccountTransaction entry, int pos, SerieType type, boolean isPermanent) {
        // If no position was specified
        if(pos<0) {
            // Find the position which is valid at the transaction date
            pos = values.indexOf(entry.getDate());
            // If the transaction date was before the first entry in stock value series AND
            //    the stock value serie is not empty
            if(pos<0 && values.size()>0) {
                // Use the first position in the stock value serie
                // This may result in incorrect result, but since no real value exists this is still better than using 0 as value
                pos=0;
            }
        }
        // If a position to use in the stock value serie has been found or specified
        if(pos>=0) {
            DateValue dv = (DateValue) values.getDateValue(pos);

            // If the transaction entry contains a change of number of stocks
            if(entry.getNumber()!=0) {
                // If we are doing a calculation in which the number of stocks are interesting
                if(type==SerieType.STOCK_VALUES||type==SerieType.STOCKNUMBER_VALUES||type==SerieType.STOCKNUMBERDIFF_VALUES) {
                    return entry.getNumber();
                // If we are doing a calculation in which the purchase value is interesting
                }else if(type==SerieType.PURCHASE_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                    // If the transation entry contains a possitive purchase value
                    if(entry.getValue()>=0) {
                        // Calculate the real purchase value by multiplying it with the number of stocks
                        return entry.getNumber()*entry.getValue();
                    }else {
                        // If this is a permanent transaction
                        if(isPermanent) {
                            // Return -1 to indicate that we have not done any calculation
                            return -1;
                        }else {
                            // Calculate the real purchase value by multiplying it with the number of stocks
                            return entry.getNumber()*dv.getValue();
                        }
                    }
                }
            }else {
                // If we are doing a calculation in which the number of stocks are interesting
                if(type==SerieType.STOCK_VALUES||type==SerieType.STOCKNUMBER_VALUES||type==SerieType.STOCKNUMBERDIFF_VALUES) {
                    // Calculate number of stocks by dividing the transaction value with the current stock value in the stock value serie
                    return entry.getValue()/dv.getValue();
                // If we are doing a calculation in which the purchase value is interesting
                }else if(type==SerieType.PURCHASE_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                    // The purchase value is directly in the transaction, so just return it, no calculation is required in this situation
                    return entry.getValue();
                }
            }
        }
        // Else this transaction should not affect the calculation of the specified type of result
        return 0;
    }

    /**
     * Get next date where the specified continously transaction should affect the calculation
     * @param transaction The continously transaction to check
     * @param startDate The start date, the next date after this date will be found
     * @return The date where the transaction affects the calculation next time
     */
    private Date getNextMultiple(StockAccountTransaction transaction, Date startDate) {
        final Calendar calMultiple = Calendar.getInstance();
        final Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(startDate);
        calMultiple.setTime(transaction.getDate());
        LOG.debug("getNextMultiple in = "+calMultiple.getTime()+" "+startDate);
        int yearMultiple=calMultiple.get(Calendar.YEAR);
        int monthMultiple=calMultiple.get(Calendar.MONTH);
        int dayMultiple=calMultiple.get(Calendar.DAY_OF_MONTH);
        calMultiple.set(Calendar.DAY_OF_MONTH,1);
        if(yearMultiple<calCurrent.get(Calendar.YEAR)) {
            yearMultiple = calCurrent.get(Calendar.YEAR);
            monthMultiple = 0;
            calMultiple.set(Calendar.YEAR,yearMultiple);
            LOG.debug("getNextMultiple1 = "+calMultiple.getTime());
            //if(calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)<calMultiple.get(Calendar.DAY_OF_MONTH)) {
            //    calMultiple.set(Calendar.DAY_OF_MONTH,calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH));
            //}
            LOG.debug("getNextMultiple2 = "+calMultiple.getTime());
            calMultiple.set(Calendar.MONTH,monthMultiple);
            LOG.debug("getNextMultiple3 = "+calMultiple.getTime());
        }
        if(monthMultiple<calCurrent.get(Calendar.MONTH)) {
            monthMultiple = calCurrent.get(Calendar.MONTH);
            //if(calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)<calMultiple.get(Calendar.DAY_OF_MONTH)) {
            //    calMultiple.set(Calendar.DAY_OF_MONTH,calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
            //}
            LOG.debug("getNextMultiple4 = "+calMultiple.getTime());
            calMultiple.set(Calendar.MONTH,monthMultiple);
            LOG.debug("getNextMultiple5 = "+calMultiple.getTime());
        }
        if(dayMultiple<=calCurrent.get(Calendar.DAY_OF_MONTH)||calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)==calCurrent.get(Calendar.DAY_OF_MONTH)) {
            monthMultiple++;
            if(monthMultiple>11) {
                yearMultiple++;
                monthMultiple-=12;
            }
            //calMultiple.set(Calendar.DAY_OF_MONTH,1);
            LOG.debug("getNextMultiple6 = "+calMultiple.getTime());
            calMultiple.set(Calendar.MONTH,monthMultiple);
            LOG.debug("getNextMultiple7 = "+calMultiple.getTime());
            calMultiple.set(Calendar.YEAR,yearMultiple);
            LOG.debug("getNextMultiple8 = "+calMultiple.getTime());
        }
        if(calMultiple.getActualMaximum(Calendar.DAY_OF_MONTH)<dayMultiple) {
            calMultiple.set(Calendar.DAY_OF_MONTH,calMultiple.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
            LOG.debug("getNextMultiple9 = "+calMultiple.getTime());
        }else {
            calMultiple.set(Calendar.DAY_OF_MONTH,dayMultiple);
            LOG.debug("getNextMultiple0 = "+calMultiple.getTime());
        }
        LOG.debug("getNextMultiple = "+calMultiple.getTime());
        return calMultiple.getTime();
    }

    /**
     * Get value of the stock at the specified date
     * @param values The stock values to look in
     * @param broker The id of the broker
     * @param stock The id of the stock
     * @param dateTo The date to get the value for
     * @param type The type of value to get
     * @return The value of the stock at the specified date
     */
    private double getStockValue(DateValueSerieInterface values, String broker, String stock, Date dateTo, SerieType type) {
        LOG.debug("getStockValue start "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
        double currentValue=0;

        // If  no date has been specified, just return 0
        if(dateTo==null) {
            LOG.debug("getStockValue end currentValue="+currentValue+" "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
            return currentValue;
        }

        // Get all transactions which shall be used in the calculations
        StockAccountTransactionListInterface permanentStocks = getPermanentEntries();
        StockAccountTransactionListInterface oneTimePurchase = getPurchaseOnceEntries();
        StockAccountTransactionListInterface continouslyPurchase = getPurchaseContinouslyEntries();
        StockAccountTransaction permanent = permanentStocks.getTransactionBefore(broker,stock,dateTo,
                new StockAccountTransactionFilterInterface() {
                    public boolean isMatching(StockAccountTransaction transaction) {
                        return transaction.getValue()>=0;
                    }
        });


        //
        // PHASE 1
        // Set start date and initial value of calculation based on any existing permanent value transactions
        //

        Date startDate;

        // If a permanent value transaction exists
        if(permanent!=null) {
            // Set current value to the value at the permanent transaction
            currentValue = getValue(values,permanent,-1,type,true);
            // Set start date of the calculation to the start date of the permanent transaction, there is no idea to
            // perform calculation before the permanent transaction since the permanent transaction still will reset the
            // calculation result when its reached.
            startDate = permanent.getDate();
            LOG.debug("getStockValue permanent "+startDate);
        }else {
            // Set start date to 1970
            startDate = new Date(0);
        }

        // Get position in stock value series for toDate
        int startPos = values.indexOf(dateTo);
        // If no position in the stock value series exists before the toDate, just return the current value
        if(startPos<0) {
            LOG.debug("getStockValue end currentValue="+currentValue+" "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
            return currentValue;
        }

        // Get the last permanent value transactions before toDate, transactions after toDate does not affect the result
        // and permanent transactions before the "last one before toDate" will not affect anything since there calculation
        // will be reset when the last one is reached
        StockAccountTransaction permanentWithNumberOnly = permanentStocks.getTransactionBefore(broker,stock,dateTo,null);
        // If a permanent value transaction before toDate exists and is a permanent transation of the type where only
        // number of stocks has been specified
        if(permanentWithNumberOnly!=null && permanentWithNumberOnly.getValue()<0) {
            // Get stock value for the permanent transaction
            double val = getValue(values,permanentWithNumberOnly,-1,type,true);
            // If stock value exists and date of permanent value transaction is after start date
            if(val>=0 && permanentWithNumberOnly.getDate().getTime()>startDate.getTime()) {
                // Set current value to the calculated value of the permanent value transaction
                currentValue = val;
                // Set start date to the date of the permanent value transaction
                startDate = permanentWithNumberOnly.getDate();
                LOG.debug("getStockValue permOnly "+startDate);
            }
        }

        //
        // PHASE 2
        // Calculate all purchase once transactions
        //

        // Get and iterate through all purchace once transactions before startDate and for each iterations perform
        // calculation of current value
        StockAccountTransactionListInterface beforeFirstDate = oneTimePurchase.getTransactions(broker,stock,startDate,dateTo,null);
        for(int i=0;i<beforeFirstDate.size();i++) {
            StockAccountTransaction entry = beforeFirstDate.getTransaction(i);
            currentValue += getValue(values,entry,-1,type,false);
            LOG.debug("getStockValue once "+entry.getDate());
        }

        //
        // PHASE 3
        // Calculate the last continously transaction before the calculation interval. The calculation will of course
        // only be done inside the calculation interval, but the transaction may start before the interval.
        //
        StockAccountTransaction beforeStartDate = continouslyPurchase.getTransactionBefore(broker,stock,startDate,null);
        StockAccountTransaction afterStartDate = continouslyPurchase.getTransactionAfter(broker,stock,startDate,null);
        // If a continously transaction exists before start date
        if(beforeStartDate!=null) {
            Date nextDate=dateTo;
            boolean includeNextDate = true;
            // If continously transaction exists after start date its date is before or equal to toDate
            if(afterStartDate!=null && afterStartDate.getDate().getTime()<=nextDate.getTime()) {
                // Do not include the date of the continously transaction after start date in the calculation
                includeNextDate = false;
                // Set the end date of the continously transaction calculation to the date of the continously
                // transaction after start date
                nextDate=afterStartDate.getDate();
            }

            // Perform calculation of all times the continously transaction before start date until the next continously
            // transaction or until toDate of no more continously transaction exists
            currentValue += getStockContinously(values,beforeStartDate,startDate,nextDate,type,false,includeNextDate);
        }

        //
        // PHASE 4
        // Calculate all continously transactions inside the calculation interval
        //

        // Iterate through all contiously transactions between start date and toDate
        beforeFirstDate = continouslyPurchase.getTransactions(broker, stock, startDate, dateTo,null);
        for(int i=0;i<beforeFirstDate.size();i++) {
            StockAccountTransaction entry = beforeFirstDate.getTransaction(i);
            Date nextDate;
            boolean includeNextDate = true;
            // If this is not the last continoulsy transaction before toDate
            if(i<beforeFirstDate.size()-1) {
                StockAccountTransaction next = beforeFirstDate.getTransaction(i+1);
                // Set the end of the continously transaction to the date of the next continously transaction
                nextDate = next.getDate();
                // Do not include the date of the next continously transaction in the calculation
                includeNextDate = false;
            }else {
                // Set the end date of the continously transaction to toDate
                nextDate = dateTo;
            }

            // Perform calculation of all times the continously transaction affects the result between the date of
            // the continously transaction until the end date (initialized in the previous statements)
            LOG.debug("getStockValue multiple "+entry.getDate());
            currentValue += getStockContinously(values,entry,entry.getDate(),nextDate,type,true,includeNextDate);
        }

        LOG.debug("getStockValue end currentValue="+currentValue+" "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
        return currentValue;
    }

    /**
     * Get a calculated stock value serie for the specified type of result
     * @param values The stock value serie to base calculation on
     * @param broker The id of the broker
     * @param stock The id of the stock
     * @param fromDate The start date of the requested result serie
     * @param toDate The end date fo the requested result serie
     * @param type The type of result to calculate
     * @return The calculated stock value serie
     */
    private DateValueSerie getStockValues(DateValueSerieInterface values, String broker, String stock, Date fromDate, Date toDate,SerieType type) {
        LOG.debug("getStockValues start "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
        Date dateFrom = fromDate;
        Date dateTo = toDate;
        DateValueSerie result = new DateValueSerie(values.getName());
        //TODO: Why can't we have some default values instead of requesting that both from and to is specified ?
        if(dateFrom==null||dateTo==null) {
            LOG.debug("getStockValues end result.size()="+result.size()+" "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
            return result;
        }
        int finalEndPos = values.indexOf(dateTo);
        int curPos=values.indexOf(dateFrom);

        //If fromDate is before all available stock values AND
        //   toDate is equal to or after the first available stock value
        if(curPos<0 && finalEndPos>=0) {
            // Set fromDate to the first available stock value
            curPos=0;
            dateFrom = new Date(values.getDateValue(0).getDate().getTime());
        }

        // Get value of the stock at fromDate
        double currentValue = getStockValue(values,broker,stock,dateFrom,type);
        double oldValue = 0;

        // Get all transaction entries which we need to use when calculating
        StockAccountTransaction currentMultiple = getPurchaseContinouslyEntries().getTransactionBefore(broker,stock,dateFrom,null);
        StockAccountTransactionListInterface purchaseMultiple = getPurchaseContinouslyEntries().getTransactions(broker,stock,dateFrom,dateTo,null);
        StockAccountTransactionListInterface purchaseOnce = getPurchaseOnceEntries().getTransactions(broker, stock, dateFrom, dateTo,null);
        StockAccountTransactionListInterface permanentStocks = getPermanentEntries().getTransactions(broker,stock,dateFrom, dateTo,null);

        // If no stock value exists within the fromDate<->toDate interval OR
        //    the fromDate and toDate points to the same stock value entry
        if((curPos <0 && finalEndPos<0) || curPos==finalEndPos) {
            LOG.debug("getStockValues end result.size()="+result.size()+" "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
            // Return an emtpy result
            return result;
        }

        boolean bLast = curPos==finalEndPos;

        // Iterate through all stock values between fromDate and toDate, each iterations is divided into tree phases:
        // PHASE 1: Find the next transaction that affects the result
        // PHASE 2: Perform calculation for all intermediate entries until the next transation
        // PAHSE 3: Perform calculation for the next transation
        while(curPos<=finalEndPos) {
            // Get stock value for current position
            DateValue dv = (DateValue) values.getDateValue(curPos);

            //
            // PHASE 1:
            // Find the next transaction that will affect the calculation and get the time when it will affect
            // the calculation next time. This is important because each iteration shall calculate all entries
            // until a transation affects the calculation.
            // Also observe that several transations may exist at same time and then they will all affect the
            // calculation.
            //

            // Get next ordinary transaction
            StockAccountTransaction next = purchaseOnce.getTransactionAfter(broker,stock,dv.getDate(),null);
            int endPos = -1;
            if(next!=null) {
                LOG.debug("found oncePurchase "+next.getDate());
                // Set last stockvalue to use in this iteration to the one used at the time of the purchace once transaction
                endPos = values.indexOf(next.getDate());
            }else {
                // Set last stockvalue to use in this iteration to the one used at the time of toDate
                endPos = finalEndPos;
            }

            // Get next permanent value transation
            StockAccountTransaction nextPermanent = permanentStocks.getTransactionAfter(broker,stock,dv.getDate(),null);
            if(nextPermanent!=null) {
                LOG.debug("found permanent "+nextPermanent.getDate());
                int pos = values.indexOf(nextPermanent.getDate());
                // If permanent value transation is before the last stockvalue to use in this iteration
                if(pos<endPos) {
                    // Ignore any previously found purchace once transaction in this iteration
                    next=null;
                    // Set last stockvalue to use in this iteration to the one used at the time of the permanent value transation
                    endPos=pos;
                // If permanent value transation is after the last stockvalue to use in this iteration
                }else if(pos>endPos) {
                    // Ignore the permanent value transation in this iteration
                    nextPermanent=null;
                }
                // If permenent value transation is equal to the last stockvalue to use in this iteration
                // we don't need to move the last stockvalue to use, but we still need to keep the permanent
                // transaction since it shall be calculated in this iteration
            }

            // Get next continously transation
            StockAccountTransaction nextMultiple = purchaseMultiple.getTransactionAfter(broker, stock, dv.getDate(),null);
            if(nextMultiple!=null) {
                LOG.debug("found multiple "+nextMultiple.getDate());
                int pos = values.indexOf(nextMultiple.getDate());
                // If continously transaction is after toDate
                if(nextMultiple.getDate().getTime()>dateTo.getTime()) {
                    // Ignore this continously transation
                    nextMultiple=null;
                }
                // If continously transation is before the last stockvalue to use in this iteration
                if(pos<endPos) {
                    // Ignore any previously found purchace once/permanent value transactions
                    next=null;
                    nextPermanent=null;
                    // Set last stockvalue to use in this iteration to the one used at the time of this continously transation
                    endPos = pos;
                // If continously transation is after the last stockvalue to use in this iteration
                }else if(pos>endPos) {
                    // Ignore the continously transation in this iteration
                    nextMultiple = null;
                }
                // If continsously transation is equal to the last stockvalue to use in this iteration
                // we don't need to move the last stockvalue to use, but we still need to keep the continously
                // transaction since it shall be calculated in this iteration
            }

            boolean bAddCurrentMultiple = false;
            // If a continously transaction was found before this iteration we need to
            // check it since we may be required to use it in the calculations in this iteration
            if(currentMultiple!=null) {
                LOG.debug("found current "+currentMultiple.getDate());
                bAddCurrentMultiple=true;
                //LOG.debug("find next multiple: "+dv.getStartDate()+" "+yearMultiple+" "+monthMultiple);

                // Get the next time this continously transation shall be calculated
                Date dateMultiple = getNextMultiple(currentMultiple,dv.getDate());
                int pos = values.indexOf(dateMultiple);
                //LOG.debug("find next multiple: "+calMultiple.getTime()+" "+pos+" "+endPos);

                // If this continously transation shall be calculated next time after toDate
                if(dateMultiple.getTime()>dateTo.getTime()) {
                    // Ignore this continously transation
                    currentMultiple=null;
                }
                // If this continously transation shall be calculated next time before the last stockvalue to use in this iteration
                if(pos<endPos) {
                    // Ignore any previously found purchace once/permanent value/continously transations
                    next = null;
                    nextPermanent = null;
                    nextMultiple = null;
                    // Set last stockvalue to use in this iteration to the one used at this continously transation
                    endPos = pos;
                    //LOG.debug("find next multiple: found");
                // If this continously transation shall be calculated next time after the last stockvalue to use in this iteration
                }else if(pos>endPos) {
                    // Ignore this continously transation in this iteration, we still cant ignore it because we still
                    // might need to use it in the next iteration
                    bAddCurrentMultiple=false;
                    //LOG.debug("find next multiple: no more");
                }
                // If the continously transation is equal to the last stockvalue to use in this iteration
                // we don't need to move the last stockvalue to use, but we still need to keep the continously
                // transaction since it shall be calculated in this iteration
            }

            //
            // PHASE 2:
            // Iterate through all stock values that shall be part of this iteration and perform calculation for
            // each entry and add them to the result. The calculation in this phase is based on the transactions
            // found in the previous iteration
            //
            for(;curPos<endPos||bLast;curPos++) {
                bLast = false;
                DateValue dateValue = (DateValue) values.getDateValue(curPos);

                if(type==SerieType.PURCHASE_VALUES||type==SerieType.STOCKNUMBER_VALUES) {
                    result.addDateValue(new DateValue(dateValue.getDate(),currentValue));
                }else if(type==SerieType.STOCK_VALUES) {
                    double value = currentValue*dateValue.getValue();
                    result.addDateValue(new DateValue(dateValue.getDate(),value));
                }else if(type==SerieType.STOCKNUMBERDIFF_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                    double value = currentValue-oldValue;
                    if(value!=0) {
                        result.addDateValue(new DateValue(dateValue.getDate(),value));
                    }
                    oldValue=currentValue;
                }
            }

            //
            // PHASE 3:
            // Perform calculations for the transactions that has been found in PHASE 1 and should affect
            // calculation in this iteration. For each of these transactions peform a single calculation before
            // we step to the next iteration
            //
            if(nextPermanent!=null) {
                double val =getValue(values,nextPermanent,endPos,type,true);
                LOG.debug("adding permanent "+val+" "+values.getDateValue(endPos).getDate());
                // If a stockvalue is found for a permanent value transaction and it should affect the calculation
                // in this iteration and for this time of result
                if(val>=0) {
                    // Set the result to the value of the permanent value transation
                    currentValue = val;
                }
            }
            if(next!=null) {
                LOG.debug("adding once "+getValue(values,next,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                // Add the stockvalue for a purchace once transaction if it should affect the calculation in this iteration
                // and this type of result
                currentValue+=getValue(values,next,endPos,type,false);
            }
            //LOG.debug("AddMultiple = "+bAddMultiple);
            if(currentMultiple!=null && nextMultiple==null) {
                // Add the stockvalue for a continously transaction found in a previous iteration if it should affect
                // the calculation in this iteration and this type of result
                if(bAddCurrentMultiple) {
                    LOG.debug("adding current multiple "+getValue(values,currentMultiple,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                    currentValue+=getValue(values,currentMultiple,endPos,type,false);
                }
            }
            if(nextMultiple!=null) {
                LOG.debug("adding next multiple "+getValue(values,nextMultiple,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                // Add the stockvalue for a continously transation found in this iteration if it should affect the
                // calculation in this iteration and this type of result
                currentValue += getValue(values,nextMultiple,endPos,type,false);
                // Set this continously transaction to the currently active one.
                // This means that this transaction might also be used in the next iterations
                currentMultiple = nextMultiple;
            }

            // Check if this is the last iteration
            bLast = (curPos==finalEndPos);
        }
        LOG.debug("getStockValues end result.size="+result.size()+" "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
        return result;
    }

    public StockAccountValue getStockValue(String broker, String stock, Date date) {
        LOG.debug("getStockValue "+broker+" "+stock+" "+date);
        StockInterface s = stockStorage.getStock(broker,stock);
        double noOfStocks = getStockValue(s.getRates(),broker,stock,date,SerieType.STOCK_VALUES);
        int i = s.getRates().indexOf(date);
        if(i>=0) {
            StockAccountValue result = new StockAccountValue();
            result.setValue(noOfStocks*s.getRates().getDateValue(i).getValue());
            result.setRate(s.getRates().getDateValue(i).getValue());
            result.setNoOfStocks(noOfStocks);
            return result;
        }else {
            return null;
        }
    }

    public double getPurchaseValue(String broker, String stock, Date date) {
        LOG.debug("getPurchaseValue "+broker+" "+stock+" "+date);
        StockInterface s = stockStorage.getStock(broker,stock);
        return getStockValue(s.getRates(),broker,stock,date,SerieType.PURCHASE_VALUES);
    }

    public DateValueSerieInterface getStockNumbers(String broker, String stock, Date fromDate, Date toDate, String serieName) {
        return getStockNumbers(broker,stock,fromDate,toDate,serieName,SerieType.STOCKNUMBER_VALUES);
    }
    public DateValueSerieInterface getStockNumberDiffs(String broker, String stock, Date fromDate, Date toDate, String serieName) {
        return getStockNumbers(broker,stock,fromDate,toDate,serieName,SerieType.STOCKNUMBERDIFF_VALUES);
    }

    private DateValueSerieInterface getStockNumbers(String broker, String stock, Date fromDate, Date toDate, String serieName, SerieType type) {
        LOG.debug("getStockNumbers "+broker+" "+stock+" "+fromDate+" "+toDate);
        StockInterface s = stockStorage.getStock(broker,stock);
        DateValueSerieInterface values = s.getRates();
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate, type);
        dateValues.setName(getBrokerManager().getStockName(broker,stock)+" "+serieName);
        if(LOG.isTraceEnabled()) {
            LOG.trace("Values for: "+dateValues.getName());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Iterator it=dateValues.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                LOG.trace(format.format(dv.getDate())+" "+dv.getValue());
            }
        }
        return dateValues;
    }

    public DateValueSerieInterface getStockValues(String broker, String stock, Date fromDate, Date toDate) {
        LOG.debug("getStockValues "+broker+" "+stock+" "+fromDate+" "+toDate);
        StockInterface s = stockStorage.getStock(broker,stock);
        DateValueSerieInterface values = s.getRates();
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate, SerieType.STOCK_VALUES);
        dateValues.setName(getBrokerManager().getStockName(broker,stock));
        if(LOG.isTraceEnabled()) {
            LOG.trace("Values for: "+dateValues.getName());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Iterator it=dateValues.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                LOG.trace(format.format(dv.getDate())+" "+dv.getValue());
            }
        }
        return dateValues;
    }

    private DateValueSerieInterface getPurchaseValues(String broker, String stock, Date fromDate, Date toDate, String purchaseName, SerieType type) {
        LOG.debug("getStockValues "+broker+" "+stock+" "+fromDate+" "+toDate);
        StockInterface s = stockStorage.getStock(broker,stock);
        DateValueSerieInterface values = s.getRates();
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate, type);
        dateValues.setName(getBrokerManager().getStockName(broker,stock)+" "+purchaseName);
        if(LOG.isTraceEnabled()) {
            LOG.trace("Values for: "+dateValues.getName());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Iterator it=dateValues.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                LOG.trace(format.format(dv.getDate())+" "+dv.getValue());
            }
        }
        return dateValues;
    }

    public DateValueSerieInterface getPurchaseValues(String broker, String stock, Date fromDate, Date toDate, String purchaseName) {
        return getPurchaseValues(broker, stock, fromDate, toDate, purchaseName, SerieType.PURCHASE_VALUES);
    }

    public DateValueSerieInterface getPurchaseDiffValues(String broker, String stock, Date fromDate, Date toDate, String purchaseName) {
        return getPurchaseValues(broker, stock, fromDate, toDate, purchaseName, SerieType.PURCHASEDIFF_VALUES);
    }

    public DateValueSerieInterface getTotalStockValues(String broker, Date fromDate, Date toDate) {
        LOG.debug("getStockValues "+fromDate+" "+toDate);
        Vector subresult = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                subresult.addElement(getStockValues(entry.getBroker(),entry.getStock(),fromDate,toDate));
            }
        }
        DateValueSerie result = getTotalStockValues(subresult, "Total");
        if(LOG.isTraceEnabled()) {
            LOG.trace("Values for: "+result.getName());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Iterator it=result.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                LOG.trace(format.format(dv.getDate())+" "+dv.getValue());
            }
        }
        return getDateLimitedSerie(fromDate,toDate,result);
    }

    private DateValueSerieInterface getDateLimitedSerie(Date fromDate, Date toDate, DateValueSerie serie) {
        LOG.debug("getDateLimitedSerie "+serie.getName()+" "+fromDate+" "+toDate);
        int pos = serie.indexOf(fromDate);
        if(pos>=0) {
            DateValueInterface dvBefore = serie.getDateValue(pos);
            if(!dvBefore.getDate().equals(fromDate)) {
                double interpolatedValue = dvBefore.getValue();
                if(serie.size()>pos+1) {
                    DateValueInterface dv = serie.getDateValue(pos+1);
                    interpolatedValue = dvBefore.getValue()+((dv.getValue()-dvBefore.getValue())*(((double)(fromDate.getTime()-dvBefore.getDate().getTime()))/(dv.getDate().getTime()-dvBefore.getDate().getTime())));
                }
                serie.insertDateValue(new DateValue(fromDate,interpolatedValue),pos+1);
                LOG.debug("Inserted interpolated date: "+fromDate+" value:"+interpolatedValue);
            }
            serie.deleteBefore(fromDate);
        }

        pos = serie.indexOf(toDate);
        if(pos>=0) {
            DateValueInterface dvBefore = serie.getDateValue(pos);
            if(!dvBefore.getDate().equals(toDate)) {
                double interpolatedValue = dvBefore.getValue();
                if(serie.size()>pos+1) {
                    DateValueInterface dv = serie.getDateValue(pos+1);
                    interpolatedValue = dvBefore.getValue()+((dv.getValue()-dvBefore.getValue())*(((double)(toDate.getTime()-dvBefore.getDate().getTime()))/(dv.getDate().getTime()-dvBefore.getDate().getTime())));
                }
                serie.insertDateValue(new DateValue(toDate,interpolatedValue),pos+1);
                LOG.debug("Inserted interpolated date: "+toDate+" value:"+interpolatedValue);
            }
            serie.deleteAfter(toDate);
        }
        return serie;
    }
    public DateValueSerieInterface getTotalPurchaseValues(String broker, Date fromDate, Date toDate, String purchaseName) {
        LOG.debug("getStockValues "+fromDate+" "+toDate);
        Vector subresult = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                subresult.addElement(getPurchaseValues(entry.getBroker(),entry.getStock(),fromDate,toDate,purchaseName));
            }
        }
        DateValueSerie result = getTotalStockValues(subresult, "Total "+purchaseName);
        if(LOG.isTraceEnabled()) {
            LOG.trace("Values for: "+result.getName());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(Iterator it=result.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                LOG.trace(format.format(dv.getDate())+" "+dv.getValue());
            }
        }
        return getDateLimitedSerie(fromDate,toDate,result);
    }

    private DateValueSerie getTotalStockValues(Vector series, String name) {
        LOG.debug("getTotalStockValues start "+System.currentTimeMillis());
        if(series.size()==0) {
            LOG.debug("getTotalStockValues end = null "+System.currentTimeMillis());
            return null;
        }
        // First create an empty result list with all dates
        DateValueSerie result = new DateValueSerie(name);
        for(int i=0;i<series.size();i++) {
            DateValueSerie serie = (DateValueSerie) series.elementAt(i);
            int lastPos = -1;
            for(int j=0;j<serie.size();j++) {
                DateValueInterface dv = serie.getDateValue(j);
                Date currentDate = dv.getDate();
                int pos = result.indexOf(currentDate,lastPos+1);
                if(pos>=0) {
                    DateValueInterface dvResult = result.getDateValue(pos);
                    if(!dvResult.getDate().equals(currentDate)) {
                        result.insertDateValue(new DateValue(currentDate,0),pos+1);
                    }
                }else {
                    result.insertDateValue(new DateValue(currentDate,0),0);
                }
                lastPos = pos;
            }
        }
        // Now step through each serie again and add values
        for(int i=0;i<series.size();i++) {
            DateValueSerie serie = (DateValueSerie) series.elementAt(i);
            double lastValue = -1;
            int lastPos = -1;
            for(int j=0;j<serie.size();j++) {
                DateValueInterface dv = serie.getDateValue(j);
                Date currentDate = dv.getDate();
                int pos = result.indexOf(currentDate,lastPos+1);
                DateValueInterface dvResult = result.getDateValue(pos);
                result.setDateValue(new DateValue(dvResult.getDate(),dvResult.getValue()+dv.getValue()),pos);

                // Step through all intermediate dates between last date and the current date and calculate summary entries
                if(lastPos>=0) {
                    for(int k=lastPos+1;k<pos;k++) {
                        DateValueInterface dvResultIntermediate = result.getDateValue(k);
                        result.setDateValue(new DateValue(dvResultIntermediate.getDate(),dvResultIntermediate.getValue()+lastValue),k);
                    }
                }
                lastPos = pos;
                lastValue = dv.getValue();
            }
            // Step throgh all dates after the last date and calculate summary entries
            for(int k=lastPos+1;k<result.size();k++) {
                DateValueInterface dvResultIntermediate = result.getDateValue(k);
                result.setDateValue(new DateValue(dvResultIntermediate.getDate(),dvResultIntermediate.getValue()+lastValue),k);
            }
        }
        LOG.debug("getTotalStockValues end = result.size()="+result.size()+" "+System.currentTimeMillis());
        return result;
    }
    private Vector getStockNumbers(String broker, Date fromDate, Date toDate, String serieName, SerieType type) {
        LOG.debug("getStockNumbers "+fromDate+" "+toDate);
        Vector result = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                result.addElement(getStockNumbers(entry.getBroker(),entry.getStock(),fromDate,toDate,serieName, type));
            }
        }
        return result;
    }

    public Vector getStockNumbers(String broker, Date fromDate, Date toDate, String serieName) {
        return getStockNumbers(broker, fromDate, toDate, serieName, SerieType.STOCKNUMBER_VALUES);
    }
    public Vector getStockNumberDiffs(String broker, Date fromDate, Date toDate, String serieName) {
        return getStockNumbers(broker, fromDate, toDate, serieName, SerieType.STOCKNUMBERDIFF_VALUES);
    }
    public DateValueSerieInterface[] getStockValues(String broker, Date fromDate, Date toDate) {
        LOG.debug("getStockValues "+fromDate+" "+toDate);
        Vector result = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                result.addElement(getStockValues(entry.getBroker(),entry.getStock(),fromDate,toDate));
            }
        }
        result.addElement(getTotalStockValues(result, "Total"));
        return (DateValueSerieInterface[]) result.toArray(new DateValueSerieInterface[0]);
    }

    private DateValueSerieInterface[] getPurchaseValues(String broker, Date fromDate, Date toDate, String purchaseName, SerieType type) {
        LOG.debug("getStockValues "+fromDate+" "+toDate);
        Vector result = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                result.addElement(getPurchaseValues(entry.getBroker(),entry.getStock(),fromDate,toDate,purchaseName, type));
            }
        }
        result.addElement(getTotalStockValues(result, "Total "+ purchaseName));
        return (DateValueSerieInterface[]) result.toArray(new DateValueSerieInterface[0]);
    }
    public DateValueSerieInterface[] getPurchaseValues(String broker, Date fromDate, Date toDate, String purchaseName) {
        return getPurchaseValues(broker, fromDate, toDate, purchaseName, SerieType.PURCHASE_VALUES);
    }

    public DateValueSerieInterface[] getPurchaseDiffValues(String broker, Date fromDate, Date toDate, String purchaseName) {
        return getPurchaseValues(broker, fromDate, toDate, purchaseName, SerieType.PURCHASEDIFF_VALUES);
    }

    public StockAccountValue getStockValue(Date date) {
        return getStockValue(null,date);
    }
    public StockAccountValue getStockValue(String broker, Date date) {
        LOG.debug("getStockValue "+date);
        double value=0;
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                StockAccountValue stockValue = getStockValue(entry.getBroker(),entry.getStock(),date);
                if(stockValue!=null) {
                    value += stockValue.getValue();
                }
            }
        }
        StockAccountValue result = new StockAccountValue();
        result.setValue(value);
        result.setNoOfStocks(0);
        result.setRate(0);
        return result;
    }

    public double getPurchaseValue(Date date) {
        return getPurchaseValue(null,date);
    }
    public double getPurchaseValue(String broker, Date date) {
        LOG.debug("getPurchaseValue "+date);
        double value=0;
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                value += getPurchaseValue(entry.getBroker(),entry.getStock(),date);
            }
        }
        return value;
    }

    protected BrokerManagerInterface getBrokerManager() {
        if(brokerManager==null) {
            brokerManager = (BrokerManagerInterface) getEnvironment().getServiceFactory().create("stock-brokermanager");
        }
        return brokerManager;
    }
}
