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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchInventoryEntriesCommand implements CommandInterface, ViewInventoryEntriesInterface, ViewInventoryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private InventoryEntry[] entries;
    private InventoryEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter("allforuser");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary-inventoryentry").search(filter);
        entries = new InventoryEntry[entities.length];
        String idString=request.getParameter("id");
        Integer id=null;
        if(idString!=null && idString.length()>0) {
            id = Integer.valueOf(idString);
        }
        entry=null;
        for(int i=0;i<entities.length;i++) {
            entries[i] = (InventoryEntry)entities[i];
            if(id!=null && entries[i].getId().equals(id)) {
                entry = entries[i];
            }
        }
        return null;
    }

    public InventoryEntry[] getEntries() {
        return entries;
    }

    public InventoryEntryEvent[] getEvents(InventoryEntry entry) {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id",entry.getId());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary-inventoryentryevent").search(filter);
        InventoryEntryEvent[] events = new InventoryEntryEvent[entities.length];
        for (int i = 0; i < entities.length; i++) {
            events[i] = (InventoryEntryEvent) entities[i];
        }
        return events;
    }

    public InventoryEntry getEntry() {
        return entry;
    }

    public InventoryEntryEvent[] getEvents() {
        if(entry!=null) {
            return getEvents(entry);
        }else {
            return new InventoryEntryEvent[0];
        }
    }
}
