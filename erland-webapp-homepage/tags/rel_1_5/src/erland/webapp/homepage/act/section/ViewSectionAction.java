package erland.webapp.homepage.act.section;

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

import erland.webapp.common.DescriptionIdHelper;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.fb.DescriptionIdPB;
import erland.webapp.homepage.entity.section.Section;
import erland.webapp.homepage.fb.section.SectionFB;
import erland.webapp.homepage.fb.service.ServicePB;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewSectionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SectionFB fb = (SectionFB) form;
        Section template = (Section) getEnvironment().getEntityFactory().create("homepage-section");
        template.setUsername(request.getRemoteUser());
        template.setId(fb.getId());
        Section section = (Section) getEnvironment().getEntityStorageFactory().getStorage("homepage-section").load(template);
        PropertyUtils.copyProperties(fb, section);

        boolean useEnglish = !getLocale(request).getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("homepage-service").search(new QueryFilter("all"));
        ServicePB[] servicesPB = new ServicePB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            servicesPB[i] = new ServicePB();
            PropertyUtils.copyProperties(servicesPB[i],entities[i]);
            if(useEnglish && StringUtil.asNull(servicesPB[i].getNameEnglish())!=null) {
                servicesPB[i].setName(servicesPB[i].getNameEnglish());
            }
        }
        request.getSession().setAttribute("servicesPB",servicesPB);
    }
}
