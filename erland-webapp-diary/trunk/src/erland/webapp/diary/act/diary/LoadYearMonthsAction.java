package erland.webapp.diary.act.diary;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.diary.fb.diary.SelectYearMonthFB;
import erland.webapp.diary.fb.diary.KeyLinkPB;
import erland.webapp.diary.fb.diary.YearMonthsPB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

public class LoadYearMonthsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectYearMonthFB fb = (SelectYearMonthFB) form;
        String user = request.getRemoteUser();
        if(user==null) {
            user = fb.getUser();
        }
        Integer year = fb.getYear();
        if(year==null) {
            year = new Integer(Calendar.getInstance().get(Calendar.YEAR));
        }
        Integer month = fb.getMonth();
        if(month==null) {
            month = new Integer(Calendar.getInstance().get(Calendar.MONTH)+1);
        }
        KeyLinkPB[] yearsPB = new KeyLinkPB[3];
        ActionForward forward = mapping.findForward("view-year-link");
        Map parameters = new HashMap();
        parameters.put("user",user);
        parameters.put("month",new Integer(12));
        if(forward!=null) {
            for(int i=0;i<3;i++) {
                parameters.put("year",new Integer(year.intValue()-1+i));
                if(i==1) {
                    parameters.put("month",new Integer(1));
                }
                yearsPB[i] = new KeyLinkPB(""+(year.intValue()-1+i),ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                if(yearsPB[i].getKey().equals(year.toString())) {
                    yearsPB[i].setSelected(Boolean.TRUE);
                }
            }
        }

        KeyLinkPB[] monthsPB = new KeyLinkPB[12];
        forward = mapping.findForward("view-month-link");
        parameters.put("year",year);
        if(forward!=null) {
            for(int i=0;i<12;i++) {
                parameters.put("month",new Integer(i+1));
                monthsPB[i] = new KeyLinkPB("diary.calendar.month."+(i+1),ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                if((i+1)==month.intValue()) {
                    monthsPB[i].setSelected(Boolean.TRUE);
                }
            }
        }
        YearMonthsPB pb = new YearMonthsPB();
        pb.setYears(yearsPB);
        pb.setMonths(monthsPB);
        request.getSession().setAttribute("calendarYearMonthsPB",pb);
        request.getSession().setAttribute("year",year);
        request.getSession().setAttribute("month",month);
    }
}