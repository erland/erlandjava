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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.tvguide.fb.channel.ChannelFB;
import erland.webapp.tvguide.fb.service.ServicePB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewChannelAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChannelFB fb = (ChannelFB) form;
        fb.setDescription(null);
        fb.setId(null);
        fb.setTag(null);
        fb.setName(null);
        fb.setDescription(null);
        fb.setLogo(null);
        fb.setLink(null);
        fb.setService(null);
        fb.setServiceParameters(null);

        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-service").search(new QueryFilter("all"));
        ServicePB[] servicesPB = new ServicePB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            servicesPB[i] = new ServicePB();
            PropertyUtils.copyProperties(servicesPB[i],entities[i]);
        }
        request.getSession().setAttribute("servicesPB",servicesPB);
    }
}
