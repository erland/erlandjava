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
import erland.webapp.usermgmt.User;
import erland.webapp.diary.purchase.ViewPurchaseEntryInterface;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class EditPurchaseEntryCommand implements CommandInterface, ViewPurchaseEntryInterface {
    private PurchaseEntry entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String store = request.getParameter("store");
            String description = request.getParameter("description");
            String dateString = request.getParameter("date");
            Date date = dateFormat.parse(dateString);
            String priceString = request.getParameter("price");
            Double price = Double.valueOf(priceString);
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            entry = (PurchaseEntry)environment.getEntityFactory().create("diary-purchaseentry");
            if(id!=null && id.length()>0) {
                entry.setId(Integer.valueOf(id));
            }
            entry.setStore(store);
            entry.setDescription(description);
            entry.setPrice(price);
            entry.setDate(date);
            entry.setUsername(username);
            environment.getEntityStorageFactory().getStorage("diary-purchaseentry").store(entry);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    public PurchaseEntry getEntry() {
        return entry;
    }
}
