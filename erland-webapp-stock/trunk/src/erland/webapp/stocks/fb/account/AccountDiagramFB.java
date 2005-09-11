package erland.webapp.stocks.fb.account;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;

import erland.util.StringUtil;
import erland.webapp.common.fb.BaseFB;

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

public class AccountDiagramFB extends BaseFB implements Serializable{
    private Integer accountId;
    private String broker;
    private String stock;
    private Date startDate;
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartDateDisplay() {
        return StringUtil.asString(startDate,null);
    }

    public void setStartDateDisplay(String dateDisplay) {
        this.startDate = StringUtil.asDate(dateDisplay,null);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateDisplay() {
        return StringUtil.asString(endDate,null);
    }

    public void setEndDateDisplay(String purchaseDateDisplay) {
        this.endDate = StringUtil.asDate(purchaseDateDisplay,null);
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        setBroker(null);
        setStock(null);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE,1);
        cal.set(Calendar.MONTH,0);
        setStartDate(cal.getTime());
        setEndDate(new Date());
        setAccountId(null);
    }
}