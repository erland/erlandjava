package erland.webapp.tvguide.act.favorite;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.tvguide.fb.channel.ChannelFB;
import erland.webapp.tvguide.fb.channel.ChannelPB;
import erland.webapp.tvguide.fb.favorite.FavoriteFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewFavoriteAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FavoriteFB fb = (FavoriteFB) form;
        fb.setId(null);
        fb.setChannel(null);
        fb.setOrderNo(null);

        QueryFilter filter = new QueryFilter("all");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").search(filter);
        ChannelPB[] channelsPB = new ChannelPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            channelsPB[i] = new ChannelPB();
            PropertyUtils.copyProperties(channelsPB[i],entities[i]);
        }
        request.getSession().setAttribute("channelsPB",channelsPB);
        
    }
}
