package erland.webapp.homepage.act.account;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.homepage.entity.account.UserAccount;
import erland.webapp.homepage.fb.account.AccountFB;
import erland.webapp.homepage.fb.account.AccountPB;
import erland.webapp.homepage.logic.section.SectionHelper;
import erland.webapp.usermgmt.User;
import erland.webapp.usermgmt.UserApplicationRole;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class NewUserAccountAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-user");
        template.setUsername(fb.getUsername());
        User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").load(template);
        if (user != null) {
            saveErrors(request, Arrays.asList(new String[]{"homepage.useraccount.new.user-exists"}));
            return;
        }
        if (!fb.getPassword1().equals(fb.getPassword2())) {
            saveErrors(request, Arrays.asList(new String[]{"homepage.useraccount.new.passwords-dont-match"}));
            return;
        }
        template.setFirstName(fb.getFirstName());
        template.setLastName(fb.getLastName());
        template.setPassword(fb.getPassword1());
        getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").store(template);
        UserApplicationRole templateRole = (UserApplicationRole) getEnvironment().getEntityFactory().create("usermgmt-userapplicationrole");
        templateRole.setUsername(fb.getUsername());
        templateRole.setApplication("homepage");
        templateRole.setRole("user");
        getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userapplicationrole").store(templateRole);
        UserAccount templateAccount = (UserAccount) getEnvironment().getEntityFactory().create("homepage-useraccount");
        PropertyUtils.copyProperties(templateAccount,fb);
        templateAccount.setShowLogo(Boolean.TRUE);
        getEnvironment().getEntityStorageFactory().getStorage("homepage-useraccount").store(templateAccount);
        AccountPB pb = new AccountPB();
        PropertyUtils.copyProperties(pb, template);
        PropertyUtils.copyProperties(pb, templateAccount);
        request.getSession().setAttribute("accountPB", pb);
        request.getSession().setAttribute("skinsPB",new Object[0]);
    }
}
