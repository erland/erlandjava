package erland.webapp.stocks.bl.logic.broker.robur;
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

import erland.webapp.stocks.bl.logic.broker.BrokerConnectionInterface;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.http.CookieHandler;
import erland.webapp.common.http.ClientHttpRequest;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.Vector;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class RoburConnection implements BrokerConnectionInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public String getStock(Date startDate, String fondPrefix) {
        try {
            Date today = new Date();
            DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            URL url = new URL("http://www.robur.se/RoburPages/PeriodFundQuotes.aspx?fundId="+fondPrefix+
                    "&fromDate="+formatDate.format(startDate)+
                    "&toDate="+formatDate.format(today));
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            BufferedReader inRobur = new BufferedReader(new InputStreamReader(conn.getInputStream(),"ISO-8859-1"));
            String result = RoburXMLEncoder.encodeStockData(inRobur);
            inRobur.close();
            return result;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BrokerStockEntry[] getAvailableStocks() {
        QueryFilter filter = new QueryFilter("allforbroker");
        filter.setAttribute("broker","robur");
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("stock-brokerstockentry").search(filter);
        return (BrokerStockEntry[]) Arrays.asList(entities).toArray(new BrokerStockEntry[0]);
    }

    public String getName() {
        return environment.getResources().getParameter("brokers.robur.name");
    }
}