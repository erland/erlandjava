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
import erland.webapp.diagram.DateValueSeriesContainerInterface;
import erland.webapp.diagram.DateValueSerie;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Vector;

public class StockAccountCommand implements CommandInterface, DateValueSeriesContainerInterface {
    private StockAccount account;
    private String broker;
    private String stock;
    private String purchaseDate;
    private String currentDate;
    private String purchaseValues;
    private String seriesincluded;
    private String stockNumbers;
    private String diffValues;
    private StockServletEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = (StockServletEnvironmentInterface)environment;
    }

    public String execute(HttpServletRequest request) {
        account = environment.getStockAccountFactory().getAccount(request);
        broker = request.getParameter("broker");
        stock = request.getParameter("stock");
        purchaseDate = request.getParameter("purchasedate");
        currentDate = request.getParameter("date");
        purchaseValues = request.getParameter("purchasevalues");
        seriesincluded = request.getParameter("seriesincluded");
        stockNumbers = request.getParameter("stocknumbers");
        diffValues = request.getParameter("diffvalues");
        return null;
    }

    public double getStockValue() {
        if(broker!=null && broker.length()>0 &&
                stock!=null && stock.length()>0) {
            return account.getStockValue(broker,stock,currentDate);
        }else {
            return account.getStockValue(broker,currentDate);
        }
    }

    public double getPurchaseValue() {
        if(broker!=null && broker.length()>0 &&
                stock!=null && stock.length()>0) {
            return account.getPurchaseValue(broker,stock,currentDate);
        }else {
            return account.getPurchaseValue(broker,currentDate);
        }
    }

    public StockAccountStockEntryListInterface getStocks() {
        return account.getStocks();
    }
    public Iterator getSeries() {

        if(broker!=null&& broker.length()>0 &&
                stock!=null && stock.length()>0) {
            Vector v = new Vector();
            DateValueSerie serie = account.getStockValues(broker,stock,purchaseDate,currentDate);
            v.addElement(serie);
            if(purchaseValues!=null) {
                serie = account.getPurchaseValues(broker,stock,purchaseDate,currentDate,purchaseValues);
                v.addElement(serie);
                if(diffValues!=null) {
                    serie = account.getPurchaseDiffValues(broker, stock, purchaseDate, currentDate, purchaseValues+diffValues);
                    v.addElement(serie);
                }
            }
            if(stockNumbers!=null) {
                serie = account.getStockNumbers(broker,stock,purchaseDate, currentDate, stockNumbers);
                v.addElement(serie);
                if(diffValues!=null) {
                    serie = account.getStockNumberDiffs(broker,stock,purchaseDate, currentDate, stockNumbers+" "+diffValues);
                    v.addElement(serie);
                }
            }
            return v.iterator();
        }else {
            if(seriesincluded==null||seriesincluded.length()==0||seriesincluded.equals("all")) {
                Vector res = account.getStockValues(broker,purchaseDate,currentDate);
                if(purchaseValues!=null) {
                    Vector res2 = account.getPurchaseValues(broker,purchaseDate, currentDate, purchaseValues);
                    for(int i=0;i<res2.size();i++) {
                        res.addElement(res2.elementAt(i));
                    }
                    if(diffValues!=null) {
                        res2 = account.getPurchaseDiffValues(broker,purchaseDate, currentDate, purchaseValues+" "+diffValues);
                        for(int i=0;i<res2.size();i++) {
                            res.addElement(res2.elementAt(i));
                        }
                    }
                }
                if(stockNumbers!=null) {
                    Vector res2 = account.getStockNumbers(broker,purchaseDate, currentDate, stockNumbers);
                    for(int i=0;i<res2.size();i++) {
                        res.addElement(res2.elementAt(i));
                    }
                    if(diffValues!=null) {
                        res2 = account.getStockNumberDiffs(broker,purchaseDate, currentDate, stockNumbers+" "+diffValues);
                        for(int i=0;i<res2.size();i++) {
                            res.addElement(res2.elementAt(i));
                        }
                    }
                }
                return res.iterator();
            }else if(seriesincluded.equals("total")) {
                Vector res = new Vector();
                res.add(account.getTotalStockValues(broker,purchaseDate,currentDate));
                if(purchaseValues!=null) {
                    res.add(account.getTotalPurchaseValues(broker,purchaseDate,currentDate,purchaseValues));
                }
                return res.iterator();
            }else {
                return null;
            }
        }
    }
}
