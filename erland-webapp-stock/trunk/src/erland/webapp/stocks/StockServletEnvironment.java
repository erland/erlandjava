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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.WebAppEnvironmentCustomizable;

public class StockServletEnvironment extends WebAppEnvironmentCustomizable implements StockServletEnvironmentInterface {
    private BrokerConnectionFactoryInterface brokerConnectionFactory;
    private StockAccountFactoryInterface stockAccountFactory;
    private BrokersInterface brokers;

    public StockServletEnvironment(WebAppEnvironmentInterface environment) {
        super(environment);
    }

    public BrokerConnectionFactoryInterface getBrokerConnectionFactory() {
        if(brokerConnectionFactory==null) {
            brokerConnectionFactory = new BrokerConnectionFactory(this);
        }
        return brokerConnectionFactory;
    }
    public StockAccountFactoryInterface getStockAccountFactory() {
        if(stockAccountFactory==null) {
            stockAccountFactory = new StockAccountFactory(this);
        }
        return stockAccountFactory;
    }
    public BrokersInterface getBrokers() {
        if(brokers==null) {
            brokers = new Brokers(this);
        }
        return brokers;
    }
}
