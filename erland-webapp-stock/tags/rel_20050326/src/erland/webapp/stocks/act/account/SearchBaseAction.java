package erland.webapp.stocks.act.account;

import org.apache.struts.action.Action;
import erland.webapp.stocks.fb.account.AccountEntryPB;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;

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

public class SearchBaseAction extends Action {
    protected AccountEntryPB[] createPB(StockAccountTransactionListInterface stockList) {
        BrokerManagerInterface brokerManager = (BrokerManagerInterface) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-brokermanager");
        StockAccountTransaction[] transactions = stockList.toArray();
        AccountEntryPB[] pbCollection = new AccountEntryPB[transactions.length];
        for (int i = 0; i < pbCollection.length; i++) {
            pbCollection[i] = new AccountEntryPB();
            pbCollection[i].setBroker(transactions[i].getBroker());
            pbCollection[i].setStock(transactions[i].getStock());
            pbCollection[i].setBrokerDescription(brokerManager.getBrokerName(transactions[i].getBroker()));
            pbCollection[i].setStockDescription(brokerManager.getStockName(transactions[i].getBroker(),transactions[i].getStock()));
            pbCollection[i].setPurchaseDate(transactions[i].getDate());
            pbCollection[i].setNumber(transactions[i].getNumber()!=0?new Double(transactions[i].getNumber()):null);
            pbCollection[i].setValue((transactions[i].getValue()!=0&&transactions[i].getValue()!=-1)?new Double(transactions[i].getValue()):null);
        }
        return pbCollection;
    }
}