package erland.webapp.common.html;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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


public class HTMLBasicStringReplace implements StringReplaceInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(HTMLBasicStringReplace.class);
    public String replace(String str) {
        LOG.debug("replacing HTML tags with "+getClass().getName());
        str = str.replaceAll("<","&lt");
        str = str.replaceAll(">","&gt");
        str = str.replaceAll("\n","<BR>");
        return str;
    }
}
