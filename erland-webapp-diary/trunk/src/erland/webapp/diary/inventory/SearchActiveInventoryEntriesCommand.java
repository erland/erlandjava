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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class SearchActiveInventoryEntriesCommand implements CommandInterface, ViewInventoryEntriesInterface, ViewInventoryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private InventoryEntry[] entries;
    private InventoryEntry entry;

    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        try {
            QueryFilter filter = new QueryFilter("allforuser");
            String username = request.getParameter("user");
            if(username==null||username.length()==0) {
                User user = (User) request.getSession().getAttribute("user");
                username = user.getUsername();
            }
            filter.setAttribute("username",username);
            String dateString = request.getParameter("date");
            Date date=null;
            if(dateString!=null) {
                date = dateFormat.parse(dateString);
            }else {
                date = new Date();
            }
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("inventoryentry").search(filter);
            String idString=request.getParameter("id");
            Integer id=null;
            if(idString!=null && idString.length()>0) {
                id = Integer.valueOf(idString);
            }
            entry=null;

            List result = new ArrayList();
            for(int i=0;i<entities.length;i++) {
                InventoryEntryEvent[] events = getEvents((InventoryEntry)entities[i]);
                for (int j = 0; j < events.length; j++) {
                    InventoryEntryEvent event = events[j];
                    if(!event.getDate().after(date)) {
                        if(event.isActive()) {
                            result.add(entities[i]);
                            if(id!=null && ((InventoryEntry)entities[i]).getId().equals(id)) {
                                entry = (InventoryEntry)entities[i];
                            }
                        }
                        break;
                    }
                }
            }
            entries = (InventoryEntry[])result.toArray(new InventoryEntry[0]);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    public InventoryEntry[] getEntries() {
        return entries;
    }

    public InventoryEntryEvent[] getEvents(InventoryEntry entry) {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id",entry.getId());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("inventoryentryevent").search(filter);
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
        return getEvents(entry);
    }
}
