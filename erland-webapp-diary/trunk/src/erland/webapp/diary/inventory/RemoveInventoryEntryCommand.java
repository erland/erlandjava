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

import erland.webapp.common.*;

import javax.servlet.http.HttpServletRequest;

public class RemoveInventoryEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        InventoryEntry entry = (InventoryEntry)environment.getEntityFactory().create("inventoryentry");
        if(id!=null) {
            entry.setId(Integer.valueOf(id));
            QueryFilter filter = new QueryFilter("allforid");
            filter.setAttribute("id",entry.getId());
            EntityStorageInterface storage = environment.getEntityStorageFactory().getStorage("inventoryentryevent");
            EntityInterface[] entities = storage.search(filter);
            for (int i = 0; i < entities.length; i++) {
                EntityInterface entity = entities[i];
                storage.delete(entity);
            }
            environment.getEntityStorageFactory().getStorage("inventoryentry").delete(entry);
        }
        return null;
    }
}
