package erland.webapp.stocks.fb.account;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.Serializable;

import erland.util.StringUtil;

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

public class AccountEntryFB extends ValidatorForm implements Serializable{
    private String broker;
    private String stock;
    private Double number;
    private Double value;
    private Date purchaseDate;

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

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getNumberDisplay() {
        return StringUtil.asString(number,"");
    }

    public void setNumberDisplay(String number) {
        this.number = StringUtil.asDouble(number,null);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getValueDisplay() {
        return StringUtil.asString(value,"");
    }

    public void setValueDisplay(String value) {
        this.value = StringUtil.asDouble(value,null);
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseDateDisplay() {
        return StringUtil.asString(purchaseDate,"");
    }

    public void setPurchaseDateDisplay(String purchaseDateDisplay) {
        this.purchaseDate = StringUtil.asDate(purchaseDateDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping,httpServletRequest);
        setPurchaseDate(new Date());
        setBroker(null);
        setStock(null);
        setValue(null);
        setNumber(null);
    }
}