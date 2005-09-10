/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
package erland.webapp.stocks.bl.service;

import erland.webapp.stocks.bl.logic.storage.StockStorage;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.bl.logic.stock.XMLStock;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class FileStockStorage extends StockStorage {
    protected Date getLastCachedDate(String broker, String stock) {
        Date startDate = new Date(0);
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-01");
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return startDate;
    }

    protected String getCacheFileName(String broker, String stock) {
        return getEnvironment().getConfigurableResources().getParameter("brokers."+broker+".cache")+"/"+broker+stock+".xml";
    }
    protected void storeStock(String broker, String stock, String xmlData) {
        try {
            FileWriter outFile = new FileWriter(getCacheFileName(broker,stock));
            outFile.write(xmlData);
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected boolean isCacheUpdated(String broker, String stock) {
        File file = new File(getCacheFileName(broker,stock));
        return file.exists() && file.lastModified()>System.currentTimeMillis()-1000*60*60;
    }

    protected StockInterface getStockFromCache(String broker, String stock) {
        StockInterface s = null;
        try {
            FileInputStream fileInput = new FileInputStream(getCacheFileName(broker,stock));
            s = new XMLStock(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return s;
    }
}
