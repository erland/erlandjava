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

        ProgramHelper.loadPrograms(getEnvironment(),false);

        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }

        pb.setPrograms(getPrograms(username,fb,subscription));
        request.setAttribute("subscriptionPB",pb);
    }

    protected ProgramPB[] getPrograms(String username, SelectSubscriptionFB fb, Subscription subscription) {

        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username",username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-favorite").search(filter);
        if(entities.length>0) {
            Collection channels = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                Favorite favorite = (Favorite) entities[i];
                channels.add(favorite.getChannel());
            }

            filter = new QueryFilter("allinlist");
            filter.setAttribute("channel",channels);
            entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").search(filter);
            Map channelMap = new HashMap();
            for (int i = 0; i < entities.length; i++) {
                Channel channel = (Channel) entities[i];
                channelMap.put(channel.getId(),channel);
            }
            Date date = new Date();
            filter = new QueryFilter("allinlistaftertime");
            filter.setAttribute("channel",channels);
            filter.setAttribute("time",date);
            entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-program").search(filter);
            Collection programCollection = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                Program entity = (Program) entities[i];
                ProgramPB program = new ProgramPB();
                if (entity.getName().matches(subscription.getPattern())) {
                    try {
                        PropertyUtils.copyProperties(program,entity);
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e) {
                    } catch (NoSuchMethodException e) {
                    }
                    Channel ch = (Channel) channelMap.get(entity.getChannel());
                    program.setChannelName(ch.getName());
                    program.setChannelLogo(ch.getLogo());
                    program.setChannelLink(ch.getLink());

                    if(program.getStart().before(date)) {
                        program.setStarted(Boolean.TRUE);
                    }else {
                        program.setStarted(Boolean.FALSE);
                    }
                    if (sameDay(program.getStart(), date)) {
                        program.setStartSameDay(Boolean.TRUE);
                    } else {
                        program.setStartSameDay(Boolean.FALSE);
                    }
                    programCollection.add(program);
                }
            }
            return (ProgramPB[]) programCollection.toArray(new ProgramPB[0]);
        }
        return new ProgramPB[0];
    }
    private boolean sameDay(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }
}