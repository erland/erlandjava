package erland.webapp.stocks.bl.service;
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

import erland.webapp.stocks.bl.logic.broker.BrokerConnectionInterface;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.util.Vector;

public class BrokerManager implements BrokerManagerInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String[] getBrokers() {
        Vector availableBrokers = new Vector();
        availableBrokers.addElement("robur");
        availableBrokers.addElement("sb_aktie");
        availableBrokers.addElement("sb_obligation");
        return (String[]) availableBrokers.toArray(new String[0]);
    }
    public String getBrokerName(String brokerCode) {
        BrokerConnectionFactoryInterface factory = (BrokerConnectionFactoryInterface) environment.getServiceFactory().create("stock-brokerconnectionfactory");
        BrokerConnectionInterface brokerCls = factory.create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getName();
        }
        return "";
    }
    public BrokerStockEntry[] getStocks(String brokerCode) {
        BrokerConnectionFactoryInterface factory = (BrokerConnectionFactoryInterface) environment.getServiceFactory().create("stock-brokerconnectionfactory");
        BrokerConnectionInterface brokerCls = factory.create(brokerCode);
        if(brokerCls!=null) {
            return brokerCls.getAvailableStocks();
        }
        return new BrokerStockEntry[0];
    }
    public String getStockName(String brokerCode, String stockCode) {
        BrokerStockEntry[] stocks = getStocks(brokerCode);
        for (int i = 0; i < stocks.length; i++) {
            BrokerStockEntry stock = stocks[i];
            if(stock.getCode().equals(stockCode)) {
                return stock.getName();
            }
        }
        return "???";
    }
}
