package erland.webapp.common.html;
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

import erland.util.Log;

public class HTMLLinkStringReplace implements StringReplaceInterface {
    public String replace(String str) {
        Log.println(this,"replacing links with "+getClass().getName());
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[img=([^\\]]*),([^\\]]*)\\]","<a href=\"$2\" target=\"_blank\"><img src=\"$1\" border=\"0\"></img></a>");
        Log.println(this,"After : "+str);
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[img=([^\\]]*)\\]","<img src=\"$1\"></img>");
        Log.println(this,"After : "+str);
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[link=([^\\]]*),([^\\]]*)\\]","<a class=\"link\" href=\"$2\" target=\"_blank\">$1</a>");
        Log.println(this,"After : "+str);
        return str;
    }
}
