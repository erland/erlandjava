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

import erland.webapp.diagram.DateValue;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueSerie;
import erland.webapp.diagram.DateValueInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.bl.entity.StockAccountStockEntry;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionFilterInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionList;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.bl.logic.storage.StockStorageInterface;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class StockAccount implements EntityInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(StockAccount.class);
    private StockStorageInterface stockStorage;
    private String userName;
    private WebAppEnvironmentInterface environment;
    private BrokerManagerInterface brokerManager;

    static class SerieType {
        private SerieType() {};
        public static final SerieType PURCHASE_VALUES = new SerieType();
        public static final SerieType PURCHASEDIFF_VALUES = new SerieType();
        public static final SerieType STOCK_VALUES = new SerieType();
        public static final SerieType STOCKNUMBER_VALUES = new SerieType();
        public static final SerieType STOCKNUMBERDIFF_VALUES = new SerieType();
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public void init(String userName, StockStorageInterface stockStorage) {
        this.userName = userName;
        this.stockStorage = stockStorage;
    }
    protected String getUsername() {
        return userName;
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
        double noOfStocks = getStockValue(s.getRates(),broker,stock,StockAccountTransactionList.getDateFromString(date),SerieType.STOCKNUMBER_VALUES);
        return noOfStocks;
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
        LOG.debug("getStockContinously end "+entry.getValue()+" "+fromDate+" "+toDate);
        return number;
    }

    private double getValue(DateValueSerieInterface values, StockAccountTransaction entry, int pos, SerieType type, boolean isPermanent) {
        if(pos<0) {
            pos = values.indexOf(entry.getDate());
        }
        if(pos>=0) {
            DateValue dv = (DateValue) values.getDateValue(pos);
            if(entry.getNumber()!=0) {
                if(type==SerieType.STOCK_VALUES||type==SerieType.STOCKNUMBER_VALUES||type==SerieType.STOCKNUMBERDIFF_VALUES) {
                    return entry.getNumber();
                }else if(type==SerieType.PURCHASE_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                    if(entry.getValue()>=0) {
                        return entry.getNumber()*entry.getValue();
                    }else {
                        if(isPermanent) {
                            return -1;
                        }else {
                            return entry.getNumber()*dv.getValue();
                        }
                    }
                }
            }else {
                if(type==SerieType.STOCK_VALUES||type==SerieType.STOCKNUMBER_VALUES||type==SerieType.STOCKNUMBERDIFF_VALUES) {
                    return entry.getValue()/dv.getValue();
                }else if(type==SerieType.PURCHASE_VALUES||type==SerieType.PURCHASEDIFF_VALUES) {
                    return entry.getValue();
                }
            }
        }
        return 0;
    }
    private Date getNextMultiple(StockAccountTransaction transaction, Date startDate) {
        final Calendar calMultiple = Calendar.getInstance();
        final Calendar calCurrent = Calendar.getInstance();
        int monthMultiple=0;
        int yearMultiple=0;
        int dayMultiple=0;
        calCurrent.setTime(startDate);
        calMultiple.setTime(transaction.getDate());
        LOG.debug("getNextMultiple in = "+calMultiple.getTime()+" "+startDate);
        yearMultiple=calMultiple.get(Calendar.YEAR);
        monthMultiple=calMultiple.get(Calendar.MONTH);
        dayMultiple=calMultiple.get(Calendar.DAY_OF_MONTH);
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
    private double getStockValue(DateValueSerieInterface values, String broker, String stock, Date dateTo, SerieType type) {
        LOG.debug("getStockValue start "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
        double currentValue=0;
        if(dateTo==null) {
            LOG.debug("getStockValue end "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
            return currentValue;
        }
        StockAccountTransactionListInterface permanentStocks = getPermanentEntries();
        StockAccountTransactionListInterface oneTimePurchase = getPurchaseOnceEntries();
        StockAccountTransactionListInterface continouslyPurchase = getPurchaseContinouslyEntries();
        StockAccountTransaction permanent = permanentStocks.getTransactionBefore(broker,stock,dateTo,
                new StockAccountTransactionFilterInterface() {
                    public boolean isMatching(StockAccountTransaction transaction) {
                        return transaction.getValue()>=0;
                    }
        });

        Date startDate;
        if(permanent!=null) {
            currentValue = getValue(values,permanent,-1,type,true);
            startDate = permanent.getDate();
            LOG.debug("getStockValue permanent "+startDate);
        }else {
            startDate = new Date(0);
        }

        int startPos = values.indexOf(dateTo);
        if(startPos<0) {
            LOG.debug("getStockValue end "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
            return currentValue;
        }

        StockAccountTransaction permanentWithNumberOnly = permanentStocks.getTransactionBefore(broker,stock,dateTo,null);
        if(permanentWithNumberOnly!=null && permanentWithNumberOnly.getValue()<0) {
            double val = getValue(values,permanentWithNumberOnly,-1,type,true);
            if(val>=0 && permanentWithNumberOnly.getDate().getTime()>startDate.getTime()) {
                currentValue = val;
                startDate = permanentWithNumberOnly.getDate();
                LOG.debug("getStockValue permOnly "+startDate);
            }
        }

        StockAccountTransactionListInterface beforeFirstDate = oneTimePurchase.getTransactions(broker,stock,startDate,dateTo,null);
        for(int i=0;i<beforeFirstDate.size();i++) {
            StockAccountTransaction entry = beforeFirstDate.getTransaction(i);
            currentValue += getValue(values,entry,-1,type,false);
            LOG.debug("getStockValue once "+entry.getDate());
        }

        StockAccountTransaction beforeStartDate = continouslyPurchase.getTransactionBefore(broker,stock,startDate,null);
        StockAccountTransaction afterStartDate = continouslyPurchase.getTransactionAfter(broker,stock,startDate,null);
        if(beforeStartDate!=null) {
            Date nextDate=dateTo;
            boolean includeNextDate = true;
            if(afterStartDate!=null && afterStartDate.getDate().getTime()<=nextDate.getTime()) {
                includeNextDate = false;
                nextDate=afterStartDate.getDate();
            }
            currentValue += getStockContinously(values,beforeStartDate,startDate,nextDate,type,false,includeNextDate);
        }
        beforeFirstDate = continouslyPurchase.getTransactions(broker, stock, startDate, dateTo,null);
        for(int i=0;i<beforeFirstDate.size();i++) {
            StockAccountTransaction entry = beforeFirstDate.getTransaction(i);
            Date nextDate;
            boolean includeNextDate = true;
            if(i<beforeFirstDate.size()-1) {
                StockAccountTransaction next = beforeFirstDate.getTransaction(i+1);
                nextDate = next.getDate();
                includeNextDate = false;
            }else {
                nextDate = dateTo;
            }
            LOG.debug("getStockValue multiple "+entry.getDate());
            currentValue += getStockContinously(values,entry,entry.getDate(),nextDate,type,true,includeNextDate);
        }

        LOG.debug("getStockValue end "+broker+" "+stock+" "+dateTo+" "+System.currentTimeMillis());
        return currentValue;
    }

    private DateValueSerie getStockValues(DateValueSerieInterface values, String broker, String stock, Date fromDate, Date toDate,SerieType type) {
        LOG.debug("getStockValues start "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
        Date dateFrom = fromDate;
        Date dateTo = toDate;
        DateValueSerie result = new DateValueSerie(values.getName());
        if(dateFrom==null||dateTo==null) {
            LOG.debug("getStockValues end "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
            return result;
        }
        int finalEndPos = values.indexOf(dateTo);
        int curPos=values.indexOf(dateFrom);
        if(curPos<0 && finalEndPos>=0) {
            curPos=0;
            dateFrom.setTime(values.getDateValue(0).getDate().getTime());
        }

        double currentValue = getStockValue(values,broker,stock,dateFrom,type);
        double oldValue = 0;

        StockAccountTransaction currentMultiple = getPurchaseContinouslyEntries().getTransactionBefore(broker,stock,dateFrom,null);
        StockAccountTransactionListInterface purchaseMultiple = getPurchaseContinouslyEntries().getTransactions(broker,stock,dateFrom,dateTo,null);
        StockAccountTransactionListInterface purchaseOnce = getPurchaseOnceEntries().getTransactions(broker, stock, dateFrom, dateTo,null);
        StockAccountTransactionListInterface permanentStocks = getPermanentEntries().getTransactions(broker,stock,dateFrom, dateTo,null);

        if((curPos <0 && finalEndPos<0) || curPos==finalEndPos) {
            LOG.debug("getStockValues end "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
            return result;
        }

        boolean bLast = curPos==finalEndPos;
        while(curPos<=finalEndPos) {
            DateValue dv = (DateValue) values.getDateValue(curPos);
            StockAccountTransaction next = purchaseOnce.getTransactionAfter(broker,stock,dv.getDate(),null);
            int endPos = -1;
            if(next!=null) {
                LOG.debug("found oncePurchase "+next.getDate());
                endPos = values.indexOf(next.getDate());
            }else {
                endPos = finalEndPos;
            }

            StockAccountTransaction nextPermanent = permanentStocks.getTransactionAfter(broker,stock,dv.getDate(),null);
            if(nextPermanent!=null) {
                LOG.debug("found permanent "+nextPermanent.getDate());
                int pos = values.indexOf(nextPermanent.getDate());
                if(pos<endPos) {
                    next=null;
                    endPos=pos;
                }else if(pos>endPos) {
                    nextPermanent=null;
                }
            }

            StockAccountTransaction nextMultiple = purchaseMultiple.getTransactionAfter(broker, stock, dv.getDate(),null);
            if(nextMultiple!=null) {
                LOG.debug("found multiple "+nextMultiple.getDate());
                int pos = values.indexOf(nextMultiple.getDate());
                if(nextMultiple.getDate().getTime()>dateTo.getTime()) {
                    nextMultiple=null;
                }
                if(pos<endPos) {
                    next=null;
                    nextPermanent=null;
                    endPos = pos;
                }else if(pos>endPos) {
                    nextMultiple = null;
                }
            }

            boolean bAddCurrentMultiple = false;
            if(currentMultiple!=null) {
                LOG.debug("found current "+currentMultiple.getDate());
                bAddCurrentMultiple=true;
                //LOG.debug("find next multiple: "+dv.getStartDate()+" "+yearMultiple+" "+monthMultiple);
                Date dateMultiple = getNextMultiple(currentMultiple,dv.getDate());
                int pos = values.indexOf(dateMultiple);
                //LOG.debug("find next multiple: "+calMultiple.getTime()+" "+pos+" "+endPos);
                if(dateMultiple.getTime()>dateTo.getTime()) {
                    currentMultiple=null;
                }
                if(pos<endPos) {
                    next = null;
                    nextPermanent = null;
                    nextMultiple = null;
                    endPos = pos;
                    //LOG.debug("find next multiple: found");
                }else if(pos>endPos) {
                    bAddCurrentMultiple=false;
                    //LOG.debug("find next multiple: no more");
                }
            }

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
            if(nextPermanent!=null) {
                double val =getValue(values,nextPermanent,endPos,type,true);
                LOG.debug("adding permanent "+val+" "+values.getDateValue(endPos).getDate());
                if(val>=0) {
                    currentValue = val;
                }
            }
            if(next!=null) {
                LOG.debug("adding once "+getValue(values,next,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                currentValue+=getValue(values,next,endPos,type,false);
            }
            //LOG.debug("AddMultiple = "+bAddMultiple);
            if(currentMultiple!=null && nextMultiple==null) {
                if(bAddCurrentMultiple) {
                    LOG.debug("adding current multiple "+getValue(values,currentMultiple,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                    currentValue+=getValue(values,currentMultiple,endPos,type,false);
                }
            }
            if(nextMultiple!=null) {
                LOG.debug("adding next multiple "+getValue(values,nextMultiple,endPos,type,false)+" "+values.getDateValue(endPos).getDate());
                currentValue += getValue(values,nextMultiple,endPos,type,false);
                currentMultiple = nextMultiple;
            }
            bLast = (curPos==finalEndPos);
        }
        LOG.debug("getStockValues end "+broker+" "+stock+" "+fromDate+" "+toDate+" "+System.currentTimeMillis());
        return result;
    }

    public double getStockValue(String broker, String stock, Date date) {
        LOG.debug("getStockValue "+broker+" "+stock+" "+date);
        StockInterface s = stockStorage.getStock(broker,stock);
        double noOfStocks = getStockValue(s.getRates(),broker,stock,date,SerieType.STOCK_VALUES);
        int i = s.getRates().indexOf(date);
        if(i>=0) {
            return noOfStocks*s.getRates().getDateValue(i).getValue();
        }else {
            return 0;
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
        return dateValues;
    }

    public DateValueSerieInterface getStockValues(String broker, String stock, Date fromDate, Date toDate) {
        LOG.debug("getStockValues "+broker+" "+stock+" "+fromDate+" "+toDate);
        StockInterface s = stockStorage.getStock(broker,stock);
        DateValueSerieInterface values = s.getRates();
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate, SerieType.STOCK_VALUES);
        dateValues.setName(getBrokerManager().getStockName(broker,stock));
        return dateValues;
    }

    private DateValueSerieInterface getPurchaseValues(String broker, String stock, Date fromDate, Date toDate, String purchaseName, SerieType type) {
        LOG.debug("getStockValues "+broker+" "+stock+" "+fromDate+" "+toDate);
        StockInterface s = stockStorage.getStock(broker,stock);
        DateValueSerieInterface values = s.getRates();
        DateValueSerie dateValues = getStockValues(values,broker,stock,fromDate,toDate, type);
        dateValues.setName(getBrokerManager().getStockName(broker,stock)+" "+purchaseName);
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
        return getTotalStockValues(subresult, "Total");
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
        return getTotalStockValues(subresult, "Total "+purchaseName);
    }

    private DateValueSerieInterface getTotalStockValues(Vector series, String name) {
        LOG.debug("getTotalStockValues start "+System.currentTimeMillis());
        if(series.size()==0) {
            LOG.debug("getTotalStockValues end "+System.currentTimeMillis());
            return null;
        }
        DateValueSerie result = new DateValueSerie(name);
        for(int i=0;i<series.size();i++) {
            DateValueSerie serie = (DateValueSerie) series.elementAt(i);

            int lastCalculated = -1;
            double lastValue = 0;
            double lastResultValue = 0;
            int currentPos = 0;
            for(int j=0;j<serie.size();j++) {
                DateValueInterface dvNew = serie.getDateValue(j);
                int pos = result.indexOf(dvNew.getDate(),currentPos);
                if(pos>=0) {
                    if(pos>currentPos+1) {
                        for(int k=currentPos+1;k<pos;k++) {
                            DateValueInterface intermediateValue = new DateValue(result.getDateValue(k).getDate(),result.getDateValue(k).getValue()+lastResultValue);
                            result.setDateValue(intermediateValue,k);
                        }
                    }
                    lastResultValue = dvNew.getValue();
                    DateValueInterface dvOld = result.getDateValue(pos);
                    DateValueInterface newValue = new DateValue(dvNew.getDate(),dvNew.getValue()+dvOld.getValue());
                    if(dvNew.getDate().equals(dvOld.getDate())) {
                        result.setDateValue(newValue,pos);
                        currentPos = pos;
                        lastValue = dvOld.getValue();
                        lastCalculated=pos;
                    }else {
                        if(pos==lastCalculated) {
                            result.insertDateValue(new DateValue(dvNew.getDate(),dvNew.getValue()+lastValue),pos+1);
                            currentPos = pos+1;
                        }else {
                            result.insertDateValue(newValue,pos+1);
                            currentPos = pos+1;
                            lastValue = dvOld.getValue();
                        }
                        lastCalculated=pos+1;
                    }
                }else {
                    result.insertDateValue(dvNew,0);
                    lastResultValue = dvNew.getValue();
                    currentPos=0;
                    lastValue = 0;
                    lastCalculated=0;
                }
            }
            if(result.size()>currentPos+1) {
                int size = result.size();
                for(int k=currentPos+1;k<size;k++) {
                    DateValueInterface intermediateValue = new DateValue(result.getDateValue(k).getDate(),result.getDateValue(k).getValue()+lastResultValue);
                    result.setDateValue(intermediateValue,k);
                }
            }
        }
        LOG.debug("getTotalStockValues end "+System.currentTimeMillis());
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

    public double getStockValue(Date date) {
        return getStockValue(null,date);
    }
    public double getStockValue(String broker, Date date) {
        LOG.debug("getStockValue "+date);
        double value=0;
        StockAccountStockEntry[] stocks = getStocks();
        for(int i=0;i<stocks.length;i++) {
            StockAccountStockEntry entry = stocks[i];
            if(broker==null || entry.getBroker().equals(broker)) {
                value += getStockValue(entry.getBroker(),entry.getStock(),date);
            }
        }
        return value;
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
