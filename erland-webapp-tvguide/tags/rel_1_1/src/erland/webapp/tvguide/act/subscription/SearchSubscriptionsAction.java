package erland.webapp.tvguide.act.subscription;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.tvguide.entity.subscription.Subscription;
import erland.webapp.tvguide.fb.subscription.SubscriptionPB;
import erland.webapp.tvguide.fb.account.SelectUserFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SearchSubscriptionsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }
        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username",username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-subscription").search(filter);
        SubscriptionPB[] pb = new SubscriptionPB[entities.length];
        ActionForward viewLink = mapping.findForward("view-subscription-link");
        ActionForward updateLink = mapping.findForward("update-subscription-link");
        ActionForward removeLink = mapping.findForward("remove-subscription-link");
        Map parameters = new HashMap();
        parameters.put("user",username);
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new SubscriptionPB();
            try {
                PropertyUtils.copyProperties(pb[i], entities[i]);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            parameters.put("subscription",((Subscription)entities[i]).getId());
            if(viewLink!=null) {
                pb[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewLink.getPath(),parameters));
            }
            if(updateLink!=null) {
                pb[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateLink.getPath(),parameters));
            }
            if(removeLink!=null) {
                pb[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(removeLink.getPath(),parameters));
            }
        }
        request.setAttribute("subscriptionsPB", pb);
    }


    protected String getQueryFilter() {
        return "allforuser";
    }
}
