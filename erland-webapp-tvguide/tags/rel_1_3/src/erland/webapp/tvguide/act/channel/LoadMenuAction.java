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

import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.fb.channel.MenuItemPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadMenuAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("user");
        if (StringUtil.asNull(username)==null) {
            username = request.getRemoteUser();
        }

        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").search(filter);
        ActionForward channelForward = mapping.findForward("channel");
        String channelPath = null;
        if (channelForward != null) {
            channelPath = channelForward.getPath();
        }
        MenuItemPB[] channelsPB = new MenuItemPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            Channel channel = (Channel) entities[i];
            MenuItemPB pb = new MenuItemPB();
            pb.setId(channel.getId());
            pb.setName(channel.getName());
            pb.setPath(channelPath);
            pb.setUser(username);
            channelsPB[i] = pb;
        }

        request.getSession().setAttribute("menuChannelsPB", channelsPB);
    }

    protected String getQueryFilter() {
        return "all";
    }

}
