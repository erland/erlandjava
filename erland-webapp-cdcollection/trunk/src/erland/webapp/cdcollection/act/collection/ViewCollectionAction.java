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

import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.cdcollection.fb.collection.CollectionFB;
import erland.webapp.common.act.BaseAction;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ViewCollectionAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CollectionFB fb = (CollectionFB) form;
        Collection template = (Collection) getEnvironment().getEntityFactory().create("cdcollection-collection");
        template.setId(fb.getId());
        Collection collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").load(template);
        if (collection == null) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.collection.do-not-exist"}));
            return;
        }
        if (request.getRemoteUser()!=null && !collection.getUsername().equals(request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.collection.unauthorized"}));
            return;
        }
        PropertyUtils.copyProperties(fb,collection);
    }
}
