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

public class AccountYearStatisticPB extends BasePB implements Serializable{
    private Integer year;
    private Double value;
    private Double percent;
    private Double purchase;

    private static final NumberFormat percentFormat = new DecimalFormat("##0.0");
    private static final NumberFormat valueFormat = new DecimalFormat("#,###,###");

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getYearDisplay() {
        return StringUtil.asString(year,null);
    }

    public void setYearDisplay(String yearDisplay) {
        this.year = StringUtil.asInteger(yearDisplay,null);
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
}