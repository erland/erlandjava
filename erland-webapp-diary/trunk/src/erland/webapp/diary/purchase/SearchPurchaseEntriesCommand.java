package erland.webapp.diary.purchase;
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
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.purchase.ViewPurchaseEntriesInterface;

import javax.servlet.http.HttpServletRequest;

public class SearchPurchaseEntriesCommand implements CommandInterface, ViewPurchaseEntriesInterface{
    private WebAppEnvironmentInterface environment;
    private PurchaseEntry[] entries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter("allforuser");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("purchaseentry").search(filter);
        entries = new PurchaseEntry[entities.length];
        for(int i=0;i<entities.length;i++) {
            entries[i] = (PurchaseEntry)entities[i];
        }
        return null;
    }

    public PurchaseEntry[] getEntries() {
        return entries;
    }
}
