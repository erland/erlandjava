package erland.webapp.diary.act.account;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.account.UserAccount;
import erland.webapp.diary.fb.account.SelectUserFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ViewDefaultDiaryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = getUsername(request,fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("diary-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("diary-useraccount").load(template);

        request.setAttribute("user",username);
        Integer diary = null;
        if(account.getDefaultDiary()!=null && account.getDefaultDiary().intValue()>0) {
            diary = account.getDefaultDiary();
            request.getSession().setAttribute("diary",diary);
        }else {
            saveErrors(request, Arrays.asList(new String[]{"diary.account.no-defaultdiary"}));
        }
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }
        return username;
    }
}
