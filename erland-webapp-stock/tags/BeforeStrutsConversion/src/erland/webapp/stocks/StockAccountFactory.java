package erland.webapp.stocks;
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

import erland.webapp.usermgmt.User;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class StockAccountFactory implements StockAccountFactoryInterface {
    private WebAppEnvironmentInterface environment;

    public StockAccountFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public StockAccount getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        StockAccount account = (StockAccount) session.getAttribute("stockaccount");
        if(account==null) {
            User user = (User) session.getAttribute("user");
            if(user!=null && user.isValid()) {
                account = (StockAccount)environment.getEntityFactory().create("stock-stockaccount");
                account.init(user.getUsername(), StockStorage.getInstance(environment));
                session.setAttribute("stockaccount",account);
            }
        }
        return account;
    }
}
