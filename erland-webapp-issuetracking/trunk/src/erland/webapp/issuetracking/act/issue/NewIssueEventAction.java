package erland.webapp.issuetracking.act.issue;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.issuetracking.fb.issue.IssueEventFB;
import erland.webapp.issuetracking.fb.issue.StatePB;
import erland.webapp.issuetracking.fb.account.AccountPB;
import erland.webapp.issuetracking.entity.application.ApplicationDispatcher;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.entity.issue.IssueEvent;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.account.UserAccount;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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

public class NewIssueEventAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IssueEventFB fb = (IssueEventFB) form;
        fb.setDescription(null);
        fb.setUsername(request.getRemoteUser());
        fb.setState(null);

        Issue template = (Issue) getEnvironment().getEntityFactory().create("issuetracking-issue");
        template.setId(fb.getIssueId());
        Issue issue = (Issue) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issue").load(template);
        if (issue == null) {
            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.issue-dont-exist"}));
            return;
        }

        QueryFilter filter = new QueryFilter("allforissue");
        filter.setAttribute("issue",fb.getIssueId());
        EntityInterface[] issues = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issueevent").search(filter);
        StatePB[] statesPB = new StatePB[1];
        statesPB[0] = new StatePB(IssueEvent.STATE_NEW,"issuetracking.state."+IssueEvent.STATE_NEW);
        if(issues.length>0) {
            fb.setState(((IssueEvent)issues[issues.length-1]).getState());
            filter = new QueryFilter("allforprevstate");
            filter.setAttribute("state",((IssueEvent)issues[issues.length-1]).getState());
            filter.setAttribute("application",issue.getApplication());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").search(filter);
            statesPB = new StatePB[entities.length+1];
            statesPB[0] = new StatePB(((IssueEvent)issues[issues.length-1]).getState(),"issuetracking.state."+((IssueEvent)issues[issues.length-1]).getState());
            for (int i = 0; i < entities.length; i++) {
                ApplicationDispatcher dispatcher = (ApplicationDispatcher) entities[i];
                statesPB[i+1] = new StatePB(dispatcher.getNewState(),"issuetracking.state."+dispatcher.getNewState());
            }
        }
        request.getSession().setAttribute("statesPB",statesPB);

        Application applicationTemplate = (Application) getEnvironment().getEntityFactory().create("issuetracking-application");
        applicationTemplate.setName(issue.getApplication());
        Application application = (Application) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").load(applicationTemplate);
        EntityInterface[] entities = null;
        if(application==null || !application.getOfficial().booleanValue()) {
            UserAccount userTemplate = (UserAccount) getEnvironment().getEntityFactory().create("issuetracking-useraccount");
            userTemplate.setUsername(request.getRemoteUser());
            UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-useraccount").load(userTemplate);
            entities = new EntityInterface[] {account};
        }else {
            filter = new QueryFilter("all");
            entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-useraccount").search(filter);
        }
        AccountPB[] accountsPB = new AccountPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            accountsPB[i] = new AccountPB();
            PropertyUtils.copyProperties(accountsPB[i],entities[i]);
        }
        request.getSession().setAttribute("usersPB",accountsPB);
    }
}