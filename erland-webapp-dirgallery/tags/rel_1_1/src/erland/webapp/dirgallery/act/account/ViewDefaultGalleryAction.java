package erland.webapp.dirgallery.act.account;

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
import erland.webapp.dirgallery.entity.account.UserAccount;
import erland.webapp.dirgallery.fb.account.SelectUserFB;
import erland.webapp.dirgallery.fb.account.AccountPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ViewDefaultGalleryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = getUsername(request, fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("dirgallery-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-useraccount").load(template);
        request.setAttribute("user", username);
        if (account == null) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.useraccount.user-doesnt-exist"}));
            return;
        }
        AccountPB pb = new AccountPB();
        PropertyUtils.copyProperties(pb,account);
        request.getSession().setAttribute("accountPB",pb);
        if (account.getDefaultGallery() == null || account.getDefaultGallery().intValue() == 0) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.useraccount.no-default-gallery"}));
            return;
        }
        request.setAttribute("gallery", account.getDefaultGallery());
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        return request.getRemoteUser();
    }
}
