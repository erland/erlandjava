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
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.favorite.Favorite;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.entity.subscription.Subscription;
import erland.webapp.tvguide.fb.program.ProgramPB;
import erland.webapp.tvguide.fb.subscription.SelectSubscriptionFB;
import erland.webapp.tvguide.fb.subscription.SubscriptionPB;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ViewSubscriptionInfoAction extends BaseAction{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ViewSubscriptionInfoAction.class);

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectSubscriptionFB fb = (SelectSubscriptionFB) form;
        Subscription template = (Subscription) getEnvironment().getEntityFactory().create("tvguide-subscription");
        template.setId(fb.getSubscription());
        Subscription subscription = (Subscription) getEnvironment().getEntityStorageFactory().getStorage("tvguide-subscription").load(template);
        SubscriptionPB pb = new SubscriptionPB();
        PropertyUtils.copyProperties(pb, subscription);

        Map parameters = new HashMap();
        parameters.put("subscription",subscription.getId());
        ActionForward forward = mapping.findForward("update-subscription-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("remove-subscription-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }

        pb.setPrograms(ProgramHelper.getSubscriptionPrograms(getEnvironment(),username,subscription));
        request.setAttribute("subscriptionPB",pb);
    }
}