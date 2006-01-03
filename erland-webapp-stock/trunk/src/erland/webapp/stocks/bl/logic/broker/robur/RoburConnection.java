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
            CookieHandler cookieHandler = new CookieHandler();

            // First we need to make sure we get a session cookie
            URL url = new URL("http://www.robur.se/RT/SaveFundInformation____62.aspx");
            HttpURLConnection.setFollowRedirects(false);
            URLConnection conn = url.openConnection();
            cookieHandler.setCookies(conn);

            // Make another call to get the VIEWSTATE parameter
            url = new URL("http://www.robur.se/RT/SaveFundInformation____62.aspx");
            HttpURLConnection.setFollowRedirects(false);
            conn = url.openConnection();
            cookieHandler.postCookies(conn);

            BufferedReader inRobur = new BufferedReader(new InputStreamReader(conn.getInputStream(),"ISO-8859-1"));
            StringBuffer viewStateResult = new StringBuffer(10000);
            String line = inRobur.readLine();
            while(line!=null) {
                viewStateResult.append(line);
                line = inRobur.readLine();
            }
            inRobur.close();

            String viewState = null;
            Pattern viewStatePattern = Pattern.compile("name=\\\"__VIEWSTATE\\\".*?value=\\\"(.*?)\\\"");
            Matcher viewStateMatcher = viewStatePattern.matcher(viewStateResult);
            if(viewStateMatcher.find()) {
                viewState = viewStateMatcher.group(1);
            }

            // At last we can retreive the data
            url = new URL("http://www.robur.se/RT/SaveFundInformation.aspx?id=62");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            cookieHandler.postCookies(conn);
            ClientHttpRequest realConnection = new ClientHttpRequest(conn);

            Date today = new Date();
            DateFormat formatYear = new SimpleDateFormat("yyyy");
            DateFormat formatMonth = new SimpleDateFormat("MM");
            DateFormat formatDay = new SimpleDateFormat("dd");
            realConnection.setParameter("__EVENTTARGET","");
            realConnection.setParameter("__EVENTARGUMENT","");
            if(viewState!=null) {
                realConnection.setParameter("__VIEWSTATE",viewState);
            }
            realConnection.setParameter("RoburFramework:Menu1:ddFunds:myDropDownBox","0");
            realConnection.setParameter("RoburFramework:SaveFundInformation:ddlFundList:myDropDownBox",fondPrefix);
            realConnection.setParameter("RoburFramework:SaveFundInformation:fromDate:YearDownList",formatYear.format(startDate));
            realConnection.setParameter("RoburFramework:SaveFundInformation:fromDate:MonthDownList",formatMonth.format(startDate));
            realConnection.setParameter("RoburFramework:SaveFundInformation:fromDate:DayDownList",formatDay.format(startDate));
            realConnection.setParameter("RoburFramework:SaveFundInformation:toDate:YearDownList",formatYear.format(today));
            realConnection.setParameter("RoburFramework:SaveFundInformation:toDate:MonthDownList",formatMonth.format(today));
            realConnection.setParameter("RoburFramework:SaveFundInformation:toDate:DayDownList",formatDay.format(today));
            realConnection.setParameter("RoburFramework:SaveFundInformation:btnSave2","Spara");
            inRobur = new BufferedReader(new InputStreamReader(realConnection.post(),"ISO-8859-1"));
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