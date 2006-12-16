package erland.webapp.stocks.bl.logic.broker.sb;

import erland.webapp.stocks.bl.entity.BrokerStockEntry;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;

import java.util.Arrays;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class SBObligationConnection extends SBConnection {
    protected int getInstrumentType() {
        return 4;
    }
    public BrokerStockEntry[] getAvailableStocks() {
        QueryFilter filter = new QueryFilter("allforbroker");
        filter.setAttribute("broker","sb_obligation");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-brokerstockentry").search(filter);
        return (BrokerStockEntry[]) Arrays.asList(entities).toArray(new BrokerStockEntry[0]);
    }
    protected int getRateColumn() {
        return 2;
    }
    public String getName() {
        return getEnvironment().getResources().getParameter("brokers.sb_obligation.name");
    }

    protected String getBrokerId() {
        return "sb_obligation";
    }
}