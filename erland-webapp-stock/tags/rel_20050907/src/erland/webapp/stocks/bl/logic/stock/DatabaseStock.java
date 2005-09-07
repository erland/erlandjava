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
package erland.webapp.stocks.bl.logic.stock;

import java.io.InputStream;
import java.text.*;
import java.util.Map;

import erland.webapp.diagram.DateValue;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueSerie;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.stocks.bl.entity.Rate;
import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.util.XMLParserHandlerInterface;
import erland.util.XMLParser;

public class DatabaseStock implements StockInterface {
    private DateValueSerie rates;

    public DatabaseStock(WebAppEnvironmentInterface environment, String broker, String stock) {
        BrokerStockEntry template = (BrokerStockEntry) environment.getEntityFactory().create("stock-brokerstockentry");
        template.setBroker(broker);
        template.setCode(stock);
        BrokerStockEntry s = (BrokerStockEntry) environment.getEntityStorageFactory().getStorage("stock-brokerstockentry").load(template);

        QueryFilter filter = new QueryFilter("allforbrokerandstock");
        filter.setAttribute("broker",broker);
        filter.setAttribute("stock",stock);
        rates = new DateValueSerie(s.getName());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("stock-rate").search(filter);
        for (int i = 0; i < entities.length; i++) {
            Rate rate = (Rate) entities[i];
            rates.addDateValue(new DateValue(rate.getDate(),rate.getRate().doubleValue()));
        }
    }

    public DateValueSerieInterface getRates() {
        return rates;
    }
}
