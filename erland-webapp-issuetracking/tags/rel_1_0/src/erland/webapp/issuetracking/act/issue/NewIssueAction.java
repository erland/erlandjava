package erland.webapp.issuetracking.act.issue;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.issuetracking.fb.issue.IssueFB;
import erland.webapp.issuetracking.fb.issue.TypePB;
import erland.webapp.issuetracking.fb.application.ApplicationFB;
import erland.webapp.issuetracking.fb.application.ApplicationPB;
import erland.webapp.issuetracking.entity.issue.Issue;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class NewIssueAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IssueFB fb = (IssueFB) form;
        fillData(fb);
        fb.setId(null);
        fb.setUsername(request.getRemoteUser());
        fb.setMail(null);
        fb.setType(Issue.TYPE_PROBLEM);

        TypePB[] typesPB = new TypePB[] {
            new TypePB(Issue.TYPE_PROBLEM,"issuetracking.type."+Issue.TYPE_PROBLEM),
            new TypePB(Issue.TYPE_FEATURE,"issuetracking.type."+Issue.TYPE_FEATURE)
        };
        request.getSession().setAttribute("typesPB",typesPB);

        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("user",request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("issuetracking-application").search(filter);
        ApplicationPB[] pb = new ApplicationPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new ApplicationPB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
            if(useEnglish) {
                pb[i].setTitle(pb[i].getTitleEnglish());
            }else {
                pb[i].setTitle(pb[i].getTitleNative());
            }
        }
        request.getSession().setAttribute("applicationsPB", pb);
    }
    protected String getQueryFilter() {
        return "allofficialforuser";
    }
    protected void fillData(IssueFB fb) {
        fb.setDescription(null);
        fb.setTitle(null);
        fb.setVersion(null);
    }
}