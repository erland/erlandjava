package erland.webapp.issuetracking.act.account;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.issuetracking.entity.account.UserAccount;
import erland.webapp.issuetracking.fb.account.AccountFB;
import erland.webapp.usermgmt.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserAccountAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        String username = getUsername(request, fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("issuetracking-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-useraccount").load(template);
        PropertyUtils.copyProperties(fb, account);
        PropertyUtils.copyProperties(fb, getUser(username));
    }

    protected String getUsername(HttpServletRequest request, AccountFB fb) {
        return request.getRemoteUser();
    }

    public User getUser(String username) {
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-userinfo");
        template.setUsername(username);
        User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
        return user;
    }
}
