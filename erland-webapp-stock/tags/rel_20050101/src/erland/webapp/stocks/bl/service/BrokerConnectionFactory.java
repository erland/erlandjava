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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.bl.logic.broker.BrokerConnectionInterface;

public class BrokerConnectionFactory implements BrokerConnectionFactoryInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public BrokerConnectionInterface create(String broker) {
        BrokerConnectionInterface brokerCls = null;
        String clsName = environment.getResources().getParameter("brokers."+broker+".connection.class");
        if(clsName!=null && clsName.length()>0) {
            try {
                brokerCls = (BrokerConnectionInterface) (Class.forName(clsName).newInstance());
                brokerCls.init(environment);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        return brokerCls;
    }

}
