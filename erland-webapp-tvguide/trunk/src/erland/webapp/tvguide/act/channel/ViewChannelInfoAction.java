package erland.webapp.tvguide.act.channel;

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

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.fb.BasePB;
import erland.webapp.common.act.BaseAction;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.fb.channel.ChannelPB;
import erland.webapp.tvguide.fb.channel.SelectChannelFB;
import erland.webapp.tvguide.fb.program.ProgramPB;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.lang.reflect.InvocationTargetException;

public class ViewChannelInfoAction extends BaseAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ViewChannelInfoAction.class);

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectChannelFB fb = (SelectChannelFB) form;
        Channel template = (Channel) getEnvironment().getEntityFactory().create("tvguide-channel");
        template.setId(fb.getChannel());
        Channel channel = (Channel) getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").load(template);
        ChannelPB pb = new ChannelPB();
        PropertyUtils.copyProperties(pb, channel);

        Map parameters = new HashMap();
        parameters.put("channel",channel.getId());
        ActionForward forward = mapping.findForward("update-channel-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("remove-channel-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }

        parameters.put("user",username);
        Date date = new Date();
        if(fb.getDate()!=null) {
            date = fb.getDate();
        }
        parameters.put("date",StringUtil.asString(date,null));

        Calendar cal = Calendar.getInstance();

        forward = mapping.findForward("current-view-link");
        if(forward!=null) {
            pb.setCurrentDate(date);
            pb.setCurrentLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("next-view-link");
        if(forward!=null) {
            cal.setTime(date);
            cal.add(Calendar.DATE,1);
            pb.setNextDate(cal.getTime());
            parameters.put("date",StringUtil.asString(cal.getTime(),null));
            pb.setNextLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("prev-view-link");
        if(forward!=null) {
            cal.setTime(date);
            cal.add(Calendar.DATE,-1);
            pb.setPrevDate(cal.getTime());
            parameters.put("date",StringUtil.asString(cal.getTime(),null));
            pb.setPrevLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        ProgramHelper.loadPrograms(getEnvironment(),false);

        forward = mapping.findForward("add-subscription-link");

        pb.setPrograms(getPrograms(username,fb,channel,forward));
        request.setAttribute("channelPB",pb);
    }

    protected ProgramPB[] getPrograms(String username, SelectChannelFB fb,Channel channel,ActionForward subscriptionForward) {
        QueryFilter filter = new QueryFilter("allforchannelanddateaftertime");
        filter.setAttribute("channel",channel.getId());
        Date date = fb.getDate();
        Date currentDate = new Date();
        if(fb.getDate()==null) {
            date = currentDate;
        }
        if(!sameDay(currentDate,date) && currentDate.before(date)) {
            currentDate = date;
        }
        filter.setAttribute("time",date);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-program").search(filter);
        ProgramPB[] programsPB = new ProgramPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            Program entity = (Program) entities[i];
            programsPB[i] = new ProgramPB();
            try {
                PropertyUtils.copyProperties(programsPB[i],entity);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            if(programsPB[i].getStart().before(currentDate)) {
                programsPB[i].setStarted(Boolean.TRUE);
            }else {
                programsPB[i].setStarted(Boolean.FALSE);
            }
            if(subscriptionForward!=null) {
                Map parameters = new HashMap();
                parameters.put("programName",programsPB[i].getName());
                programsPB[i].setNewSubscriptionLink(ServletParameterHelper.replaceDynamicParameters(subscriptionForward.getPath(),parameters));
            }
        }
        return programsPB;
    }
    private boolean sameDay(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }
}
