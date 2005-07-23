package erland.webapp.cdcollection.act.collection;

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

import erland.webapp.cdcollection.entity.account.UserAccount;
import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.cdcollection.fb.collection.CollectionFB;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveCollectionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CollectionFB fb = (CollectionFB) form;
        Collection collection = (Collection) getEnvironment().getEntityFactory().create("cdcollection-collection");
        String username = request.getRemoteUser();
        collection.setId(fb.getId());
        collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").load(collection);
        if (collection.getUsername().equals(username)) {
            getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").delete(collection);
            QueryFilter filter = new QueryFilter("allforcollection");
            filter.setAttribute("collection", collection.getId());
            getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collectionmedia").delete(filter);
            UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("cdcollection-useraccount");
            template.setUsername(username);
            UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-useraccount").load(template);
            if(account.getDefaultCollection().equals(fb.getId())) {
                account.setDefaultCollection(new Integer(0));
                getEnvironment().getEntityStorageFactory().getStorage("cdcollection-useraccount").store(account);
            }
        }
    }
}
