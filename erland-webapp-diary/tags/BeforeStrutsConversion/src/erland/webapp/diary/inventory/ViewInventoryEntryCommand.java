package erland.webapp.diary.inventory;
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
import erland.webapp.common.CommandInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewInventoryEntryCommand implements CommandInterface, ViewInventoryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private InventoryEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        InventoryEntry template = (InventoryEntry)environment.getEntityFactory().create("diary-inventoryentry");
        if(id!=null) {
            template.setId(Integer.valueOf(id));
            entry = (InventoryEntry) environment.getEntityStorageFactory().getStorage("diary-inventoryentry").load(template);
        }
        return null;
    }

    public InventoryEntry getEntry() {
        return entry;
    }

    public InventoryEntryEvent[] getEvents() {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id",entry.getId());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary-inventoryentryevent").search(filter);
        InventoryEntryEvent[] events = new InventoryEntryEvent[entities.length];
        for (int i = 0; i < entities.length; i++) {
            events[i] = (InventoryEntryEvent) entities[i];
        }
        return events;
    }
}
