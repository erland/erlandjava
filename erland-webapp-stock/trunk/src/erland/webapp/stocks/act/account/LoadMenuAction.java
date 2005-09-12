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
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.stocks.fb.account.MenuItemPB;
import erland.webapp.stocks.bl.entity.Account;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadMenuAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getRemoteUser();
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username",username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("stock-account").search(filter);
        MenuItemPB[] items = new MenuItemPB[entities.length];
        for (int i = 0; i < items.length; i++) {
            Account entity = (Account) entities[i];
            items[i] = new MenuItemPB();
            MenuItemPB item = items[i];

            item.setId(entity.getAccountId());
            item.setName(entity.getName());
            item.setPath(mapping.findForward("account").getPath());
            item.setAccountId(entity.getAccountId());
            item.setChilds(createSubMenuForAccount(mapping,entity.getAccountId()));
        }
        request.getSession().setAttribute("menuItemsPB",items);
    }
    private MenuItemPB[] createSubMenuForAccount(ActionMapping mapping, Integer accountId) {
        MenuItemPB[] item = new MenuItemPB[5];
        int i=0;
        item[i] = new MenuItemPB();
        item[i].setId(new Integer(i));
        item[i].setNameKey("stock.menu.editaccount");
        item[i].setAccountId(accountId);
        item[i].setPath(mapping.findForward("editaccount").getPath());
        i++;

        item[i] = new MenuItemPB();
        item[i].setId(new Integer(i));
        item[i].setNameKey("stock.menu.continously");
        item[i].setAccountId(accountId);
        item[i].setPath(mapping.findForward("continously").getPath());
        i++;

        item[i] = new MenuItemPB();
        item[i].setId(new Integer(i));
        item[i].setNameKey("stock.menu.purchaseonce");
        item[i].setAccountId(accountId);
        item[i].setPath(mapping.findForward("purchaseonce").getPath());
        i++;

        item[i] = new MenuItemPB();
        item[i].setId(new Integer(i));
        item[i].setNameKey("stock.menu.permanent");
        item[i].setAccountId(accountId);
        item[i].setPath(mapping.findForward("permanent").getPath());
        i++;

        item[i] = new MenuItemPB();
        item[i].setId(new Integer(i));
        item[i].setNameKey("stock.menu.diagrams");
        item[i].setAccountId(accountId);
        item[i].setPath(mapping.findForward("diagram").getPath());

        return item;
    }
}
