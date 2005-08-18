package erland.webapp.cdcollection.act.media;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.cdcollection.entity.media.Media;
import erland.webapp.cdcollection.fb.collection.SelectCollectionFB;
import erland.webapp.cdcollection.fb.media.MediaFB;
import erland.webapp.cdcollection.fb.media.MediaPB;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchMediasAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MediaPB[] pb = new MediaPB[0];
        QueryFilter filter = new QueryFilter("all");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").search(filter);
        ActionForward viewLink = mapping.findForward("media-view-link");
        ActionForward coverLink = mapping.findForward("media-cover-link");
        pb = new MediaPB[entities.length];
        Map parameters = new HashMap();
        if(StringUtil.asNull(request.getServerName())!=null) {
            parameters.put("hostname",request.getServerName());
            if(request.getServerPort()!=80) {
                parameters.put("port",new Integer(request.getServerPort()));
            }
        }
        parameters.put("contextpath",request.getContextPath());
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new MediaPB();
            Media entity = (Media) entities[i];
            PropertyUtils.copyProperties(pb[i], entity);
            parameters.put("media",pb[i].getId());
            if(viewLink!=null) {
                pb[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewLink.getPath(),parameters));
            }
            if(coverLink!=null && StringUtil.asNull(entity.getCoverUrl())!=null) {
                pb[i].setCoverLink(ServletParameterHelper.replaceDynamicParameters(coverLink.getPath(),parameters));
            }
        }
        request.setAttribute("mediasPB", pb);
    }
}
