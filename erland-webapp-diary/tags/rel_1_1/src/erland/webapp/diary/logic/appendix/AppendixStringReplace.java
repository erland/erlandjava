package erland.webapp.diary.logic.appendix;
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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.html.StringReplaceInterface;
import erland.webapp.diary.entity.appendix.AppendixEntry;
import erland.util.Log;

public class AppendixStringReplace implements StringReplaceInterface {
    public String replace(String str) {
        Log.println(this,"replacing appendix entries with "+getClass().getName());
        EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("diary-appendixentry").search(new QueryFilter("all"));
        for (int i = 0; i < entities.length; i++) {
            AppendixEntry entry = (AppendixEntry) entities[i];
            Log.println(this,"replacing appendix entry "+entry.getName());
            if(entry.getDescription()!=null && entry.getDescription().length()>0) {
                Log.println(this,"Before:"+str);
                str = doReplace(str,"(?i)(\\b\\w*"+entry.getName()+"\\w*\\b)",entry.getDescription());
                Log.println(this,"After :"+str);
            }
        }
        return str;
    }
    private String doReplace(String data, String replace, String with) {
        StringBuffer sb = new StringBuffer(data);
        int startPos = 0;
        while(startPos>=0 && startPos<sb.length()) {
            int pos = sb.indexOf("[",startPos);
            if(pos>=0) {
                String str = sb.substring(startPos,pos);
                str = str.replaceAll(replace,with);
                sb.replace(startPos,pos,str);
                startPos = sb.indexOf("]",pos);
            }else {
                String str = sb.substring(startPos);
                str = str.replaceAll(replace,with);
                sb.replace(startPos,sb.length(),str);
                startPos = sb.length();
            }
            if(startPos>=0) {
                startPos++;
            }
        }

        return sb.toString();
    }
}
