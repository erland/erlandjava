package erland.webapp.cdcollection.act.account;

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
import erland.webapp.cdcollection.fb.account.SelectUserFB;
import erland.webapp.common.act.BaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewDefaultCollectionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = getUsername(request,fb);
        setUsername(request,username);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("cdcollection-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-useraccount").load(template);

        Integer collection = null;
        if(account.getDefaultCollection()!=null && account.getDefaultCollection().intValue()>0) {
            collection = account.getDefaultCollection();
        }else {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.account.no-defaultcollection"}));
        }
        setCollection(request,collection);
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        return request.getRemoteUser();
    }

    protected Map getDynamicParameters(HttpServletRequest request) {
        Map parameters = super.getDynamicParameters(request);
        if(getCollection(request)!=null) {
            if(parameters==null) {
                parameters = new HashMap();
            }
            parameters.put("collection",getCollection(request));
            parameters.put("user",getUsername(request));
        }
        return parameters;
    }

    public String getUsername(HttpServletRequest request) {
        return (String) request.getAttribute(ViewDefaultCollectionAction.class+"-username");
    }
    public void setUsername(HttpServletRequest request, String username) {
        request.setAttribute(ViewDefaultCollectionAction.class+"-username",username);
    }
    public Integer getCollection(HttpServletRequest request) {
        return (Integer) request.getAttribute(ViewDefaultCollectionAction.class+"-collection");
    }
    public void setCollection(HttpServletRequest request, Integer collection) {
        request.setAttribute(ViewDefaultCollectionAction.class+"-collection",collection);
    }
}
