package erland.webapp.diary.act.diary;

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
import erland.webapp.diary.entity.diary.Diary;
import erland.webapp.diary.fb.diary.DiaryFB;
import erland.webapp.diary.fb.diary.SelectDiaryFB;
import erland.webapp.usermgmt.User;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;

public class SelectDiaryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectDiaryFB fb = (SelectDiaryFB) form;
        Diary template = (Diary) getEnvironment().getEntityFactory().create("diary-diary");
        template.setId(fb.getDiary());
        Diary diary = (Diary) getEnvironment().getEntityStorageFactory().getStorage("diary-diary").load(template);
        if (diary == null) {
            saveErrors(request, Arrays.asList(new String[]{"diary.diary.do-not-exist"}));
            return;
        }
        if (request.getRemoteUser()!=null && !diary.getUsername().equals(request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"diary.diary.unauthorized"}));
            return;
        }
        request.getSession().setAttribute("diary",diary.getId());
    }
}