package erland.webapp.tvguide.act.program;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.favorite.Favorite;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.entity.subscription.Subscription;
import erland.webapp.tvguide.entity.account.UserAccount;
import erland.webapp.tvguide.fb.program.ProgramCollectionPB;
import erland.webapp.tvguide.fb.program.ProgramPB;
import erland.webapp.tvguide.fb.program.SearchProgramFB;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SearchMinReviewProgramsAction extends BaseAction {
    /**
     * Logging instance
     */
    private static Log LOG = LogFactory.getLog(SearchMinReviewProgramsAction.class);

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchProgramFB fb = (SearchProgramFB) form;
        String username = request.getRemoteUser();
        if (username == null) {
            username = fb.getUser();
        }

        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("tvguide-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("tvguide-useraccount").load(template);

        ProgramCollectionPB pb = new ProgramCollectionPB();
        if(fb.getDate()!=null) {
            pb.setPrograms(ProgramHelper.getProgramsWithMinReview(getEnvironment(),username, fb.getDate(),account.getMinTipsReview(),false,mapping.findForward("add-subscription-link"),mapping.findForward("add-exclusion-link")));
        }else {
            pb.setPrograms(ProgramHelper.getProgramsWithMinReview(getEnvironment(),username, new Date(), account.getMinTipsReview(), false,mapping.findForward("add-subscription-link"),mapping.findForward("add-exclusion-link")));
        }
        Map parameters = new HashMap();
        parameters.put("user", username);
        Date date = new Date();
        if(fb.getDate()!=null) {
            date = fb.getDate();
        }
        parameters.put("date",StringUtil.asString(date,null));

        Calendar cal = Calendar.getInstance();

        ActionForward forward = mapping.findForward("current-view-link");
        if(forward!=null) {
            pb.setCurrentDate(date);
            pb.setCurrentLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("next-view-link");
        if(forward!=null) {
            cal.setTime(date);
            cal.add(Calendar.DATE,1);
            pb.setNextDate(cal.getTime());
            parameters.put("date",StringUtil.asString(cal.getTime(),null));
            pb.setNextLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("prev-view-link");
        if(forward!=null) {
            cal.setTime(date);
            cal.add(Calendar.DATE,-1);
            pb.setPrevDate(cal.getTime());
            parameters.put("date",StringUtil.asString(cal.getTime(),null));
            pb.setPrevLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        request.setAttribute("programsPB", pb);
    }
}
