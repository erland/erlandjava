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
import erland.webapp.issuetracking.fb.issue.IssueEventFB;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.entity.application.ApplicationDispatcher;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.issue.IssueEvent;
import erland.webapp.usermgmt.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class EditIssueEventAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IssueEventFB fb = (IssueEventFB) form;
        IssueEvent event = (IssueEvent) getEnvironment().getEntityFactory().create("issuetracking-issueevent");
        PropertyUtils.copyProperties(event, fb);
        event.setEventId(null);
        String username = request.getRemoteUser();

        // Check if use owns issue
        Issue issue = getIssue(event.getIssueId());
        if(issue==null) {
            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.issue-dont-exists"}));
            return;
        }
        Application application = getApplication(issue.getApplication());
        if(application==null) {
            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.application-dont-exists"}));
            return;
        }
        QueryFilter filter = new QueryFilter("allforissue");
        filter.setAttribute("issue",fb.getIssueId());
        EntityInterface[] issues = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issueevent").search(filter);
        if(issues.length==0) {
            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.previous-events-dont-exists"}));
            return;
        }
        if(!username.equals(((IssueEvent)issues[issues.length-1]).getUsername()) &&
           !username.equals(application.getUsername())) {

            saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.not-authorized"}));
            return;
        }

        // Set previous state
        event.setPrevState(((IssueEvent)issues[issues.length-1]).getState());
        // Set username
        ApplicationDispatcher dispatcher = getApplicationDispatcher(issue.getApplication(),event.getPrevState(),event.getState());
        if(dispatcher!=null) {
            if(dispatcher.getUsername()!=null && dispatcher.getUsername().length()>0) {
                event.setUsername(dispatcher.getUsername());
            }else if(fb.getUsername()!=null && fb.getUsername().length()>0) {
                event.setUsername(fb.getUsername());
            }else {
                event.setUsername(((IssueEvent)issues[issues.length-1]).getUsername());
            }
        }else if(fb.getUsername()!=null && fb.getUsername().length()>0) {
            event.setUsername(fb.getUsername());
        }else {
            event.setUsername(((IssueEvent)issues[issues.length-1]).getUsername());
        }

        // Check if state change is allowed
        if(!event.getPrevState().equals(event.getState())) {
            if(dispatcher==null) {
                saveErrors(request, Arrays.asList(new String[]{"issuetracking.issueevent.new.wrong-state-change"}));
                return;
            }
        }
        getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issueevent").store(event);

        // Send mail to dispatcher if user has changed
        if(!event.getUsername().equals(((IssueEvent)issues[issues.length-1]).getUsername())) {
            User user = getUser(event.getUsername());
            if(user.getMail()!=null && user.getMail().length()>0) {
                //TODO: Implement so an e-mail is sent
            }
        }
    }

    protected Application getApplication(String name) {
        Application template = (Application) getEnvironment().getEntityFactory().create("issuetracking-application");
        template.setName(name);
        return (Application) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").load(template);
    }

    protected Issue getIssue(Integer id) {
        Issue template = (Issue) getEnvironment().getEntityFactory().create("issuetracking-issue");
        template.setId(id);
        return (Issue) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issue").load(template);
    }

    protected ApplicationDispatcher getApplicationDispatcher(String name, Integer prevState, Integer newState) {
        ApplicationDispatcher template = (ApplicationDispatcher) getEnvironment().getEntityFactory().create("issuetracking-applicationdispatcher");
        template.setApplication(name);
        template.setPrevState(prevState);
        template.setNewState(newState);
        return (ApplicationDispatcher) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").load(template);
    }

    protected User getUser(String username) {
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-user");
        template.setUsername(username);
        return (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").load(template);
    }
}