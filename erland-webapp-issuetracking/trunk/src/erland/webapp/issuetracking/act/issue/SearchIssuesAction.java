package erland.webapp.issuetracking.act.issue;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.issuetracking.fb.issue.IssueFB;
import erland.webapp.issuetracking.fb.issue.SearchIssueFB;
import erland.webapp.issuetracking.fb.issue.IssuePB;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.issue.ListIssue;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SearchIssuesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchIssueFB fb = (SearchIssueFB) form;
        String filtername = getQueryFilter();
        if(fb.getApplication()!=null && fb.getApplication().length()>0) {
            filtername = filtername+="forapplication";
        }
        if(fb.getStates()!=null && fb.getStates().length>0) {
            filtername = filtername+"forstates";
        }
        String username = request.getRemoteUser();
        if(username!=null && username.length()>0) {
            filtername = filtername+"foruser";
        }
        QueryFilter filter = new QueryFilter(filtername);
        if(fb.getStates()!=null) {
            filter.setAttribute("state",Arrays.asList(fb.getStates()));
        }
        if(fb.getApplication()!=null) {
            filter.setAttribute("application",fb.getApplication());
        }
        if(fb.getType()!=null) {
            filter.setAttribute("type",Arrays.asList(new Integer[] {fb.getType()}));
        }else {
            filter.setAttribute("type",Arrays.asList(new Integer[] {Issue.TYPE_PROBLEM,Issue.TYPE_FEATURE}));
        }
        if(username!=null) {
            filter.setAttribute("user",username);
        }
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-listissue").search(filter);
        IssuePB[] pb = new IssuePB[entities.length];
        ActionForward viewForward = mapping.findForward("view-issue-link");
        ActionForward updateForward = mapping.findForward("update-issue-link");

        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        Map parameters = new HashMap();
        parameters.put("application",fb.getApplication());
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new IssuePB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
            if(useEnglish) {
                pb[i].setApplicationTitle(((ListIssue)entities[i]).getApplicationTitleEnglish());
            }else {
                pb[i].setApplicationTitle(((ListIssue)entities[i]).getApplicationTitleNative());
            }
            pb[i].setStateTextKey("issuetracking.state."+((ListIssue)entities[i]).getState());
            parameters.put("issue",pb[i].getId());
            if(viewForward != null) {
                pb[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewForward.getPath(),parameters));
            }
            if(updateForward!=null) {
                pb[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateForward.getPath(),parameters));
            }
        }
        request.setAttribute("issuesPB", pb);
    }
    protected String getQueryFilter() {
        return "all";
    }
}