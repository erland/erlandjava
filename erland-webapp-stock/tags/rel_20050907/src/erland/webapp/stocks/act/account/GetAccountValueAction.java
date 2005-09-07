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

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.stocks.bl.entity.StockAccount;
import erland.webapp.stocks.bl.entity.StockAccountValue;
import erland.webapp.stocks.bl.service.StockAccountManager;
import erland.webapp.stocks.bl.logic.account.StockAccountStockEntryListInterface;
import erland.webapp.stocks.fb.account.AccountDiagramFB;
import erland.webapp.stocks.fb.account.AccountValuePB;
import erland.webapp.stocks.fb.account.AccountStatisticPB;
import erland.webapp.stocks.fb.stock.SelectFB;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueDiagramHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

public class GetAccountValueAction extends Action {

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        AccountDiagramFB fb = (AccountDiagramFB) actionForm;
        StockAccountManager accountManager = (StockAccountManager) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-stockaccountmanager");
        StockAccount account = accountManager.getAccount(httpServletRequest.getRemoteUser());
        if(fb.getStartDate()!=null && fb.getEndDate()!=null) {
            StockAccountValue value;
            double purchaseValue = 0;
            if(fb.getBroker()!=null && fb.getStock()!=null) {
                value = account.getStockValue(fb.getBroker(),fb.getStock(),fb.getEndDate());
                purchaseValue = account.getPurchaseValue(fb.getBroker(),fb.getStock(),fb.getEndDate());
            }else if(fb.getBroker()!=null && fb.getStock()==null) {
                value = account.getStockValue(fb.getBroker(),fb.getEndDate());
                purchaseValue = account.getPurchaseValue(fb.getBroker(),fb.getEndDate());
            }else {
                value = account.getStockValue(fb.getEndDate());
                purchaseValue = account.getPurchaseValue(fb.getEndDate());
            }
            AccountValuePB pb = new AccountValuePB();
            pb.setDate(fb.getEndDate());
            pb.setValue(new Double(value.getValue()));
            pb.setPurchaseValue(new Double(purchaseValue));
            if(value.getNoOfStocks()>0) {
                pb.setNoOfStocks(new Double(value.getNoOfStocks()));
            }
            if(value.getRate()>0) {
                pb.setCurrentRate(new Double(value.getRate()));
            }
            pb.setTotalStatistic(new Double((value.getValue()-purchaseValue)*100.0/purchaseValue));
            int[] years = value.getStatisticYears();
            if(years.length>0) {
                AccountStatisticPB[] yearStats=new AccountStatisticPB[years.length];
                for (int i = 0; i < years.length; i++) {
                    yearStats[i] = new AccountStatisticPB();
                    yearStats[i].setYear(new Integer(years[i]));
                    yearStats[i].setValue(new Double(value.getStatistic(years[i])));
                }
            }
            httpServletRequest.setAttribute("accountValuePB",pb);
        }
        return actionMapping.findForward("success");
    }
}
