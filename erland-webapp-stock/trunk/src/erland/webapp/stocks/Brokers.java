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

import java.util.Iterator;
import java.util.Vector;

public class Brokers implements BrokersInterface {
    private StockServletEnvironmentInterface environment;

    public Brokers(StockServletEnvironmentInterface environment) {
        this.environment = environment;
    }
    public Iterator getBrokers() {
        Vector availableBrokers = new Vector();
        availableBrokers.addElement("robur");
        availableBrokers.addElement("sb");
        return availableBrokers.iterator();
    }
    public String getBrokerName(String brokerCode) {
        BrokerConnectionInterface brokerCls = environment.getBrokerConnectionFactory().create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getName();
        }
        return "";
    }
    public Iterator getStocks(String brokerCode) {
        BrokerConnectionInterface brokerCls = environment.getBrokerConnectionFactory().create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getAvailableStocks();
        }
        return new Vector().iterator();
    }
    public String getStockName(String brokerCode, String stockCode) {
        Iterator it = getStocks(brokerCode);
        while(it.hasNext()) {
            BrokersStockEntry stock = (BrokersStockEntry) it.next();
            if(stock.getCode().equals(stockCode)) {
                return stock.getName();
            }
        }
        return "???";
    }
}
