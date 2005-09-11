package erland.webapp.stocks.fb.account;

import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.text.*;
import java.io.Serializable;

import erland.util.StringUtil;
import erland.webapp.common.fb.BasePB;

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

public class AccountValuePB extends BasePB implements Serializable{
    private Date date;
    private Double value;
    private Double increasedValue;
    private Double purchaseValue;
    private Double noOfStocks;
    private Double currentRate;
    private AccountYearStatisticPB[] statisticsPerYear;
    private AccountStockStatisticPB[] statisticsPerStock;
    private Double totalStatistic;
    private Double totalStatisticThisYear;

    private static final NumberFormat valueFormat = new DecimalFormat("#,###,###");
    private static final NumberFormat decimalFormat = new DecimalFormat("###,##0.##");
    private static final NumberFormat percentFormat = new DecimalFormat("0.0");

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return StringUtil.asString(date,null);
    }

    public void setDateDisplay(String dateDisplay) {
        this.date = StringUtil.asDate(dateDisplay,null);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getValueDisplay() {
        return value!=null?valueFormat.format(value.doubleValue()):null;
    }

    public void setValueDisplay(String valueDisplay) {
        this.value = StringUtil.asDouble(valueDisplay,null);
    }

    public Double getIncreasedValue() {
        return increasedValue;
    }

    public void setIncreasedValue(Double increasedValue) {
        this.increasedValue = increasedValue;
    }

    public String getIncreasedValueDisplay() {
        return increasedValue!=null?valueFormat.format(increasedValue.doubleValue()):null;
    }

    public void setIncreasedValueDisplay(String increasedValueDisplay) {
        this.increasedValue = StringUtil.asDouble(increasedValueDisplay,null);
    }

    public Double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(Double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public String getPurchaseValueDisplay() {
        return purchaseValue!=null?valueFormat.format(purchaseValue.doubleValue()):null;
    }

    public void setPurchaseValueDisplay(String purchaseValueDisplay) {
        this.purchaseValue = StringUtil.asDouble(purchaseValueDisplay,null);
    }

    public Double getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(Double noOfStocks) {
        this.noOfStocks = noOfStocks;
    }

    public String getNoOfStocksDisplay() {
        return noOfStocks!=null?decimalFormat.format(noOfStocks.doubleValue()):"";
    }

    public void setNoOfStocksDisplay(String noOfStocksDisplay) {
        this.noOfStocks = StringUtil.asDouble(noOfStocksDisplay,null);
    }

    public Double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(Double currentRate) {
        this.currentRate = currentRate;
    }

    public String getCurrentRateDisplay() {
        return currentRate!=null?decimalFormat.format(currentRate.doubleValue()):"";
    }

    public void setCurrentRateDisplay(String currentRateDisplay) {
        this.currentRate = new Double(currentRateDisplay);
    }

    public AccountYearStatisticPB[] getStatisticsPerYear() {
        return statisticsPerYear;
    }

    public void setStatisticsPerYear(AccountYearStatisticPB[] statisticsPerYear) {
        this.statisticsPerYear = statisticsPerYear;
    }

    public Double getTotalStatistic() {
        return totalStatistic;
    }

    public void setTotalStatistic(Double totalStatistic) {
        this.totalStatistic = totalStatistic;
    }
    public String getTotalStatisticDisplay() {
        return totalStatistic!=null?percentFormat.format(totalStatistic.doubleValue()):"";
    }

    public void setTotalStatisticDisplay(String totalStatisticDisplay) {
        this.totalStatistic = StringUtil.asDouble(totalStatisticDisplay,null);
    }

    public Double getTotalStatisticThisYear() {
        return totalStatisticThisYear;
    }

    public void setTotalStatisticThisYear(Double totalStatisticThisYear) {
        this.totalStatisticThisYear = totalStatisticThisYear;
    }
    public String getTotalStatisticThisYearDisplay() {
        return totalStatisticThisYear!=null?percentFormat.format(totalStatisticThisYear.doubleValue()):"";
    }

    public void setTotalStatisticThisYearDisplay(String totalStatisticThisYearDisplay) {
        this.totalStatisticThisYear = StringUtil.asDouble(totalStatisticThisYearDisplay,null);
    }

    public AccountStockStatisticPB[] getStatisticsPerStock() {
        return statisticsPerStock;
    }

    public void setStatisticsPerStock(AccountStockStatisticPB[] statisticsPerStock) {
        this.statisticsPerStock = statisticsPerStock;
    }

}