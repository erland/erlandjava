package erland.webapp.datacollection.logic.entry;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.entry.data.DataHistory;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.entry.EntryHistory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;

/*
 * Copyright (C) 2006 Erland Isaksson (erland_i@hotmail.com)
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
public class EntryHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(EntryHelper.class);

    public static void storeHistory(WebAppEnvironmentInterface environment, Integer id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Entry templateEntry = (Entry) environment.getEntityFactory().create("datacollection-entry");
        templateEntry.setId(id);
        Entry entry = (Entry) environment.getEntityStorageFactory().getStorage("datacollection-entry").load(templateEntry);

        QueryFilter filter = new QueryFilter("allforentry");
        filter.setAttribute("entry",entry.getId());
        EntityInterface[] dataEntries = environment.getEntityStorageFactory().getStorage("datacollection-data").search(filter);

        EntryHistory entryHistory = (EntryHistory) environment.getEntityFactory().create("datacollection-entryhistory");
        PropertyUtils.copyProperties(entryHistory,entry);
        environment.getEntityStorageFactory().getStorage("datacollection-entryhistory").store(entryHistory);
        LOG.info("Creating history entry "+entryHistory.getHistoryId());
        for (int i = 0; i < dataEntries.length; i++) {
            Data data = (Data) dataEntries[i];

            DataHistory dataHistory = (DataHistory) environment.getEntityFactory().create("datacollection-datahistory");
            PropertyUtils.copyProperties(dataHistory,data);
            dataHistory.setEntryHistoryId(entryHistory.getHistoryId());
            environment.getEntityStorageFactory().getStorage("datacollection-datahistory").store(dataHistory);
            LOG.info("Creating history data "+dataHistory.getHistoryId());
        }
    }
}
