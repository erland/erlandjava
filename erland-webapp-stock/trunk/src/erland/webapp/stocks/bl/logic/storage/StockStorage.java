package erland.webapp.stocks.bl.logic.storage;
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

import erland.webapp.common.ServiceInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.bl.logic.broker.BrokerConnectionInterface;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.bl.service.BrokerConnectionFactoryInterface;
import erland.webapp.stocks.bl.service.StockStorageInterface;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class StockStorage implements StockStorageInterface, ServiceInterface {
    private Map stocks = new HashMap();
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    protected abstract Date getLastCachedDate(String broker, String stock);
    protected abstract void storeStock(String broker, String stock, String xmlData);
    protected abstract boolean isCacheUpdated(String broker, String stock);
    protected abstract StockInterface getStockFromCache(String broker, String stock);

    public StockInterface getStock(String broker, String stock) {
        if(!isCacheUpdated(broker,stock)) {
            BrokerConnectionFactoryInterface factory = (BrokerConnectionFactoryInterface) environment.getServiceFactory().create("stock-brokerconnectionfactory");
            BrokerConnectionInterface brokerCls = factory.create(broker);
            String fondXML = brokerCls.getStock(getLastCachedDate(broker,stock),stock);
            if(fondXML!=null) {
                //outFile.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
                storeStock(broker,stock,fondXML);
            }
        }
        StockInterface s = (StockInterface) stocks.get(broker+stock);
        if(s==null) {
            s = getStockFromCache(broker, stock);
            if(s!=null) {
                stocks.put(broker+stock,s);
            }
        }
        return s;
    }
}
