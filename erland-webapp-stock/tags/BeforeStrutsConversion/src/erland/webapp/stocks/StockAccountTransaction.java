package erland.webapp.stocks;
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

import java.util.Date;

public class StockAccountTransaction {
    private String broker;
    private String stock;
    private Date date;
    private double value;
    private double number;

    public StockAccountTransaction(String broker, String stock, Date date, double number, double value) {
        this.broker = broker;
        this.stock = stock;
        this.date = date;
        this.number = number;
        this.value = value;
    }

    public String getBroker() {
        return broker;
    }

    public String getStock() {
        return stock;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public double getNumber() {
        return number;
    }
}
