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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.diary.DiaryEntryInterface;
import erland.webapp.diary.entity.diary.Diary;
import erland.webapp.diary.entity.diary.DiaryEntry;
import erland.webapp.diary.fb.diary.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

public class SearchEntriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectDiaryEntryFB fb = (SelectDiaryEntryFB) form;

        Diary template = (Diary) getEnvironment().getEntityFactory().create("diary-diary");
        template.setId(fb.getDiary());
        Diary diary = (Diary) getEnvironment().getEntityStorageFactory().getStorage("diary-diary").load(template);
        if(diary!=null) {
            DiaryFB diaryPB = new DiaryFB();
            try {
                PropertyUtils.copyProperties(diaryPB,diary);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            request.setAttribute("diaryPB",diaryPB);
        }

        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(request.getSession().getAttribute("year"));
        String month = String.valueOf(request.getSession().getAttribute("month"));
        if(year!=null) {
            cal.set(Calendar.YEAR,Integer.valueOf(year).intValue());
            cal.set(Calendar.DAY_OF_MONTH,1);
            cal.set(Calendar.MONTH,0);
        }
        if(month!=null) {
            cal.set(Calendar.MONTH,Integer.valueOf(month).intValue()-1);
        }
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        QueryFilter filter = new QueryFilter("allfordiarybeforedate");
        filter.setAttribute("diary", fb.getDiary());
        filter.setAttribute("date",cal.getTime());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-diaryentry").search(filter);
        DiaryEntryPB[] pb = new DiaryEntryPB[entities.length];
        Map parameters = new HashMap();
        parameters.put("diary",fb.getDiary());
        String user = request.getRemoteUser();
        if(user==null) {
            user = fb.getUser();
        }
        parameters.put("user",user);
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new DiaryEntryPB();
            try {
                PropertyUtils.copyProperties(pb[i],entities[i]);
                ActionForward forward = mapping.findForward("view-entry-link");
                if(forward!=null) {
                    parameters.put("date",pb[i].getDateDisplay());
                    pb[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }
        request.getSession().setAttribute("diaryEntriesPB",pb);
    }
}
