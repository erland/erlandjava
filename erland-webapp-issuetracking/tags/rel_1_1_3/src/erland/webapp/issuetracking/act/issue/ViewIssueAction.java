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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.fb.issue.IssueFB;
import erland.webapp.issuetracking.fb.issue.IssuePB;
import erland.webapp.issuetracking.fb.issue.IssueEventFB;
import erland.webapp.issuetracking.fb.issue.IssueEventPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewIssueAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IssueFB fb = (IssueFB) form;
        Issue template = (Issue) getEnvironment().getEntityFactory().create("issuetracking-issue");
        template.setId(fb.getId());
        Issue issue = (Issue) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issue").load(template);
        Application templateApplication = (Application) getEnvironment().getEntityFactory().create("issuetracking-application");
        templateApplication.setName(issue.getApplication());
        Application application = (Application) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").load(templateApplication);
        if(!isAllowed(request.getRemoteUser(),issue,application)) {
            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issue.view.not-authorized"}));
            return;
        }
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        IssuePB pb = new IssuePB();
        PropertyUtils.copyProperties(pb, issue);
        QueryFilter filter = new QueryFilter("allforissue");
        filter.setAttribute("issue",issue.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issueevent").search(filter);
        IssueEventPB[] eventsPB = new IssueEventPB[entities.length];
        for (int i = 0; i < eventsPB.length; i++) {
            eventsPB[i] = new IssueEventPB();
            PropertyUtils.copyProperties(eventsPB[i],entities[i]);
            eventsPB[i].setStateTextKey("issuetracking.state."+eventsPB[i].getState());
        }
        pb.setEvents(eventsPB);
        ActionForward forward = mapping.findForward("update-issue-link");
        if(forward!=null) {
            Map parameters = new HashMap();
            parameters.put("issue",pb.getId());
            parameters.put("application",pb.getApplication());
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        if(useEnglish) {
            pb.setApplicationTitle(application.getTitleEnglish());
        }else {
            pb.setApplicationTitle(application.getTitleNative());
        }
        request.setAttribute("issuePB",pb);
    }
    protected boolean isAllowed(String username, Issue issue, Application application) {
        if(issue==null) {
            return false;
        }
        if(application==null || (!application.getOfficial().booleanValue() && !application.getUsername().equals(username))) {
            return false;
        }
        return true;
    }
}