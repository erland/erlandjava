package erland.webapp.stocks.bl.logic.broker.sb;
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

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SBConnection implements BrokerConnectionInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(SBConnection.class);
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
    protected int getInstrumentType() {
        return 1;
    }
    protected int getRateColumn() {
        return 7;
    }
    public String getStock(Date startDate, String fondPrefix) {
        try {
            //URL url =new URL("http://p233/StockServlet?do=login&name=erland&password=dnalre");
            //URL url = new URL("http://www.google.com/search?q=erland");
            //URL url =new URL("http://www.stockholmsborsen.se/stocklist.asp?lang=swe&list=SSE43&group=Kursnoteringar&listName=O-listan, samtliga");
            //URL url = new URL("http://www.stockholmsborsen.se/getHistory.asp?isin=SE0000105116");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            URL url = new URL("http://www.se.omxgroup.com/slutkurser/excel.asp?"+
                    "InstrumentID="+fondPrefix+
                    "&InstrumentType="+getInstrumentType()+
                    "&From="+dateFormat.format(startDate)+
                    "&todate="+dateFormat.format(new Date()));
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);


            String s = System.getProperty("http.agent", "Unknown/1.0");
            String s1 = System.getProperty("os.arch", "") + " [" + Locale.getDefault().getLanguage() + "] " + System.getProperty("os.name", "") + " " + System.getProperty("os.version", "") + "; Sun";
            s = s + " (" + s1 + ")";
            LOG.debug(s);
            conn.setRequestProperty("User-Agent", s);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            BufferedReader inSB = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            /*
            String line;
            while((line = inSB.readLine())!=null) {
                System.out.println(line);
            }
            */
            String result = SBXMLEncoder.encodeStockData(inSB,getRateColumn());
            LOG.trace(result);
            inSB.close();
            return result;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BrokerStockEntry[] getAvailableStocks() {
        QueryFilter filter = new QueryFilter("allforbroker");
        filter.setAttribute("broker","sb_aktie");
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("stock-brokerstockentry").search(filter);
        return (BrokerStockEntry[]) Arrays.asList(entities).toArray(new BrokerStockEntry[0]);
    }

    public String getName() {
        return environment.getResources().getParameter("brokers.sb_aktie.name");
    }
    /*
    public static void main(String[] args) {
        XMLParser.setInstance(new SAXXMLParser());
        String result = new SBConnection().getStock(args[0]);
        System.out.println("result = " + result);
    }
    */
}
