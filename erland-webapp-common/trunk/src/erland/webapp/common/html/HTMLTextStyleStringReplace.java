package erland.webapp.common.html;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Pattern;

public class HTMLTextStyleStringReplace implements StringReplaceInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(HTMLTextStyleStringReplace.class);
    public String replace(String str) {
        LOG.debug("Before: "+str);
        str = str.replaceAll("\\[style=([^\\]]*),([^\\]]*)\\]","<div class=\"$1\">$2</div>");
        LOG.debug("After : "+str);
        LOG.debug("Before: "+str);
        str = Pattern.compile("\\[style=([^\\]]*)\\](.*)\\[/style\\]",Pattern.DOTALL).matcher(str).replaceAll("<p class=\"$1\">$2</p>");
        LOG.debug("After : "+str);
        return str;
    }
}
