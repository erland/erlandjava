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
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCollectionAction extends BaseAction {
    private final static String ID = EditCollectionAction.class+"-id";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CollectionFB fb = (CollectionFB) form;
        setId(request,fb.getId());

        String username = request.getRemoteUser();
        Collection collection = (Collection) getEnvironment().getEntityFactory().create("cdcollection-collection");
        PropertyUtils.copyProperties(collection, fb);
        collection.setUsername(username);

        getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").store(collection);
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        if(getId(request)!=null) {
            return mapping.findForward("success-edit");
        }else {
            return mapping.findForward("success-new");
        }
    }

    public Integer getId(HttpServletRequest request) {
        return (Integer) request.getAttribute(ID);
    }
    public void setId(HttpServletRequest request, Integer id) {
        request.setAttribute(ID,id);
    }
}
