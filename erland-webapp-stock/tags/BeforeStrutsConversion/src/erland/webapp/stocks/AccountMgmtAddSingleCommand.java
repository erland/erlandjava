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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.stocks.StockAccount;

import javax.servlet.http.HttpServletRequest;

public class AccountMgmtAddSingleCommand implements CommandInterface {
    StockAccount account;
    private StockServletEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = (StockServletEnvironmentInterface) environment;
    }

    public String execute(HttpServletRequest request) {
        account = environment.getStockAccountFactory().getAccount(request);
        String broker = request.getParameter("broker");
        String stock = request.getParameter("stock");
        String number = request.getParameter("number");
        String value = request.getParameter("value");
        String purchaseDate = request.getParameter("purchasedate");
        if(broker!=null&&broker.length()>0 &&
                stock!=null&&stock.length()>0 &&
                purchaseDate!=null&&purchaseDate.length()>0) {

            double noOfStocks=0;
            if(number!=null&&number.length()>0) {
                noOfStocks = Double.valueOf(number).doubleValue();
            }
            if(noOfStocks!=0) {
                if(value!=null && value.length()>0) {
                    double price = Double.valueOf(value).doubleValue();
                    account.addStockSingleForPrice(broker,stock,purchaseDate,noOfStocks,price);
                }else {
                    account.addStockSingle(broker,stock,purchaseDate,noOfStocks);
                }
            }else if(value!=null && value.length()>0) {
                double valueOfStocks = Double.valueOf(value).doubleValue();
                account.addStockSingleForValue(broker,stock,purchaseDate,valueOfStocks);
            }

        }
        return null;
    }
}
