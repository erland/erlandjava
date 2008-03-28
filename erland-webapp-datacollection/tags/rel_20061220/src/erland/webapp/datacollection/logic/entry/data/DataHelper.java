package erland.webapp.datacollection.logic.entry.data;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.logic.entry.EntryHelper;

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
public class DataHelper {
    public static void storeHistory(WebAppEnvironmentInterface environment, Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Data template = (Data) environment.getEntityFactory().create("datacollection-data");
        template.setId(id);
        Data data = (Data) environment.getEntityStorageFactory().getStorage("datacollection-data").load(template);
        EntryHelper.storeHistory(environment,data.getEntryId());
    }
}
