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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.fb.account.AccountPB;
import erland.webapp.usermgmt.User;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class SearchUserAccountsAction extends BaseAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(SearchUserAccountsAction.class);
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        QueryFilter filter = new QueryFilter(getQueryFilter());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").search(filter);
        UserAccount[] accounts = new UserAccount[entities.length];
        for (int i = 0; i < entities.length; i++) {
            accounts[i] = (UserAccount) entities[i];
        }

        AccountPB[] accountsPB = new AccountPB[accounts.length];
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        try {
            for (int i = 0; i < accounts.length; i++) {
                UserAccount account = accounts[i];
                User user = getUser(account);
                accountsPB[i] = new AccountPB();
                PropertyUtils.copyProperties(accountsPB[i], account);
                PropertyUtils.copyProperties(accountsPB[i], user);
                if(useEnglish && StringUtil.asNull(account.getWelcomeTextEnglish())!=null) {
                    accountsPB[i].setWelcomeText(account.getWelcomeTextEnglish());
                }
                if(useEnglish && StringUtil.asNull(account.getDescriptionEnglish())!=null) {
                    accountsPB[i].setDescription(account.getDescriptionEnglish());
                }
            }
        } catch (IllegalAccessException e) {
            LOG.error("Error while copying properties",e);
        } catch (InvocationTargetException e) {
            LOG.error("Error while copying properties",e);
        } catch (NoSuchMethodException e) {
            LOG.error("Error while copying properties",e);
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
