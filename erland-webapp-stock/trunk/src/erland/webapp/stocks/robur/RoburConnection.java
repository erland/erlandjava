package erland.webapp.stocks.robur;

import erland.webapp.stocks.BrokerConnectionInterface;
import erland.webapp.stocks.BrokersStockEntry;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.Iterator;

public class RoburConnection implements BrokerConnectionInterface {
	private final static Vector availableStocks = new Vector();
    private WebAppEnvironmentInterface environment;

    static {
		availableStocks.removeAllElements();
        availableStocks.addElement(new BrokersStockEntry("AKT","AKTIA CAPITAL"));
        availableStocks.addElement(new BrokersStockEntry("AFP","AKTIEFOND PENSION"));
        availableStocks.addElement(new BrokersStockEntry("AL1","ALLEMANSFOND I"));
        availableStocks.addElement(new BrokersStockEntry("AL2","ALLEMANSFOND II"));
        availableStocks.addElement(new BrokersStockEntry("RA3","ALLEMANSFOND III"));
        availableStocks.addElement(new BrokersStockEntry("RA4","ALLEMANSFOND IV"));
        availableStocks.addElement(new BrokersStockEntry("RA5","ALLEMANSFOND V"));
        availableStocks.addElement(new BrokersStockEntry("AME","AMERICAN EQUITY FUND"));
        availableStocks.addElement(new BrokersStockEntry("SAM","AMERIKAFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("BSF","BOSPARFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("COM","COMMUNICATION EQUITY FUND"));
        availableStocks.addElement(new BrokersStockEntry("CON","CONTURA"));
        availableStocks.addElement(new BrokersStockEntry("EGM","ETIKFOND GLOBAL MEGA"));
        availableStocks.addElement(new BrokersStockEntry("ETM","ETIKFOND SVERIGE MEGA"));
        availableStocks.addElement(new BrokersStockEntry("EUB","EURO BOND FUND"));
        availableStocks.addElement(new BrokersStockEntry("EUS","EURO SAFE FUND"));
        availableStocks.addElement(new BrokersStockEntry("EUM","EUROPAFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SEU","EUROPAFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("EUR","EUROPEAN EQUITY FUND"));
        availableStocks.addElement(new BrokersStockEntry("EXA","EXACTA MARS"));
        availableStocks.addElement(new BrokersStockEntry("EXS","EXACTA SEPT"));
        availableStocks.addElement(new BrokersStockEntry("EXP","EXPORTFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("FIN","FINANSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("GVF","GÅVOFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("BON","GLOBAL BOND FUND"));
        availableStocks.addElement(new BrokersStockEntry("GLO","GLOBAL EQUITY FUND"));
        availableStocks.addElement(new BrokersStockEntry("HOC","HOCKEYFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("IPA","IP AKTIEFOND"));
        availableStocks.addElement(new BrokersStockEntry("IPR","IP RÄNTEFOND"));
        availableStocks.addElement(new BrokersStockEntry("JAP","JAPANFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("JMB","JM BOFOND"));
        availableStocks.addElement(new BrokersStockEntry("KPI","KAPITALINVEST"));
        availableStocks.addElement(new BrokersStockEntry("SCO","KOMMUNIKATIONSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("MED","MEDICA"));
        availableStocks.addElement(new BrokersStockEntry("MJO","MILJÖFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("FMX","MIX INDEXFOND"));
        availableStocks.addElement(new BrokersStockEntry("SIM","MIXFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("MFP","MIXFOND PENSION"));
        availableStocks.addElement(new BrokersStockEntry("MIX","MIXFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SNO","NORDENFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("NOR","NORDIC EQUITY FUND"));
        availableStocks.addElement(new BrokersStockEntry("NOM","NORRMIX"));
        availableStocks.addElement(new BrokersStockEntry("SIO","OBLIGATIONSFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("OBL","OBLIGATIONSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("OST","ÖSTEUROPAFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("PAC","PACIFICFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SIP","PENNINGMARKNADSFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("PMF","PENNINGMARKNADSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("PRI","PRIVATISERINGSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("RFE","RÄNTEFOND EUROPA"));
        availableStocks.addElement(new BrokersStockEntry("RFP","RÄNTEFOND PENSION"));
        availableStocks.addElement(new BrokersStockEntry("RFO","RÄNTEFOND SVERIGE"));
        availableStocks.addElement(new BrokersStockEntry("RVF","RÅVARUFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("REA","REALINVEST"));
        availableStocks.addElement(new BrokersStockEntry("RRF","REALRÄNTEFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("RYS","RYSSLANDSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SKO","SKOGSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SME","SMÅBOLAG EUROPA"));
        availableStocks.addElement(new BrokersStockEntry("SMA","SMÅBOLAG NORDEN"));
        availableStocks.addElement(new BrokersStockEntry("SMS","SMÅBOLAG SVERIGE"));
        availableStocks.addElement(new BrokersStockEntry("SPF","STATSPAPPERSFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SVX","STATSSKULDVÄXELFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("IKA","SVENSKA KYRKANS AKTIEFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("TAL","SVENSKA KYRKANS MILJÖFOND TALENTEN"));
        availableStocks.addElement(new BrokersStockEntry("SKM","SVENSKA KYRKANS MIXFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SKR","SVENSKA KYRKANS RÄNTEFOND"));
        availableStocks.addElement(new BrokersStockEntry("IKR","SVENSKA KYRKANS RÄNTEFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SKV","SVENSKA KYRKANS VÄRDEPAPPERSFOND"));
        availableStocks.addElement(new BrokersStockEntry("SWL","SVENSKA LIKVIDITETSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SSM","SVENSKA LIKVIDITETSFONDEN MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SWE","SVENSKA OBLIGATIONSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SBM","SVENSKA OBLIGATIONSFONDEN MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SIA","SVERIGEFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("SVF","SVERIGEFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("FTI","TILLVÄXTFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("SIU","UTLANDSFOND MEGA"));
        availableStocks.addElement(new BrokersStockEntry("UTL","UTLANDSFONDEN"));
        availableStocks.addElement(new BrokersStockEntry("VAS","VASALOPPSFONDEN"));
	}

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

    public Iterator getAvailableStocks() {
        return availableStocks.iterator();
    }

    public String getName() {
        return environment.getResources().getParameter("brokers.robur.name");
    }
}
