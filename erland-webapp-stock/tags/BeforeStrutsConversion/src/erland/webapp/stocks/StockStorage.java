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
        File file = new File(environment.getConfigurableResources().getParameter("brokers."+broker+".cache")+"/"+broker+stock+".xml");
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
                FileInputStream fileInput = new FileInputStream(environment.getConfigurableResources().getParameter("brokers."+broker+".cache")+"/"+broker+stock+".xml");
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
