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

import erland.webapp.diary.fb.diary.DiaryFB;
import erland.webapp.diary.fb.diary.DiaryEntryFB;
import erland.webapp.diary.entity.diary.DiaryEntry;
import erland.webapp.common.act.BaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoveEntryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiaryEntryFB fb = (DiaryEntryFB) form;
        DiaryEntry entry = (DiaryEntry) getEnvironment().getEntityFactory().create("diary-diaryentry");
        entry.setDiary(fb.getDiary());
        entry.setDate(fb.getDate());
        getEnvironment().getEntityStorageFactory().getStorage("diary-diaryentry").delete(entry);
    }
}
