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

import erland.webapp.common.act.BaseAction;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.fb.channel.ChannelFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class EditChannelAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChannelFB fb = (ChannelFB) form;
        Channel channel = (Channel) getEnvironment().getEntityFactory().create("tvguide-channel");
        if(channel.getId()!=null && channel.getId().intValue()!=0) {
            channel.setId(fb.getId());
            Channel template = (Channel) getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").load(channel);
            if(template!=null) {
                channel.setCacheDate(template.getCacheDate());
            }
        }else {
            channel.setCacheDate(new Date(0));
        }
        PropertyUtils.copyProperties(channel, fb);
        getEnvironment().getEntityStorageFactory().getStorage("tvguide-channel").store(channel);
    }
}
