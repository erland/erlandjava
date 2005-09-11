package erland.webapp.stocks.act.account;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.stocks.fb.account.AccountEntryFB;
import erland.webapp.stocks.fb.account.AccountEntryPB;
import erland.webapp.stocks.fb.account.SelectAccountFB;
import erland.webapp.stocks.bl.service.StockAccountManager;
import erland.webapp.stocks.bl.entity.StockAccount;
import erland.webapp.stocks.bl.service.BrokerManagerInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransactionListInterface;
import erland.webapp.stocks.bl.logic.transaction.StockAccountTransaction;
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

public class SearchContinouslyAction extends SearchBaseAction {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        SelectAccountFB fb = (SelectAccountFB) actionForm;
        StockAccountManager accountManager = (StockAccountManager) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-stockaccountmanager");
        StockAccount account = accountManager.getAccount(httpServletRequest.getRemoteUser(),fb.getAccountId());

        StockAccountTransactionListInterface stockList = account.getPurchaseContinouslyEntries();

        AccountEntryPB[] pbCollection = createPB(stockList);
        httpServletRequest.setAttribute("accountEntriesPB",pbCollection);
        return actionMapping.findForward("success");
    }
}