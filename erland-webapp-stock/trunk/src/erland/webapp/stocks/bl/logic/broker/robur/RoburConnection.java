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
import erland.webapp.stocks.bl.logic.broker.BrokersStockEntry;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.Iterator;

public class RoburConnection implements BrokerConnectionInterface {
    private WebAppEnvironmentInterface environment;
	private final static BrokersStockEntry[] availableStocks = new BrokersStockEntry[] {
        new BrokersStockEntry("AKT","AKTIA CAPITAL"),
        new BrokersStockEntry("AFP","AKTIEFOND PENSION"),
        new BrokersStockEntry("AL1","ALLEMANSFOND I"),
        new BrokersStockEntry("AL2","ALLEMANSFOND II"),
        new BrokersStockEntry("RA3","ALLEMANSFOND III"),
        new BrokersStockEntry("RA4","ALLEMANSFOND IV"),
        new BrokersStockEntry("RA5","ALLEMANSFOND V"),
        new BrokersStockEntry("AME","AMERICAN EQUITY FUND"),
        new BrokersStockEntry("SAM","AMERIKAFONDEN"),
        new BrokersStockEntry("BSF","BOSPARFONDEN"),
        new BrokersStockEntry("COM","COMMUNICATION EQUITY FUND"),
        new BrokersStockEntry("CON","CONTURA"),
        new BrokersStockEntry("EGM","ETIKFOND GLOBAL MEGA"),
        new BrokersStockEntry("ETM","ETIKFOND SVERIGE MEGA"),
        new BrokersStockEntry("EUB","EURO BOND FUND"),
        new BrokersStockEntry("EUS","EURO SAFE FUND"),
        new BrokersStockEntry("EUM","EUROPAFOND MEGA"),
        new BrokersStockEntry("SEU","EUROPAFONDEN"),
        new BrokersStockEntry("EUR","EUROPEAN EQUITY FUND"),
        new BrokersStockEntry("EXA","EXACTA MARS"),
        new BrokersStockEntry("EXS","EXACTA SEPT"),
        new BrokersStockEntry("EXP","EXPORTFONDEN"),
        new BrokersStockEntry("FIN","FINANSFONDEN"),
        new BrokersStockEntry("GVF","GÅVOFONDEN"),
        new BrokersStockEntry("BON","GLOBAL BOND FUND"),
        new BrokersStockEntry("GLO","GLOBAL EQUITY FUND"),
        new BrokersStockEntry("HOC","HOCKEYFONDEN"),
        new BrokersStockEntry("IPA","IP AKTIEFOND"),
        new BrokersStockEntry("IPR","IP RÄNTEFOND"),
        new BrokersStockEntry("JAP","JAPANFONDEN"),
        new BrokersStockEntry("JMB","JM BOFOND"),
        new BrokersStockEntry("KPI","KAPITALINVEST"),
        new BrokersStockEntry("SCO","KOMMUNIKATIONSFONDEN"),
        new BrokersStockEntry("MED","MEDICA"),
        new BrokersStockEntry("MJO","MILJÖFONDEN"),
        new BrokersStockEntry("FMX","MIX INDEXFOND"),
        new BrokersStockEntry("SIM","MIXFOND MEGA"),
        new BrokersStockEntry("MFP","MIXFOND PENSION"),
        new BrokersStockEntry("MIX","MIXFONDEN"),
        new BrokersStockEntry("SNO","NORDENFONDEN"),
        new BrokersStockEntry("NOR","NORDIC EQUITY FUND"),
        new BrokersStockEntry("NOM","NORRMIX"),
        new BrokersStockEntry("SIO","OBLIGATIONSFOND MEGA"),
        new BrokersStockEntry("OBL","OBLIGATIONSFONDEN"),
        new BrokersStockEntry("OST","ÖSTEUROPAFONDEN"),
        new BrokersStockEntry("PAC","PACIFICFONDEN"),
        new BrokersStockEntry("SIP","PENNINGMARKNADSFOND MEGA"),
        new BrokersStockEntry("PMF","PENNINGMARKNADSFONDEN"),
        new BrokersStockEntry("PRI","PRIVATISERINGSFONDEN"),
        new BrokersStockEntry("RFE","RÄNTEFOND EUROPA"),
        new BrokersStockEntry("RFP","RÄNTEFOND PENSION"),
        new BrokersStockEntry("RFO","RÄNTEFOND SVERIGE"),
        new BrokersStockEntry("RVF","RÅVARUFONDEN"),
        new BrokersStockEntry("REA","REALINVEST"),
        new BrokersStockEntry("RRF","REALRÄNTEFONDEN"),
        new BrokersStockEntry("RYS","RYSSLANDSFONDEN"),
        new BrokersStockEntry("SKO","SKOGSFONDEN"),
        new BrokersStockEntry("SME","SMÅBOLAG EUROPA"),
        new BrokersStockEntry("SMA","SMÅBOLAG NORDEN"),
        new BrokersStockEntry("SMS","SMÅBOLAG SVERIGE"),
        new BrokersStockEntry("SPF","STATSPAPPERSFOND MEGA"),
        new BrokersStockEntry("SVX","STATSSKULDVÄXELFOND MEGA"),
        new BrokersStockEntry("IKA","SVENSKA KYRKANS AKTIEFOND MEGA"),
        new BrokersStockEntry("TAL","SVENSKA KYRKANS MILJÖFOND TALENTEN"),
        new BrokersStockEntry("SKM","SVENSKA KYRKANS MIXFOND MEGA"),
        new BrokersStockEntry("SKR","SVENSKA KYRKANS RÄNTEFOND"),
        new BrokersStockEntry("IKR","SVENSKA KYRKANS RÄNTEFOND MEGA"),
        new BrokersStockEntry("SKV","SVENSKA KYRKANS VÄRDEPAPPERSFOND"),
        new BrokersStockEntry("SWL","SVENSKA LIKVIDITETSFONDEN"),
        new BrokersStockEntry("SSM","SVENSKA LIKVIDITETSFONDEN MEGA"),
        new BrokersStockEntry("SWE","SVENSKA OBLIGATIONSFONDEN"),
        new BrokersStockEntry("SBM","SVENSKA OBLIGATIONSFONDEN MEGA"),
        new BrokersStockEntry("SIA","SVERIGEFOND MEGA"),
        new BrokersStockEntry("SVF","SVERIGEFONDEN"),
        new BrokersStockEntry("FTI","TILLVÄXTFONDEN"),
        new BrokersStockEntry("SIU","UTLANDSFOND MEGA"),
        new BrokersStockEntry("UTL","UTLANDSFONDEN"),
        new BrokersStockEntry("VAS","VASALOPPSFONDEN")
    };

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public String getStock(String fondPrefix) {
        try {
            URL url = new URL("http://www.robur.se/getFundInExcelFormat.asp");
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            DataOutputStream outRobur = new DataOutputStream(conn.getOutputStream());
            outRobur.writeBytes(
                    "strFundID="+fondPrefix+
                    "&fryear=1980"+
                    "&frmonth=01"+
                    "&frday=01"+
                    "&toyear=2005"+
                    "&tomonth=01"+
                    "&today=01");
            outRobur.flush();
            outRobur.close();
            BufferedReader inRobur = new BufferedReader(new InputStreamReader(conn.getInputStream(),"ISO-8859-1"));
            String result = RoburXMLEncoder.encodeStockData(inRobur);
            inRobur.close();
            return result;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BrokersStockEntry[] getAvailableStocks() {
        return availableStocks;
    }

    public String getName() {
        return environment.getResources().getParameter("brokers.robur.name");
    }
}
