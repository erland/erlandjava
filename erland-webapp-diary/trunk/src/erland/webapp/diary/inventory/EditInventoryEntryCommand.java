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
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.text.*;
import java.util.Date;

public class EditInventoryEntryCommand implements CommandInterface, ViewInventoryEntryInterface {
    private InventoryEntry entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String image = request.getParameter("image");
            String largeImage = request.getParameter("largeimage");
            String link = request.getParameter("link");
            String type = request.getParameter("type");
            String size = request.getParameter("size");
            String gallery = request.getParameter("gallery");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            entry = (InventoryEntry)environment.getEntityFactory().create("inventoryentry");
            if(id!=null && id.length()>0) {
                entry.setId(Integer.valueOf(id));
            }
            if(gallery!=null && gallery.length()>0) {
                entry.setGallery(Integer.valueOf(gallery));
            }
            entry.setType(Integer.valueOf(type));
            entry.setName(name);
            entry.setDescription(description);
            entry.setImage(image);
            entry.setLargeImage(largeImage);
            entry.setLink(link);
            entry.setUsername(username);
            environment.getEntityStorageFactory().getStorage("inventoryentry").store(entry);
            if(id==null || id.length()==0) {
                String event = request.getParameter("eventdescription");
                String dateString = request.getParameter("date");
                Date date = dateFormat.parse(dateString);
                InventoryEntryEvent entryEvent = (InventoryEntryEvent)environment.getEntityFactory().create("inventoryentryevent");
                entryEvent.setId(entry.getId());
                entryEvent.setDate(date);
                entryEvent.setSize(Double.valueOf(size));
                entryEvent.setDescription(Integer.valueOf(event));
                environment.getEntityStorageFactory().getStorage("inventoryentryevent").store(entryEvent);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    public InventoryEntry getEntry() {
        return entry;
    }

    public InventoryEntryEvent[] getEvents() {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id",entry.getId());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("inventoryentryevent").search(filter);
        InventoryEntryEvent[] events = new InventoryEntryEvent[entities.length];
        for (int i = 0; i < entities.length; i++) {
            events[i] = (InventoryEntryEvent) entities[i];
        }
        return events;
    }
}
