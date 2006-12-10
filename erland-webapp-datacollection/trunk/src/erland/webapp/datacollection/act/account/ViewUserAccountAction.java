package erland.webapp.datacollection.act.account;

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

import erland.webapp.datacollection.entity.account.UserAccount;
import erland.webapp.datacollection.entity.collection.Collection;
import erland.webapp.datacollection.fb.account.AccountFB;
import erland.webapp.datacollection.fb.collection.CollectionPB;
import erland.webapp.datacollection.logic.collection.CollectionHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.usermgmt.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserAccountAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        String username = getUsername(request,fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("datacollection-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("datacollection-useraccount").load(template);
        PropertyUtils.copyProperties(fb, account);
        PropertyUtils.copyProperties(fb, getUser(username));

        Collection[] entities = CollectionHelper.searchCollections(getEnvironment(),"datacollection-collection",username,null,"allforuser");
        CollectionPB[] pb = new CollectionPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new CollectionPB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.getSession().setAttribute("collectionsPB", pb);
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
