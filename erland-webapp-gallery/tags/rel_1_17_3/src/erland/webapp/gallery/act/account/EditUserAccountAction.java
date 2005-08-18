package erland.webapp.gallery.act.account;

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
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.fb.account.AccountFB;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserAccountAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        UserAccount account = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
        PropertyUtils.copyProperties(account, fb);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").store(account);
        if(account.getStylesheet()!=null&&account.getStylesheet().length()>0) {
            request.getSession().setAttribute("stylesheetPB",account.getStylesheet());
        }else {
            request.getSession().removeAttribute("stylesheetPB");
        }
        if(account.getUsername().equals(request.getRemoteUser())) {
            boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
            String title = account.getTitle();
            if(useEnglish && StringUtil.asNull(account.getTitleEnglish())!=null) {
                title = account.getTitleEnglish();
            }
            request.getSession().setAttribute("titlePB",title);
        }
    }
}
