package erland.webapp.common.tags;

import erland.webapp.common.html.HTMLBasicStringReplace;
import erland.webapp.common.html.StringReplaceInterface;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

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

public class MenuItemTag extends BodyTagSupport {
    private String name;
    private String id;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int doAfterBody() throws JspException {
        String menuObj = (String) pageContext.getAttribute(name,PageContext.SESSION_SCOPE);
        if(menuObj!=null&&menuObj.startsWith(id)) {
            try {
                BodyContent bc = getBodyContent();
                // get the bodycontent as string
                String body = bc.getString();
                // getJspWriter to output content
                JspWriter out = bc.getEnclosingWriter();
                if (body != null) {
                    out.print(body);
                }
            } catch (IOException e) {
                throw new JspException("Error: " + e.getMessage());
            }
        }
        return SKIP_BODY;
    }
}