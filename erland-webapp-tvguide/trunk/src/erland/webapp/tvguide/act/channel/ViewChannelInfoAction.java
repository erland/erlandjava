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
        ProgramHelper.loadPrograms(getEnvironment(),false);

        pb.setPrograms(getPrograms(username,fb,channel));
        request.setAttribute("channelPB",pb);
    }

    protected ProgramPB[] getPrograms(String username, BaseFB fb,Channel channel) {
        QueryFilter filter = new QueryFilter("allforchannelanddateaftertime");
        filter.setAttribute("channel",channel.getId());
        filter.setAttribute("time",new Date());
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
        }
        return programsPB;
    }
}
