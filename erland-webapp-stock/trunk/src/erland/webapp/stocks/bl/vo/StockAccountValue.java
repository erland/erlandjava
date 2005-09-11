/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
package erland.webapp.stocks.bl.vo;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Represents a stock account value
 */
public class StockAccountValue {
    private double purchaseValue;
    private double value;
    private double noOfStocks;
    private double rate;
    private double increasedValue;
    private double totalStatistics;
    private double totalStatisticsThisYear;
    private Map statisticsPercent = new HashMap();
    private Map statisticsValue = new HashMap();
    private Map statisticsPurchase = new HashMap();
    private Map statisticsStockId = new HashMap();
    private Map statisticsStockBroker = new HashMap();
    private Map statisticsStockPercent = new HashMap();
    private Map statisticsStockPercentThisYear = new HashMap();
    private Map statisticsStockValue = new HashMap();
    private Map statisticsStockIncreasedValue = new HashMap();
    private Map statisticsStockPurchase = new HashMap();

    public double getTotalStatistics() {
        return totalStatistics;
    }

    public void setTotalStatistics(double totalStatistics) {
        this.totalStatistics = totalStatistics;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getIncreasedValue() {
        return increasedValue;
    }

    public void setIncreasedValue(double increasedValue) {
        this.increasedValue = increasedValue;
    }

    public double getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(double noOfStocks) {
        this.noOfStocks = noOfStocks;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int[] getYearStatistic() {
        int[] years = new int[statisticsPercent.size()];
        Iterator it = statisticsPercent.keySet().iterator();
        int i=0;
        while (it.hasNext()) {
            Long year = (Long)it.next();
            years[i++]=year.intValue();

        }
        return years;
    }

    public String[] getStockStatistic() {
        String[] stocks = new String[statisticsStockPercent.size()];
        Iterator it = statisticsStockPercent.keySet().iterator();
        int i=0;
        while (it.hasNext()) {
            String stock = (String)it.next();
            stocks[i++]=stock;

        }
        return stocks;
    }

    public void setYearStatistic(int year, double percent, double value, double purchase) {
        statisticsPercent.put(new Long(year),new Double(percent));
        statisticsValue.put(new Long(year),new Double(value));
        statisticsPurchase.put(new Long(year),new Double(purchase));
    }

    public void setStockStatistic(String brokerId, String stockId, String brokerName, String stockName, double percent, double value, double purchase,double increasedValue,double percentThisYear) {
        statisticsStockId.put(brokerId+stockId,stockName);
        statisticsStockBroker.put(brokerId+stockId,brokerName);
        statisticsStockPercent.put(brokerId+stockId,new Double(percent));
        statisticsStockPercentThisYear.put(brokerId+stockId,new Double(percentThisYear));
        statisticsStockValue.put(brokerId+stockId,new Double(value));
        statisticsStockIncreasedValue.put(brokerId+stockId,new Double(increasedValue));
        statisticsStockPurchase.put(brokerId+stockId,new Double(purchase));
    }

    public double getYearStatisticPercent(int year) {
        Double statistic = (Double) statisticsPercent.get(new Long(year));
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getYearStatisticValue(int year) {
        Double statistic = (Double) statisticsValue.get(new Long(year));
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getYearStatisticPurchase(int year) {
        Double statistic = (Double) statisticsPurchase.get(new Long(year));
        return statistic!=null?statistic.doubleValue():0;
    }
    public String getStockStatisticStockName(String id) {
        return (String) statisticsStockId.get(id);
    }
    public String getStockStatisticBroker(String id) {
        return (String) statisticsStockBroker.get(id);
    }
    public double getStockStatisticPercent(String id) {
        Double statistic = (Double) statisticsStockPercent.get(id);
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getStockStatisticPercentThisYear(String id) {
        Double statistic = (Double) statisticsStockPercentThisYear.get(id);
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getStockStatisticValue(String id) {
        Double statistic = (Double) statisticsStockValue.get(id);
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getStockStatisticIncreasedValue(String id) {
        Double statistic = (Double) statisticsStockIncreasedValue.get(id);
        return statistic!=null?statistic.doubleValue():0;
    }
    public double getStockStatisticPurchase(String id) {
        Double statistic = (Double) statisticsStockPurchase.get(id);
        return statistic!=null?statistic.doubleValue():0;
    }

    public double getTotalStatisticsThisYear() {
        return totalStatisticsThisYear;
    }

    public void setTotalStatisticsThisYear(double totalStatisticsThisYear) {
        this.totalStatisticsThisYear = totalStatisticsThisYear;
    }
}
