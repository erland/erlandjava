/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
package erland.webapp.stocks.act.account;

import erland.webapp.common.act.BaseAction;
import erland.webapp.stocks.fb.account.AccountFB;
import erland.webapp.stocks.bl.entity.Account;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAccountAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        Account account = (Account) getEnvironment().getEntityFactory().create("stock-account");
        account.setAccountId(fb.getAccountId());
        account.setUsername(request.getRemoteUser());
        account = (Account) getEnvironment().getEntityStorageFactory().getStorage("stock-account").load(account);
        if(account!=null) {
            fb.setAccountId(account.getAccountId());
            fb.setName(account.getName());
        }
    }
}
