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

import erland.webapp.common.EntityReadUpdateInterface;
import erland.webapp.common.BaseEntity;

import java.util.Date;

public class Transaction extends BaseEntity implements EntityReadUpdateInterface {
    private Integer accountId;
    private Integer type;
    private String brokerId;
    private String stockId;
    private Date date;
    private Double price;
    private Double value;
    private static final Double ZERO = new Double(0);
    private static final Double NOTUSED = new Double(-1);

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void preReadUpdate() {

    }
    public void postReadUpdate() {
        switch(type.intValue()) {
            case 1:
            case 2:
                setPrice(getValue());
                setValue(NOTUSED);
                break;
            case 3:
            case 4:
                setPrice(ZERO);
                break;
            case 5:
            case 6:
                Double price = getPrice();
                setPrice(getValue());
                setValue(price);
            default:
                break;
        }
    }
}
