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
import erland.webapp.gallery.fb.account.SelectUserFB;
import erland.webapp.gallery.fb.gallery.SelectGalleryFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class ViewDefaultGalleryAction extends BaseAction {
    Integer gallery = null;
    String username = null;
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        username = getUsername(request,fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").load(template);
        gallery = null;
        if(account.getDefaultGallery()!=null && account.getDefaultGallery().intValue()>0) {
            gallery = account.getDefaultGallery();
        }else {
            saveErrors(request, Arrays.asList(new String[]{"gallery.account.no-defaultgallery"}));
        }
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        return request.getRemoteUser();
    }

    protected Map getDynamicParameters() {
        Map parameters = super.getDynamicParameters();
        if(gallery!=null) {
            if(parameters==null) {
                parameters = new HashMap();
            }
            parameters.put("gallery",gallery.toString());
            parameters.put("user",username);
        }
        return parameters;
    }
}
