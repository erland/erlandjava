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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.help.entity.application.ApplicationVersion;
import erland.webapp.help.entity.application.Application;
import erland.webapp.help.fb.application.MenuItemPB;
import erland.webapp.help.fb.application.ApplicationPB;
import erland.webapp.help.fb.chapter.SelectChapterFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoadOfficialMenuAction extends LoadMenuAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectChapterFB fb = (SelectChapterFB) form;

        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }
        Application template = (Application) getEnvironment().getEntityFactory().create("help-application");
        template.setName(fb.getApplication());
        Application application = (Application) getEnvironment().getEntityStorageFactory().getStorage("help-application").load(template);
        if(application==null) {
            saveErrors(request, Arrays.asList(new String[]{"help.application.application-dont-exist"}));
            return;
        }
        if(application.getOfficial()==null || !application.getOfficial().booleanValue()) {
            saveErrors(request, Arrays.asList(new String[]{"help.application.application-unauthorized"}));
            return;
        }
        ApplicationPB applicationPB = new ApplicationPB();
        PropertyUtils.copyProperties(applicationPB,application);
        if(useEnglish && StringUtil.asNull(applicationPB.getTitleEnglish())!=null) {
            applicationPB.setTitle(applicationPB.getTitleEnglish());
        }else {
            applicationPB.setTitle(applicationPB.getTitleNative());
        }
        request.getSession().setAttribute("applicationPB",applicationPB);

        String version = fb.getVersion();
        if(StringUtil.asNull(version)==null) {
            QueryFilter filter = new QueryFilter("allforapplication");
            filter.setAttribute("application",fb.getApplication());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("help-applicationversion").search(filter);
            if(entities.length>0) {
                version = ((ApplicationVersion) entities[entities.length-1]).getVersion();
            }
        }
        if(StringUtil.asNull(version)==null) {
            saveErrors(request, Arrays.asList(new String[]{"help.application.version.version-dont-exist"}));
            return;
        }

        ActionForward chapterForward = mapping.findForward("view-chapter");
        MenuItemPB[] pb = getChapters(request.getRemoteUser(),fb.getApplication(),version,chapterForward,null,useEnglish);
        request.setAttribute("version",version);
        request.getSession().setAttribute("menuApplicationsPB", pb);
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SelectChapterFB fb = (SelectChapterFB) form;
        if(StringUtil.asNull(fb.getChapter())!=null) {
            return mapping.findForward("success-chapter");
        }else {
            return super.findSuccess(mapping, form, request, response);
        }
    }
}