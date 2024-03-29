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

import java.util.Vector;

public class HTMLEncoder {
    private static Vector replaceRoutines = new Vector();
    public static String encode(String str) {
        for (int i = 0; i < replaceRoutines.size(); i++) {
            StringReplaceInterface replaceRoutine = (StringReplaceInterface) replaceRoutines.elementAt(i);
            str = replaceRoutine.replace(str);
        }
        return str;
    }
    public static void addReplaceRoutine(StringReplaceInterface replaceRoutine) {
        replaceRoutines.add(replaceRoutine);
    }
    public static void reset() {
        for (int i = 0; i < replaceRoutines.size(); i++) {
            StringReplaceInterface replaceRoutine = (StringReplaceInterface) replaceRoutines.elementAt(i);
            if(replaceRoutine instanceof DynamicStringReplaceInterface) {
                ((DynamicStringReplaceInterface)replaceRoutine).reset();
            }
        }
    }
}
