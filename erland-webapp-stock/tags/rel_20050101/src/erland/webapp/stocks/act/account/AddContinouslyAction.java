package erland.webapp.stocks.act.account;
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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.stocks.bl.entity.StockAccount;
import erland.webapp.stocks.bl.service.StockAccountManager;
import erland.webapp.stocks.fb.account.AccountEntryFB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

public class AddContinouslyAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        AccountEntryFB fb = (AccountEntryFB) actionForm;

        StockAccountManager accountManager = (StockAccountManager) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-stockaccountmanager");
        StockAccount account = accountManager.getAccount(httpServletRequest.getRemoteUser());

        account.addStockContinously(fb.getBroker(),fb.getStock(),fb.getPurchaseDate(),fb.getValue().doubleValue(),null);
        return actionMapping.findForward("success");
    }
}
