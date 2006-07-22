package erland.webapp.issuetracking.act.application;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.issuetracking.entity.issue.Issue;
import erland.webapp.issuetracking.entity.issue.IssueEvent;
import erland.webapp.issuetracking.entity.application.Application;
import erland.webapp.issuetracking.fb.application.MenuItemPB;
import erland.webapp.issuetracking.fb.application.SelectApplicationFB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoadOfficialMenuApplicationsAction extends LoadMenuApplicationsAction {
    protected QueryFilter getQueryFilter(ActionForm form, HttpServletRequest request) {
        SelectApplicationFB fb = (SelectApplicationFB) form;
        QueryFilter filter = new QueryFilter("allwithissuesforapplication");
        filter.setAttribute("application",fb.getApplication());
        return filter;
    }
}
