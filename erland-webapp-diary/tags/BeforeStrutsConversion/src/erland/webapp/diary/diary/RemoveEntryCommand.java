package erland.webapp.diary.diary;
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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class RemoveEntryCommand extends BaseEntryCommand implements CommandInterface {
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String execute(HttpServletRequest request) {
        try {
            Integer diary = getDiary(request.getParameter("diary"),null,request.getSession(true));
            if(diary!=null) {
                String dateString = request.getParameter("date");
                Date date = dateFormat.parse(dateString);
                DiaryEntryInterface entry = (DiaryEntryInterface)getEnvironment().getEntityFactory().create("diary-diaryentry");
                entry.setDiary(diary);
                entry.setDate(date);
                getEnvironment().getEntityStorageFactory().getStorage("diary-diaryentry").delete(entry);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }
}
