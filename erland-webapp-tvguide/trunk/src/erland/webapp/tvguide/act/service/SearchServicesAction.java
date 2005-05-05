package erland.webapp.tvguide.act.service;

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
import erland.webapp.tvguide.entity.service.Service;
import erland.webapp.tvguide.fb.service.ServicePB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SearchServicesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter filter = new QueryFilter(getQueryFilter());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-service").search(filter);
        ServicePB[] pb = new ServicePB[entities.length];
        ActionForward updateLink = mapping.findForward("update-service-link");
        ActionForward removeLink = mapping.findForward("remove-service-link");
        Map parameters = new HashMap();
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new ServicePB();
            try {
                PropertyUtils.copyProperties(pb[i], entities[i]);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            parameters.put("service",((Service)entities[i]).getId());
            if(updateLink!=null) {
                pb[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateLink.getPath(),parameters));
            }
            if(removeLink!=null) {
                pb[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(removeLink.getPath(),parameters));
            }
        }
        request.setAttribute("servicesPB", pb);
    }


    protected String getQueryFilter() {
        return "all";
    }
}
