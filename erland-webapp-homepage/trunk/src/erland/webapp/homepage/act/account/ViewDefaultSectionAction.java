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

import erland.webapp.homepage.fb.account.SelectUserFB;
import erland.webapp.homepage.fb.account.AccountPB;
import erland.webapp.homepage.entity.account.UserAccount;
import erland.webapp.common.act.BaseAction;
import erland.webapp.usermgmt.User;
import erland.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ViewDefaultSectionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = getUsername(request, fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("homepage-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("homepage-useraccount").load(template);
        request.setAttribute("user", username);
        if (account == null) {
            saveErrors(request, Arrays.asList(new String[]{"homepage.useraccount.user-doesnt-exist"}));
            return;
        }
        AccountPB pb = new AccountPB();
        PropertyUtils.copyProperties(pb,account);
        PropertyUtils.copyProperties(pb,getUser(username));
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        if(useEnglish && StringUtil.asNull(pb.getHeaderTextEnglish())!=null) {
            pb.setHeaderText(pb.getHeaderTextEnglish());
        }
        if(useEnglish && StringUtil.asNull(pb.getLogoEnglish())!=null) {
            pb.setLogo(pb.getLogoEnglish());
        }
        if(useEnglish && StringUtil.asNull(pb.getDescriptionEnglish())!=null) {
            pb.setDescription(pb.getDescriptionEnglish());
        }
        if(useEnglish && StringUtil.asNull(pb.getWelcomeTextEnglish())!=null) {
            pb.setWelcomeText(pb.getWelcomeTextEnglish());
        }
        if(useEnglish && StringUtil.asNull(pb.getTitleEnglish())!=null) {
            pb.setTitle(pb.getTitleEnglish());
        }
        request.getSession().setAttribute("accountPB",pb);
        request.getSession().setAttribute("titlePB",pb.getTitle());
        if (account.getDefaultSection() == null || account.getDefaultSection().intValue() == 0) {
            saveErrors(request, Arrays.asList(new String[]{"homepage.useraccount.no-default-section"}));
            return;
        }
        request.setAttribute("section", account.getDefaultSection());
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        return request.getRemoteUser();
    }
    public User getUser(String username) {
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-userinfo");
        template.setUsername(username);
        User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
        return user;
    }
}