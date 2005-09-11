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
import erland.webapp.common.BaseEntity;
import erland.webapp.stocks.bl.entity.StockAccountStockEntry;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionList;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionFilterInterface;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.bl.service.StockStorageInterface;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;
import erland.util.StringUtil;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class StockAccount extends BaseEntity {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(StockAccount.class);
    private StockStorageInterface stockStorage;
    private String username;
    private Integer accountId;
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

    class SerieTypeController {
        private SerieType serieType = SerieType.STOCK_VALUES;

        public void setSerieType(SerieType serieType) {
            this.serieType = serieType;
        }
        public SerieType getSerieType() {
            return serieType;
        }
    }
    class DateValue implements DateValueInterface {
        private Date date;
        private double value;
        private double purchaseValue;
        private double numberOf;
        private double purchaseDiff;
        private double numberOfDiff;
        private SerieTypeController controller;

        public DateValue(SerieTypeController controller,Date date, double purchaseValue, double numberOf, double stockValue,double purchaseDiff, double numberOfDiff) {
            this.controller = controller;
            this.date = date;
            this.value = stockValue;
            this.purchaseValue = purchaseValue;
            this.numberOf = numberOf;
            this.numberOfDiff = numberOfDiff;
            this.purchaseDiff = purchaseDiff;
        }

        public DateValue(SerieTypeController controller,Date date, DateValue dv) {
            this(controller,date,dv.purchaseValue, dv.numberOf,dv.value,dv.purchaseDiff,dv.numberOfDiff);
        }

        public DateValue interpolated(DateValue dv,Date date) {
            DateValue next = dv;
            DateValue previous = this;
            if(date.before(this.date)) {
                previous = next;
                next = this;
            }
            double percent = (((double)(date.getTime()-previous.getDate().getTime()))/(next.getDate().getTime()-previous.getDate().getTime()));
            double interpolatedValue = previous.value+((next.value-previous.value)*percent);
            double interpolatedPurchaseValue = previous.purchaseValue+((next.purchaseValue-previous.purchaseValue)*percent);
            double interpolatedNumberOfValue = previous.numberOf+((next.numberOf-previous.numberOf)*percent);
            double interpolatedPurchaseDiff = previous.purchaseDiff+((next.purchaseDiff-previous.purchaseDiff)*percent);
            double interpolatedNumberOfDiff = previous.numberOfDiff+((next.numberOfDiff-previous.numberOfDiff)*percent);
            return new DateValue(controller,date,interpolatedPurchaseValue,interpolatedNumberOfValue,interpolatedValue,interpolatedPurchaseDiff,interpolatedNumberOfDiff);
        }
        public void set(DateValue dv) {
            this.date = dv.date;
            this.value = dv.value;
            this.purchaseValue = dv.purchaseValue;
            this.numberOf = dv.numberOf;
            this.numberOfDiff = dv.numberOfDiff;
            this.purchaseDiff = dv.purchaseDiff;
        }
        public void add(DateValue dv) {
            this.value+=dv.value;
            this.purchaseValue+=dv.purchaseValue;
            this.numberOf+=dv.numberOf;
            this.numberOfDiff+=dv.numberOfDiff;
            this.purchaseDiff+=dv.purchaseDiff;
        }

        public Date getDate() {
            return date;
        }

        public double getValue() {
            if(controller.getSerieType()==SerieType.STOCK_VALUES) {
                return value;
            }else if(controller.getSerieType()==SerieType.PURCHASE_VALUES) {
                return purchaseValue;
            }else if(controller.getSerieType()==SerieType.STOCKNUMBER_VALUES) {
                return numberOf;
            }else if(controller.getSerieType()==SerieType.PURCHASEDIFF_VALUES) {
                return purchaseDiff;
            }else if(controller.getSerieType()==SerieType.STOCKNUMBERDIFF_VALUES) {
                return numberOfDiff;
            }
            return 0;
        }

        public String toString() {
            return StringUtil.objectToString(this,null,Object.class,true);
        }
    }
    class DateValueSerie extends erland.webapp.diagram.DateValueSerie {
        private SerieTypeController controller = new SerieTypeController();
        public DateValueSerie(String name) {
            super(name);
        }
        public SerieTypeController getController() {
            return controller;
        }
    }

    class Value {
        //TODO: Maybe we need to have a purchaseValue attribute also ?
        private double value;
        private double numberOf;
        public Value() {}
        public Value(double value, double numberOf) {
            this.value = value;
            this.numberOf = numberOf;
        }
        public boolean isEmpty() {
            return value==0 && numberOf==0;
        }
        public void add(Value value) {
            this.value+=value.value;
            this.numberOf+=value.numberOf;
        }
        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public double getNumberOf() {
            return numberOf;
        }

        public void setNumberOf(double numberOf) {
            this.numberOf = numberOf;
        }

        public void addValue(double value) {
            this.value+=value;
        }
        public void addNumberOf(double numberOf) {
            this.numberOf+=numberOf;
        }
        public String toString() {
            return StringUtil.objectToString(this,null,Object.class,true);
        }
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
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    protected Integer getAccountId() {
        return accountId;
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
        Value value = getStockValue(s.getRates(),broker,stock,StockAccountTransactionList.getDateFromString(date));
        return value.getNumberOf();
    }

    private void addStockContinously(Value resultValue, DateValueSerieInterface values, StockAccountTransaction entry, Date fromDate, Date toDate, boolean includeFromDate, boolean includeToDate,Date startDatePermanentNumberOf) {
        LOG.debug("addStockContinously start "+entry.getValue()+" "+fromDate+" "+toDate+" "+startDatePermanentNumberOf);
        Date date;
        if(includeFromDate) {
            date = fromDate;
        }else {
            date = getNextMultiple(entry,fromDate);
        }
        int oldPos=0;
        while(date.getTime()<=toDate.getTime()) {
            if(includeToDate || date.getTime()<toDate.getTime()) {
                int pos = values.indexOf(date,oldPos);
                if(pos>=0) {
                    oldPos=pos;
                    DateValueInterface dv = values.getDateValue(pos);
                    if(LOG.isDebugEnabled()) LOG.debug("getStockContinously add "+entry.getValue()+" "+dv.getDate());
                    resultValue.addValue(entry.getValue());
                    if(startDatePermanentNumberOf==null || dv.getDate().after(startDatePermanentNumberOf)) {
                        resultValue.addNumberOf(entry.getValue()/dv.getValue());
                    }
                }
            }
            date = getNextMultiple(entry,date);
        }
        LOG.debug("getStockContinously end result="+entry.getValue()+" "+fromDate+" "+toDate+" "+startDatePermanentNumberOf);
    }

    /**
     * Calculate the change of stock value for a specified type of result and transation
     * Observe that the value returned indicates how much the calculation should be changed due to the specified transation,
     * it does not return a calculated value in which previously calcation has been taken into account
     * @param values The stock value serie to get values from
     * @param entry The transaction to use in the calculation
     * @param pos The position in the stock value serie
     * @param isPermanent Indicates if this is a permanent transaction
     * @return The calculated stock value, -1 for a permanent transaction means no calculation has been made
     */
    private Value getValue(DateValueSerieInterface values, StockAccountTransaction entry, int pos, boolean isPermanent) {
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
        Value result = new Value();
        // If a position to use in the stock value serie has been found or specified
        if(pos>=0) {
            DateValueInterface dv = values.getDateValue(pos);

            // If the transaction entry contains a change of number of stocks
            if(entry.getNumber()!=0) {
                result.setNumberOf(entry.getNumber());
                // If the transation entry contains a possitive purchase value
                if(entry.getValue()>=0) {
                    // Calculate the real purchase value by multiplying it with the number of stocks
                    result.setValue(entry.getNumber()*entry.getValue());
                }else {
                    // If this is a permanent transaction
                    if(isPermanent) {
                        // TODO: Why do we need to return -1 instead of 0 ?
                        // Return -1 to indicate that we have not done any calculation
                        result.setValue(-1);
                    }else {
                        // Calculate the real purchase value by multiplying it with the number of stocks
                        result.setValue(entry.getNumber()*dv.getValue());
                    }
                }
            }else {
                result.setNumberOf(entry.getValue()/dv.getValue());
                result.setValue(entry.getValue());
            }
        }
        if(LOG.isDebugEnabled()) LOG.debug("getValue("+entry+")="+result);
        // Else this transaction should not affect the calculation of the specified type of result
        return result;
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
        if(LOG.isDebugEnabled()) LOG.debug("getNextMultiple in = "+calMultiple.getTime()+" "+startDate);
        int yearMultiple=calMultiple.get(Calendar.YEAR);
        int monthMultiple=calMultiple.get(Calendar.MONTH);
        int dayMultiple=calMultiple.get(Calendar.DAY_OF_MONTH);
        calMultiple.set(Calendar.DAY_OF_MONTH,1);
        if(yearMultiple<calCurrent.get(Calendar.YEAR)) {
            yearMultiple = calCurrent.get(Calendar.YEAR);
            monthMultiple = 0;
            calMultiple.set(Calendar.YEAR,yearMultiple);
            //if(calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)<calMultiple.get(Calendar.DAY_OF_MONTH)) {
            //    calMultiple.set(Calendar.DAY_OF_MONTH,calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH));
            //}
            calMultiple.set(Calendar.MONTH,monthMultiple);
        }
        if(monthMultiple<calCurrent.get(Calendar.MONTH)) {
            monthMultiple = calCurrent.get(Calendar.MONTH);
            //if(calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)<calMultiple.get(Calendar.DAY_OF_MONTH)) {
            //    calMultiple.set(Calendar.DAY_OF_MONTH,calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
            //}
            calMultiple.set(Calendar.MONTH,monthMultiple);
        }
        if(dayMultiple<=calCurrent.get(Calendar.DAY_OF_MONTH)||calCurrent.getActualMaximum(Calendar.DAY_OF_MONTH)==calCurrent.get(Calendar.DAY_OF_MONTH)) {
            monthMultiple++;
            if(monthMultiple>11) {
                yearMultiple++;
                monthMultiple-=12;
            }
            //calMultiple.set(Calendar.DAY_OF_MONTH,1);
            calMultiple.set(Calendar.MONTH,monthMultiple);
            calMultiple.set(Calendar.YEAR,yearMultiple);
        }
        if(calMultiple.getActualMaximum(Calendar.DAY_OF_MONTH)<dayMultiple) {
            calMultiple.set(Calendar.DAY_OF_MONTH,calMultiple.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
        }else {
            calMultiple.set(Calendar.DAY_OF_MONTH,dayMultiple);
        }
        if(LOG.isDebugEnabled()) LOG.debug("getNextMultiple = "+calMultiple.getTime());
        return calMultiple.getTime();
    }

    /**
     * Get value of the stock at the specified date
     * @param values The stock values to look in
     * @param broker The id of the broker
     * @param stock The id of the stock
     * @param dateTo The date to get the value for
     * @return The value of the stock at the specified date
     */
    private Value getStockValue(DateValueSerieInterface values, String broker, String stock, Date dateTo) {
        LOG.debug("getStockValue start "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
        Value currentValue=new Value();

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
            currentValue = getValue(values,permanent,-1,true);
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
        Date startDateNumberOf = null;
        double permanentNumberOf = 0;

        // Get the last permanent value transactions before toDate, transactions after toDate does not affect the result
        // and permanent transactions before the "last one before toDate" will not affect anything since there calculation
        // will be reset when the last one is reached
        StockAccountTransaction permanentWithNumberOnly = permanentStocks.getTransactionBefore(broker,stock,dateTo,null);
        // If a permanent value transaction before toDate exists and is a permanent transation of the type where only
        // number of stocks has been specified
        if(permanentWithNumberOnly!=null && permanentWithNumberOnly.getValue()<0) {
            // Get stock value for the permanent transaction
            Value val = getValue(values,permanentWithNumberOnly,-1,true);

            // If number of stocks exists and date of permanent value transation is after start date
            if(val.getNumberOf()>=0 && permanentWithNumberOnly.getDate().getTime()>startDate.getTime()) {
                // Set current value of number of stocks to the calculated value of the permanent value transaction
                permanentNumberOf = val.getNumberOf();
                // Set start date of number of stocks calculation to the date of the permanent value transaction
                startDateNumberOf = permanentWithNumberOnly.getDate();
                LOG.debug("getStockValue permAdj "+startDateNumberOf);
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
            currentValue.add(getValue(values,entry,-1,false));
            // Reset number of stocks to permanent value if this entry is before the permanent value
            if(startDateNumberOf!=null && !entry.getDate().after(startDateNumberOf)) {
                currentValue.setNumberOf(permanentNumberOf);
            }
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
            addStockContinously(currentValue, values,beforeStartDate,startDate,nextDate,false,includeNextDate,startDateNumberOf);
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
            addStockContinously(currentValue,values,entry,entry.getDate(),nextDate,true,includeNextDate,startDateNumberOf);
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
     * @return The calculated stock value serie
     */
    private DateValueSerie getStockValues(DateValueSerieInterface values, String broker, String stock, Date fromDate, Date toDate) {
        LOG.debug("getStockValues start "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
        Date dateFrom = fromDate;
        Date dateTo = toDate;
        DateValueSerie result = new DateValueSerie(values.getName());
        //TODO: Why can't we have some default values instead of requesting that both from and to is specified ?
        if(dateTo==null || values.size()==0) {
            LOG.debug("getStockValues end result.size()="+result.size()+" "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
            return result;
        }
        int curPos = -1;
        if(dateFrom!=null) {
            curPos =values.indexOf(dateFrom);
        }
        int finalEndPos = values.indexOf(dateTo);


        //If fromDate is before all available stock values AND
        //   toDate is equal to or after the first available stock value
        if(curPos<0 && finalEndPos>=0) {
            // Set fromDate to the first available stock value
            curPos=0;
            dateFrom = new Date(values.getDateValue(0).getDate().getTime());
        }

        // Get value of the stock at fromDate
        Value currentValue = getStockValue(values,broker,stock,dateFrom);
        Value oldValue = new Value();

        // Get all transaction entries which we need to use when calculating
        StockAccountTransaction currentMultiple = getPurchaseContinouslyEntries().getTransactionBefore(broker,stock,dateFrom,null);
        StockAccountTransactionListInterface purchaseMultiple = getPurchaseContinouslyEntries().getTransactions(broker,stock,dateFrom,dateTo,null);
        StockAccountTransactionListInterface purchaseOnce = getPurchaseOnceEntries().getTransactions(broker, stock, dateFrom, dateTo,null);
        StockAccountTransactionListInterface permanentStocks = getPermanentEntries().getTransactions(broker,stock,dateFrom, dateTo,null);

        // If no stock value exists within the fromDate<->toDate interval OR
        //    the fromDate and toDate points to the same stock value entry
        if((curPos <0 && finalEndPos<0)) {
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
            DateValueInterface dv = values.getDateValue(curPos);

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
                DateValueInterface dateValue = values.getDateValue(curPos);

                // Calculate stock value
                double value = currentValue.getNumberOf()*dateValue.getValue();
                // Calculate purchase value differens compared to previous entry
                double purchaseDiff = currentValue.getValue()-oldValue.getValue();
                // Calculate number of stocks differens compared to previous entry
                double numberOfDiff = currentValue.getNumberOf()-oldValue.getNumberOf();

                // Add entry to serie
                DateValue newDV = new DateValue(result.getController(),dateValue.getDate(),currentValue.getValue(),currentValue.getNumberOf(),value,purchaseDiff,numberOfDiff);
                result.addDateValue(newDV);

                // Store current values for usage in next iteration
                oldValue.setNumberOf(currentValue.getNumberOf());
                oldValue.setValue(currentValue.getNumberOf());
            }

            //
            // PHASE 3:
            // Perform calculations for the transactions that has been found in PHASE 1 and should affect
            // calculation in this iteration. For each of these transactions peform a single calculation before
            // we step to the next iteration
            //
            if(nextPermanent!=null) {
                Value val =getValue(values,nextPermanent,endPos,true);
                LOG.debug("adding permanent "+val+" "+values.getDateValue(endPos).getDate());
                // If a stockvalue is found for a permanent value transaction and it should affect the calculation
                // in this iteration and for this time of result
                if(val.getValue()>=0) {
                    // Set the result to the value of the permanent value transation
                    currentValue = val;
                }else if(val.getNumberOf()>=0) {
                    // Just adjust the number of stocks if this is a permanent value transaction which just specifies number
                    // of stocks
                    currentValue.setNumberOf(val.getNumberOf());
                }
            }
            if(next!=null) {
                LOG.debug("adding once "+getValue(values,next,endPos,false)+" "+values.getDateValue(endPos).getDate());
                // Add the stockvalue for a purchace once transaction if it should affect the calculation in this iteration
                // and this type of result
                currentValue.add(getValue(values,next,endPos,false));
            }
            //LOG.debug("AddMultiple = "+bAddMultiple);
            if(currentMultiple!=null && nextMultiple==null) {
                // Add the stockvalue for a continously transaction found in a previous iteration if it should affect
                // the calculation in this iteration and this type of result
                if(bAddCurrentMultiple) {
                    LOG.debug("adding current multiple "+getValue(values,currentMultiple,endPos,false)+" "+values.getDateValue(endPos).getDate());
                    currentValue.add(getValue(values,currentMultiple,endPos,false));
                }
            }
            if(nextMultiple!=null) {
                LOG.debug("adding next multiple "+getValue(values,nextMultiple,endPos,false)+" "+values.getDateValue(endPos).getDate());
                // Add the stockvalue for a continously transation found in this iteration if it should affect the
                // calculation in this iteration and this type of result
                currentValue.add(getValue(values,nextMultiple,endPos,false));
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
        DateValueSerie valueSerie = getStockValues(s.getRates(),broker,stock,null,date);
        DateValue value = (DateValue) valueSerie.getDateValue(valueSerie.size()-1);

        valueSerie.getController().setSerieType(SerieType.STOCKNUMBER_VALUES);
        double noOfStocks = value.getValue();

        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double purchaseValue = value.getValue();

        int i = s.getRates().indexOf(date);
        if(i>=0) {
            StockAccountValue result = new StockAccountValue();
            result.setValue(noOfStocks*s.getRates().getDateValue(i).getValue());
            result.setRate(s.getRates().getDateValue(i).getValue());
            result.setNoOfStocks(noOfStocks);
            result.setPurchaseValue(purchaseValue);
            result.setIncreasedValue(result.getValue()-purchaseValue);
            result.setTotalStatistics((result.getValue()-purchaseValue)*100.0/purchaseValue);
            calculateYearStatistics(result,valueSerie);
            calculateStockStatistics(result,broker,stock, valueSerie);
            return result;
        }else {
            return null;
        }
    }

    private double calculateThisYearStatistics(DateValueSerie valueSerie) {
        if(valueSerie.size()==0) {
            return 0;
        }
        int pos = valueSerie.size()-1;
        DateValue dv = (DateValue) valueSerie.getDateValue(pos);
        Date date = dv.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.DAY_OF_MONTH,1);
        Date startOfYear = cal.getTime();
        int startPos = valueSerie.indexOf(startOfYear);
        if(startPos<0) {
            return 0;
        }
        DateValue startDV = (DateValue) valueSerie.getDateValue(startPos);

        valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
        double startValue = 0;
        while(startValue<=1000 && startPos<valueSerie.size()) {
            startDV = (DateValue) valueSerie.getDateValue(startPos++);
            startValue = startDV.getValue();
            if(!startDV.getDate().before(date)) {
                return 0;
            }
        }

        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double startPurchase = startDV.getValue();

        valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
        double endValue = dv.getValue();
        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double endPurchase = dv.getValue();

        if(pos>startPos) {
            double statisticValue = endValue-startValue-(endPurchase-startPurchase);
            return statisticValue*100/startValue;
        }
        return 0;
    }

    private void calculateYearStatistics(StockAccountValue accountValue,DateValueSerie valueSerie) {
        if(valueSerie.size()==0) {
            return;
        }

        int pos = valueSerie.size()-1;
        boolean bFirst = true;
        while(pos>=0) {
            DateValue dv = (DateValue) valueSerie.getDateValue(pos);
            Date date = dv.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MONTH,0);
            cal.set(Calendar.DAY_OF_MONTH,1);
            int year = cal.get(Calendar.YEAR);
            Date startOfYear = cal.getTime();
            int startPos = valueSerie.indexOf(startOfYear);
            if(startPos<0) {
                break;
            }
            DateValue startDV = (DateValue) valueSerie.getDateValue(startPos);

            valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
            double startPurchaseInitial = startDV.getValue();

            valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
            double startValue = 0;
            while(startValue<=1000 && startPos<valueSerie.size()) {
                startDV = (DateValue) valueSerie.getDateValue(startPos++);
                startValue = startDV.getValue();
                if(!startDV.getDate().before(date)) {
                    break;
                }
            }

            valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
            double startPurchase = startDV.getValue();

            valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
            double endValue = dv.getValue();
            valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
            double endPurchase = dv.getValue();

            if(startValue<=1000) {
                break;
            }
            if(pos>startPos) {
                double purchaseValue = endPurchase-startPurchaseInitial;
                double statisticValue = endValue-startValue-(endPurchase-startPurchase);
                double statisticPercent = statisticValue*100/startValue;

                accountValue.setYearStatistic(year,statisticPercent,statisticValue,purchaseValue);
                if(bFirst) {
                    accountValue.setTotalStatisticsThisYear(statisticPercent);
                }
            }
            bFirst = false;
            cal.add(Calendar.DATE,-1);
            date = cal.getTime();
            pos = valueSerie.indexOf(date);
        }
    }

    private void calculateStockStatistics(StockAccountValue accountValue,String brokerId, String stockId, DateValueSerie valueSerie) {
        if(valueSerie.size()<=1) {
            return;
        }
        DateValue startDV = (DateValue) valueSerie.getDateValue(0);
        DateValue endDV = (DateValue) valueSerie.getDateValue(valueSerie.size()-1);

        valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
        double startValue = startDV.getValue();

        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double startPurchase = startDV.getValue();

        valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
        double endValue = endDV.getValue();
        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double endPurchase = endDV.getValue();

        double statisticPercent = 0;
        if(endPurchase!=0) {
            statisticPercent = (endValue-endPurchase)*100/endPurchase;
        }

        String stockName = getBrokerManager().getStockName(brokerId,stockId);
        String brokerName = getBrokerManager().getBrokerName(brokerId);
        accountValue.setStockStatistic(brokerId,stockId,brokerName,stockName,statisticPercent,endValue,endPurchase,endValue-endPurchase,calculateThisYearStatistics(valueSerie));
    }

    public double getPurchaseValue(String broker, String stock, Date date) {
        LOG.debug("getPurchaseValue "+broker+" "+stock+" "+date);
        StockInterface s = stockStorage.getStock(broker,stock);
        Value value = getStockValue(s.getRates(),broker,stock,date);
        return value.getValue();
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
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate);
        dateValues.getController().setSerieType(type);
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
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate);
        dateValues.setName(getBrokerManager().getStockName(broker,stock));
        dateValues.getController().setSerieType(SerieType.STOCK_VALUES);
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
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate);
        dateValues.setName(getBrokerManager().getStockName(broker,stock)+" "+purchaseName);
        dateValues.getController().setSerieType(type);
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
        return getTotalStockValues(broker,fromDate,toDate,null);
    }

    public DateValueSerieInterface getTotalStockValues(String broker, Date fromDate, Date toDate, StockAccountValue statistics) {
        if(LOG.isDebugEnabled()) LOG.debug("getStockValues "+fromDate+" "+toDate);
        Vector subresult = new Vector();
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                DateValueSerieInterface valueSeries = getStockValues(entry.getBroker(),entry.getStock(),fromDate,toDate);
                if(statistics!=null) {
                    calculateStockStatistics(statistics,entry.getBroker(),entry.getStock(),(DateValueSerie) valueSeries);
                }
                subresult.addElement(valueSeries);
            }
        }
        DateValueSerie result = getTotalStockValues(subresult, "Total");
        result.getController().setSerieType(SerieType.STOCK_VALUES);
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
        if(fromDate!=null) {
            int pos = serie.indexOf(fromDate);
            if(pos>=0) {
                DateValue dvBefore = (DateValue) serie.getDateValue(pos);
                if(!dvBefore.getDate().equals(fromDate)) {
                    DateValue interpolatedDV = null;
                    if(serie.size()>pos+1) {
                        DateValue dv = (DateValue) serie.getDateValue(pos+1);
                        interpolatedDV = dv.interpolated(dvBefore,fromDate);
                    }else {
                        interpolatedDV = new DateValue(serie.getController(),fromDate,dvBefore);
                    }
                    serie.insertDateValue(interpolatedDV,pos+1);
                    LOG.debug("Inserted interpolated date: "+fromDate+" value:"+interpolatedDV);
                }
                serie.deleteBefore(fromDate);
            }
        }
        if(toDate!=null) {
            int pos = serie.indexOf(toDate);
            if(pos>=0) {
                DateValue dvBefore = (DateValue) serie.getDateValue(pos);
                if(!dvBefore.getDate().equals(toDate)) {
                    DateValue interpolatedDV = null;
                    if(serie.size()>pos+1) {
                        DateValue dv = (DateValue) serie.getDateValue(pos+1);
                        interpolatedDV = dv.interpolated(dvBefore,toDate);
                    }else {
                        interpolatedDV = new DateValue(serie.getController(),toDate,dvBefore);
                    }
                    serie.insertDateValue(interpolatedDV,pos+1);
                    LOG.debug("Inserted interpolated date: "+toDate+" value:"+interpolatedDV);
                }
                serie.deleteAfter(toDate);
            }
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
        result.getController().setSerieType(SerieType.PURCHASE_VALUES);
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
                        result.insertDateValue(new DateValue(result.getController(),currentDate,0,0,0,0,0),pos+1);
                    }
                }else {
                    result.insertDateValue(new DateValue(result.getController(),currentDate,0,0,0,0,0),0);
                }
                lastPos = pos;
            }
        }
        // Now step through each serie again and add values
        for(int i=0;i<series.size();i++) {
            DateValueSerie serie = (DateValueSerie) series.elementAt(i);
            DateValue lastValue = new DateValue(result.getController(),null,0,0,0,0,0);
            int lastPos = -1;
            for(int j=0;j<serie.size();j++) {
                DateValue dv = (DateValue) serie.getDateValue(j);
                Date currentDate = dv.getDate();
                int pos = result.indexOf(currentDate,lastPos+1);
                DateValue dvResult = (DateValue) result.getDateValue(pos);

                dvResult.add(dv);

                // Step through all intermediate dates between last date and the current date and calculate summary entries
                if(lastPos>=0) {
                    for(int k=lastPos+1;k<pos;k++) {
                        DateValue dvResultIntermediate = (DateValue) result.getDateValue(k);
                        dvResultIntermediate.add(lastValue);
                    }
                }
                lastPos = pos;
                lastValue.set(dv);
            }
            // Step throgh all dates after the last date and calculate summary entries
            for(int k=lastPos+1;k<result.size();k++) {
                DateValue dvResultIntermediate = (DateValue) result.getDateValue(k);
                dvResultIntermediate.add(lastValue);
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
        StockAccountValue result = new StockAccountValue();
        DateValueSerie valueSerie = (DateValueSerie) getTotalStockValues(broker,null,date,result);
        DateValue dv = (DateValue) valueSerie.getDateValue(valueSerie.size()-1);

        valueSerie.getController().setSerieType(SerieType.PURCHASE_VALUES);
        double purchaseValue = dv.getValue();

        valueSerie.getController().setSerieType(SerieType.STOCK_VALUES);
        double value = dv.getValue();

        result.setValue(value);
        result.setRate(0);
        result.setNoOfStocks(0);
        result.setPurchaseValue(purchaseValue);
        result.setIncreasedValue(value-purchaseValue);
        result.setTotalStatistics((result.getValue()-purchaseValue)*100.0/purchaseValue);
        calculateYearStatistics(result,valueSerie);
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
