package erland.webapp.stocks.act.stock;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.stocks.fb.stock.StockPB;
import erland.webapp.stocks.fb.stock.BrokerPB;
import erland.webapp.stocks.fb.stock.SelectFB;
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

public class SearchBrokersAndStocksAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        BrokerManagerInterface brokers = (BrokerManagerInterface) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-brokermanager");
        String[] brokerIds = brokers.getBrokers();
        BrokerPB[] brokersFB = new BrokerPB[brokerIds.length];
        for (int i = 0; i < brokerIds.length; i++) {
            String brokerId = brokerIds[i];
            BrokerStockEntry[] stocks = brokers.getStocks(brokerId);
            StockPB[] stocksFB = new StockPB[stocks.length];
            for (int j = 0; j < stocksFB.length; j++) {
                stocksFB[j] = new StockPB();
                stocksFB[j].setStock(stocks[j].getCode());
                stocksFB[j].setDescription(stocks[j].getName());
            }
            String name = brokers.getBrokerName(brokerId);
            brokersFB[i] = new BrokerPB();
            brokersFB[i].setBroker(brokerId);
            brokersFB[i].setDescription(name);
            brokersFB[i].setStocks(stocksFB);
        }
        httpServletRequest.setAttribute("brokersPB",brokersFB);
        return actionMapping.findForward("success");
    }
}