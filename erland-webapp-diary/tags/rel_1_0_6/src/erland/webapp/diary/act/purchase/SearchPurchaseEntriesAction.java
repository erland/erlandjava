package erland.webapp.diary.act.purchase;

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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.purchase.PurchaseEntry;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.purchase.PurchaseEntryPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchPurchaseEntriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = request.getRemoteUser();
        if(username==null) {
            username=fb.getUser();
        }
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchaseentry").search(filter);
        PurchaseEntryPB[] pb = new PurchaseEntryPB[entities.length];
        ActionForward updateForward = mapping.findForward("update-entry-link");
        ActionForward deleteForward = mapping.findForward("delete-entry-link");
        Map parameters = new HashMap();
        parameters.put("user",username);
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new PurchaseEntryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
            parameters.put("id",((PurchaseEntry)entities[i]).getId());
            if(updateForward!=null) {
                pb[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateForward.getPath(),parameters));
            }
            if(deleteForward!=null) {
                pb[i].setDeleteLink(ServletParameterHelper.replaceDynamicParameters(deleteForward.getPath(),parameters));
            }
        }
        request.setAttribute("purchaseEntriesPB",pb);
    }
}
