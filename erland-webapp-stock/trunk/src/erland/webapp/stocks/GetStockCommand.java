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

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;

public class GetStockCommand implements CommandInterface, DateValueSeriesContainerInterface {
    private Vector stocks;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        stocks = new Vector();
        String fondListaString = request.getParameter("stocks");
        String broker = request.getParameter("broker");
        StringTokenizer fondLista = new StringTokenizer(fondListaString,",");
        while(broker!=null && fondLista.hasMoreTokens()) {
            String current = fondLista.nextToken().trim();
            stocks.addElement(StockStorage.getInstance(environment).getStock(broker, current).getRates());
        }
        return null;
    }

    public Iterator getSeries() {
        return stocks.iterator();
    }
}
