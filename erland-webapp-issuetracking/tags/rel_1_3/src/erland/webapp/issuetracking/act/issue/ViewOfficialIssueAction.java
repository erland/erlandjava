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
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.fb.issue.IssueFB;
import erland.webapp.issuetracking.fb.issue.IssuePB;
import erland.webapp.issuetracking.fb.issue.IssueEventFB;
import erland.webapp.issuetracking.fb.issue.IssueEventPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewOfficialIssueAction extends ViewIssueAction {
    protected boolean isAllowed(String username, Issue issue) {
        if(issue==null) {
            return false;
        }
        Application template = (Application) getEnvironment().getEntityFactory().create("issuetracking-application");
        template.setName(issue.getApplication());
        Application application = (Application) getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").load(template);

        if(application==null || application.getOfficial()==null || !application.getOfficial().booleanValue()) {
            return false;
        }
        return true;
    }
}