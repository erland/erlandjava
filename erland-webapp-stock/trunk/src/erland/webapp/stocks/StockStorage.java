package erland.webapp.stocks;

import erland.webapp.common.WebAppEnvironmentInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;

public class StockStorage implements StockStorageInterface {
    private static StockStorage me;
    private Map stocks = new HashMap();
    private StockServletEnvironmentInterface environment;

    private StockStorage(WebAppEnvironmentInterface environment) {
        this.environment = (StockServletEnvironmentInterface) environment;
    }
    public static StockStorageInterface getInstance(WebAppEnvironmentInterface environment) {
        if(me==null) {
            me = new StockStorage(environment);
        }
        return me;
    }

    public StockInterface getStock(String broker, String stock) {
        File file = new File(environment.getResources().getParameter("brokers."+broker+".cache.directory")+"/"+broker+stock+".xml");
        if(!file.exists() || file.lastModified()<System.currentTimeMillis()-1000*60*60) {
            try {
                FileWriter outFile = new FileWriter(file);
                BrokerConnectionInterface brokerCls = environment.getBrokerConnectionFactory().create(broker);
                String fondXML = brokerCls.getStock(stock);
                if(fondXML!=null) {
                    //outFile.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
                    outFile.write(fondXML);
                    outFile.flush();
                }
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        try {
            StockInterface s = (StockInterface) stocks.get(broker+stock);
            if(s==null) {
                FileInputStream fileInput = new FileInputStream(environment.getResources().getParameter("brokers."+broker+".cache.directory")+"/"+broker+stock+".xml");
                s = new Stock(fileInput);
                fileInput.close();
                stocks.put(broker+stock,s);
            }
            return s;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }
}
