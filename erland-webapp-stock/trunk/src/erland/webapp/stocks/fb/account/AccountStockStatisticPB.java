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
package erland.webapp.stocks.fb.account;

import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.text.*;
import java.io.Serializable;

import erland.util.StringUtil;
import erland.webapp.common.fb.BasePB;

public class AccountStockStatisticPB extends BasePB implements Serializable{
    private String broker;
    private String stock;
    private Double value;
    private Double percent;
    private Double percentThisYear;
    private Double purchase;
    private Double increasedValue;

    private static final NumberFormat percentFormat = new DecimalFormat("##0.0");
    private static final NumberFormat valueFormat = new DecimalFormat("#,###,###");

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getValueDisplay() {
        return value!=null?valueFormat.format(value):"";
    }

    public void setValueDisplay(String valueDisplay) {
        this.value = StringUtil.asDouble(valueDisplay,null);
    }
    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getPercentDisplay() {
        return percent!=null?percentFormat.format(percent):"";
    }

    public void setPercentDisplay(String percentDisplay) {
        this.percent = StringUtil.asDouble(percentDisplay,null);
    }

    public Double getPercentThisYear() {
        return percentThisYear;
    }

    public void setPercentThisYear(Double percentThisYear) {
        this.percentThisYear = percentThisYear;
    }

    public String getPercentThisYearDisplay() {
        return percentThisYear!=null?percentFormat.format(percentThisYear):"";
    }

    public void setPercentThisYearDisplay(String percentThisYearDisplay) {
        this.percentThisYear = StringUtil.asDouble(percentThisYearDisplay,null);
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public String getPurchaseDisplay() {
        return purchase!=null?valueFormat.format(purchase):"";
    }

    public void setPurchaseDisplay(String purchaseDisplay) {
        this.purchase = StringUtil.asDouble(purchaseDisplay,null);
    }
    public Double getIncreasedValue() {
         return purchase;
     }

     public void setIncreasedValue(Double increasedValue) {
         this.increasedValue = increasedValue;
     }

     public String getIncreasedValueDisplay() {
         return increasedValue!=null?valueFormat.format(increasedValue):"";
     }

     public void setIncreasedValueDisplay(String increasedValueDisplay) {
         this.increasedValue = StringUtil.asDouble(increasedValueDisplay,null);
     }

}