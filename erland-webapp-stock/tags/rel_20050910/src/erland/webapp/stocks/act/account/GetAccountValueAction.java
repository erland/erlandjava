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
import erland.webapp.stocks.fb.account.AccountDiagramFB;
import erland.webapp.stocks.fb.account.AccountValuePB;
import erland.webapp.stocks.fb.account.AccountYearStatisticPB;
import erland.webapp.stocks.fb.account.AccountStockStatisticPB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

public class GetAccountValueAction extends Action {

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        AccountDiagramFB fb = (AccountDiagramFB) actionForm;
        StockAccountManager accountManager = (StockAccountManager) WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-stockaccountmanager");
        StockAccount account = accountManager.getAccount(httpServletRequest.getRemoteUser());
        if(fb.getStartDate()!=null && fb.getEndDate()!=null) {
            StockAccountValue value;
            if(fb.getBroker()!=null && fb.getStock()!=null) {
                value = account.getStockValue(fb.getBroker(),fb.getStock(),fb.getEndDate());
            }else if(fb.getBroker()!=null && fb.getStock()==null) {
                value = account.getStockValue(fb.getBroker(),fb.getEndDate());
            }else {
                value = account.getStockValue(fb.getEndDate());
            }
            AccountValuePB pb = new AccountValuePB();
            pb.setDate(fb.getEndDate());
            pb.setValue(new Double(value.getValue()));
            pb.setPurchaseValue(new Double(value.getPurchaseValue()));
            pb.setIncreasedValue(new Double(value.getIncreasedValue()));
            if(value.getNoOfStocks()>0) {
                pb.setNoOfStocks(new Double(value.getNoOfStocks()));
            }
            if(value.getRate()>0) {
                pb.setCurrentRate(new Double(value.getRate()));
            }
            pb.setTotalStatistic(new Double(value.getTotalStatistics()));
            pb.setTotalStatisticThisYear(new Double(value.getTotalStatisticsThisYear()));
            int[] years = value.getYearStatistic();
            AccountYearStatisticPB[] yearStats=new AccountYearStatisticPB[years.length];
            for (int i = 0; i < years.length; i++) {
                yearStats[i] = new AccountYearStatisticPB();
                yearStats[i].setYear(new Integer(years[i]));
                yearStats[i].setPercent(new Double(value.getYearStatisticPercent(years[i])));
                yearStats[i].setValue(new Double(value.getYearStatisticValue(years[i])));
                yearStats[i].setPurchase(new Double(value.getYearStatisticPurchase(years[i])));
            }
            Arrays.sort(yearStats,new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((AccountYearStatisticPB)o1).getYear().compareTo(((AccountYearStatisticPB)o2).getYear());
                }
            });
            pb.setStatisticsPerYear(yearStats);

            String[] stockIds = value.getStockStatistic();
            AccountStockStatisticPB[] stockStats=new AccountStockStatisticPB[stockIds.length];
            for (int i = 0; i < stockIds.length; i++) {
                stockStats[i] = new AccountStockStatisticPB();
                stockStats[i].setStock(value.getStockStatisticStockName(stockIds[i]));
                stockStats[i].setBroker(value.getStockStatisticBroker(stockIds[i]));
                stockStats[i].setPercent(new Double(value.getStockStatisticPercent(stockIds[i])));
                stockStats[i].setPercentThisYear(new Double(value.getStockStatisticPercentThisYear(stockIds[i])));
                stockStats[i].setValue(new Double(value.getStockStatisticValue(stockIds[i])));
                stockStats[i].setIncreasedValue(new Double(value.getStockStatisticIncreasedValue(stockIds[i])));
                stockStats[i].setPurchase(new Double(value.getStockStatisticPurchase(stockIds[i])));
            }
            Arrays.sort(stockStats,new Comparator() {
                public int compare(Object o1, Object o2) {
                    int comp = ((AccountStockStatisticPB)o1).getBroker().compareTo(((AccountStockStatisticPB)o2).getBroker());
                    if(comp!=0) {
                        return comp;
                    }
                    return -((AccountStockStatisticPB)o1).getPercentThisYear().compareTo(((AccountStockStatisticPB)o2).getPercentThisYear());
                }
            });
            pb.setStatisticsPerStock(stockStats);
            httpServletRequest.setAttribute("accountValuePB",pb);
        }
        return actionMapping.findForward("success");
    }
}
