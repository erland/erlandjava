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
import erland.webapp.issuetracking.fb.issue.IssueFB;
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
import java.util.Date;

public class EditIssueAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IssueFB fb = (IssueFB) form;
        Issue issue = (Issue) getEnvironment().getEntityFactory().create("issuetracking-issue");
        PropertyUtils.copyProperties(issue, fb);
        issue.setId(null);
        String username = request.getRemoteUser();
        if(username!=null) {
            issue.setUsername(username);
        }
        getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issue").store(issue);
        IssueEvent event = (IssueEvent) getEnvironment().getEntityFactory().create("issuetracking-issueevent");
        event.setIssueId(issue.getId());
        event.setPrevState(null);
        event.setState(IssueEvent.STATE_NEW);
        event.setDate(new Date());
        event.setDescription(null);
        ApplicationDispatcher dispatcher = getApplicationDispatcher(fb.getApplication(),IssueEvent.STATE_UNINITIALIZED,event.getState());
        if(dispatcher==null || dispatcher.getUsername()==null || dispatcher.getUsername().length()==0) {
            Application application = getApplication(fb.getApplication());
            if(application!=null) {
                event.setUsername(application.getUsername());
            }
        }else {
            event.setUsername(dispatcher.getUsername());
        }
        getEnvironment().getEntityStorageFactory().getStorage("issuetracking-issueevent").store(event);

        // Send mail to dispatcher
        if(event.getUsername()!=null && event.getUsername().length()>0) {
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