package erland.webapp.help.act.application;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.help.entity.application.Application;
import erland.webapp.help.fb.application.ApplicationFB;
import erland.webapp.help.fb.application.ApplicationPB;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ViewApplicationInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApplicationFB fb = (ApplicationFB) form;
        Application template = (Application) getEnvironment().getEntityFactory().create("help-application");
        template.setName(fb.getName());
        Application filter = (Application) getEnvironment().getEntityStorageFactory().getStorage("help-application").load(template);
        ApplicationPB pb = new ApplicationPB();
        PropertyUtils.copyProperties(pb, filter);

        boolean useEnglish = true;
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        if(useEnglish && StringUtil.asNull(pb.getTitleEnglish())!=null) {
            pb.setTitle(pb.getTitleEnglish());
        }else {
            pb.setTitle(pb.getTitleNative());
        }
        Map parameters = new HashMap();
        parameters.put("application",pb.getName());
        ActionForward forward = mapping.findForward("application-update-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("application-remove-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        request.setAttribute("applicationPB",pb);
    }
}