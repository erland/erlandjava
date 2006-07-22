package erland.webapp.issuetracking.act.application;

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
import erland.webapp.common.act.BaseAction;
import erland.webapp.issuetracking.fb.application.MenuItemPB;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.issue.IssueEvent;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoadMenuApplicationsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }
        QueryFilter filter = getQueryFilter(form,request);
        filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_PROBLEM,Issue.TYPE_FEATURE}));
        filter.setAttribute("state",Arrays.asList(new Integer[] {
            IssueEvent.STATE_NEW,
            IssueEvent.STATE_ANALYZED,
            IssueEvent.STATE_ANALYZED_FAIL,
            IssueEvent.STATE_ANALYZING,
            IssueEvent.STATE_CORRECTED,
            IssueEvent.STATE_CORRECTED_FAIL,
            IssueEvent.STATE_CORRECTING,
            IssueEvent.STATE_TESTED,
            IssueEvent.STATE_TESTED_FAIL,
            IssueEvent.STATE_TESTING,
            IssueEvent.STATE_WAITING}));
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        MenuItemPB[] pb = new MenuItemPB[entities.length];
        ActionForward forward = mapping.findForward("view-application-issues");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuIssuesApplicationsPB", pb);

        filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_PROBLEM}));
        entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        pb = new MenuItemPB[entities.length];
        forward = mapping.findForward("view-application-error-issues");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuErrorIssuesApplicationsPB", pb);

        filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_FEATURE}));
        entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        pb = new MenuItemPB[entities.length];
        forward = mapping.findForward("view-application-feature-issues");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuFeatureIssuesApplicationsPB", pb);

        filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_FEATURE,Issue.TYPE_PROBLEM}));
        filter.setAttribute("state",Arrays.asList(new Integer[] {IssueEvent.STATE_DELIVERED}));
        entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        pb = new MenuItemPB[entities.length];
        forward = mapping.findForward("view-application-delivered-issues");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuClosedIssuesApplicationsPB", pb);

        filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_FEATURE,Issue.TYPE_PROBLEM}));
        filter.setAttribute("state",Arrays.asList(new Integer[] {IssueEvent.STATE_REJECTED,IssueEvent.STATE_DELIVERED}));
        entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        pb = new MenuItemPB[entities.length];
        forward = mapping.findForward("view-application-closed-issues");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuClosedIssuesApplicationsPB", pb);

        filter = new QueryFilter("allforuser");
        filter.setAttribute("user",request.getRemoteUser());
        entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        pb = new MenuItemPB[entities.length];
        forward = mapping.findForward("view-application");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            if(useEnglish) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuUserApplicationsPB", pb);
    }
    protected QueryFilter getQueryFilter(ActionForm form, HttpServletRequest request) {
        QueryFilter filter = new QueryFilter("allwithissuesforuser");
        filter.setAttribute("user",request.getRemoteUser());
        return filter;
    }
}