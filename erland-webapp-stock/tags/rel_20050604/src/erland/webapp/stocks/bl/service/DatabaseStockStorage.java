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
import erland.webapp.stocks.bl.logic.stock.DatabaseStock;
import erland.webapp.stocks.bl.entity.Rate;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.diagram.DateValueSerie;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueSerieType;
import erland.webapp.diagram.DateValueInterface;
import erland.util.StringUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.*;

public class DatabaseStockStorage extends StockStorage {
    protected Date getLastCachedDate(String broker, String stock) {
        QueryFilter filter = new QueryFilter("lastforbrokerandstock");
        filter.setAttribute("broker",broker);
        filter.setAttribute("stock",stock);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-rate").search(filter);
        if(entities.length>0) {
            return ((Rate)entities[0]).getDate();
        }
        Date startDate = new Date(0);
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-01");
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return startDate;
    }

    protected void storeStock(String broker, String stock, String xmlData) {
        if(StringUtil.asNull(xmlData)!=null) {
            StockInterface s = new XMLStock(new ByteArrayInputStream(xmlData.getBytes()));
            DateValueSerieInterface rates = s.getRates();
            for(Iterator it = rates.getSerie(DateValueSerieType.ALL);it.hasNext();) {
                DateValueInterface dv = (DateValueInterface) it.next();
                Rate rate = (Rate) getEnvironment().getEntityFactory().create("stock-rate");
                rate.setBroker(broker);
                rate.setStock(stock);
                rate.setDate(dv.getDate());
                rate.setRate(new Double(dv.getValue()));
                getEnvironment().getEntityStorageFactory().getStorage("stock-rate").store(rate);
            }
            BrokerStockEntry template = (BrokerStockEntry) getEnvironment().getEntityFactory().create("stock-brokerstockentry");
            template.setBroker(broker);
            template.setCode(stock);
            BrokerStockEntry entry = (BrokerStockEntry) getEnvironment().getEntityStorageFactory().getStorage("stock-brokerstockentry").load(template);
            if(StringUtil.asNull(rates.getName())!=null) {
                entry.setName(rates.getName());
            }
            entry.setCacheTime(new Date());
            getEnvironment().getEntityStorageFactory().getStorage("stock-brokerstockentry").store(entry);
        }
    }

    protected boolean isCacheUpdated(String broker, String stock) {
        BrokerStockEntry template = (BrokerStockEntry) getEnvironment().getEntityFactory().create("stock-brokerstockentry");
        template.setBroker(broker);
        template.setCode(stock);
        BrokerStockEntry s = (BrokerStockEntry) getEnvironment().getEntityStorageFactory().getStorage("stock-brokerstockentry").load(template);
        Date cacheDate = s.getCacheTime();
        return cacheDate!=null && cacheDate.getTime()>System.currentTimeMillis()-1000*60*60;
    }

    protected StockInterface getStockFromCache(String broker, String stock) {
        return new DatabaseStock(getEnvironment(),broker,stock);
    }
}
