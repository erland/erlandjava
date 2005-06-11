package erland.webapp.tvguide.act.account;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.tvguide.entity.account.UserAccount;
import erland.webapp.tvguide.fb.account.AccountPB;
import erland.webapp.usermgmt.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class SearchUserAccountsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        QueryFilter filter = new QueryFilter(getQueryFilter());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-useraccount").search(filter);
        UserAccount[] accounts = new UserAccount[entities.length];
        for (int i = 0; i < entities.length; i++) {
            accounts[i] = (UserAccount) entities[i];
        }

        AccountPB[] accountsPB = new AccountPB[accounts.length];
        try {
            for (int i = 0; i < accounts.length; i++) {
                UserAccount account = accounts[i];
                User user = getUser(account);
                accountsPB[i] = new AccountPB();
                PropertyUtils.copyProperties(accountsPB[i], account);
                PropertyUtils.copyProperties(accountsPB[i], user);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        request.setAttribute("accountsPB", accountsPB);
        request.getSession().setAttribute("accountsPB", accountsPB);
    }

    private User getUser(UserAccount account) {
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-userinfo");
        template.setUsername(account.getUsername());
        User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
        return user;
    }

    protected String getQueryFilter() {
        return "all";
    }
}
