package erland.webapp.issuetracking.act.application;

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
import erland.webapp.issuetracking.fb.application.ApplicationFB;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.entity.application.ApplicationDispatcher;
import erland.webapp.issuetracking.entity.issue.IssueEvent;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditApplicationAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApplicationFB fb = (ApplicationFB) form;

        Application template = (Application) getEnvironment().getEntityFactory().create("issuetracking-application");
        template.setName(fb.getName());
        Application application = (Application) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").load(template);

        PropertyUtils.copyProperties(template, fb);
        String username = request.getRemoteUser();
        if(application==null) {
            template.setUsername(username);
        }
        getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").store(template);

        if(application==null) {
            ApplicationDispatcher dispatcher = (ApplicationDispatcher) getEnvironment().getEntityFactory().create("issuetracking-applicationdispatcher");
            dispatcher.setApplication(fb.getName());

            // New dispatcher
            dispatcher.setPrevState(IssueEvent.STATE_UNINITIALIZED);
            dispatcher.setNewState(IssueEvent.STATE_NEW);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // New choices
            dispatcher.setPrevState(IssueEvent.STATE_NEW);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_NEW);
            dispatcher.setNewState(IssueEvent.STATE_CORRECTING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_NEW);
            dispatcher.setNewState(IssueEvent.STATE_REJECTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_NEW);
            dispatcher.setNewState(IssueEvent.STATE_WAITING);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Correcting choices
            dispatcher.setPrevState(IssueEvent.STATE_CORRECTING);
            dispatcher.setNewState(IssueEvent.STATE_CORRECTED_FAIL);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_CORRECTING);
            dispatcher.setNewState(IssueEvent.STATE_CORRECTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Corrected choices
            dispatcher.setPrevState(IssueEvent.STATE_CORRECTED);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_CORRECTED);
            dispatcher.setNewState(IssueEvent.STATE_TESTING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_CORRECTED);
            dispatcher.setNewState(IssueEvent.STATE_DELIVERED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Analyzing choices
            dispatcher.setPrevState(IssueEvent.STATE_ANALYZING);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZED_FAIL);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_ANALYZING);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Analyzed choices
            dispatcher.setPrevState(IssueEvent.STATE_ANALYZED);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_ANALYZED);
            dispatcher.setNewState(IssueEvent.STATE_TESTING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_ANALYZED);
            dispatcher.setNewState(IssueEvent.STATE_REJECTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Testing choices
            dispatcher.setPrevState(IssueEvent.STATE_TESTING);
            dispatcher.setNewState(IssueEvent.STATE_TESTED_FAIL);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_TESTING);
            dispatcher.setNewState(IssueEvent.STATE_TESTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Tested choices
            dispatcher.setPrevState(IssueEvent.STATE_TESTED);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_TESTED);
            dispatcher.setNewState(IssueEvent.STATE_CORRECTING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_TESTED);
            dispatcher.setNewState(IssueEvent.STATE_DELIVERED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_TESTED);
            dispatcher.setNewState(IssueEvent.STATE_REJECTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);

            // Waiting choices
            dispatcher.setPrevState(IssueEvent.STATE_WAITING);
            dispatcher.setNewState(IssueEvent.STATE_ANALYZING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_WAITING);
            dispatcher.setNewState(IssueEvent.STATE_CORRECTING);
            dispatcher.setUsername(null);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
            dispatcher.setPrevState(IssueEvent.STATE_WAITING);
            dispatcher.setNewState(IssueEvent.STATE_REJECTED);
            dispatcher.setUsername(username);
            getEnvironment().getEntityStorageFactory().getStorage("issuetracking-applicationdispatcher").store(dispatcher);
        }

    }
}