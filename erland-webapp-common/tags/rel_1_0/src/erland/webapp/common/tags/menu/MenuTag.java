package erland.webapp.common.tags.menu;

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

public class MenuTag extends BodyTagSupport implements MenuItemInterface {
    private String id;
    private String style;
    private String styleSelected;
    private Integer indentWidth;
    private String indentImage;
    private String menuStyle;

    public void setIndentWidth(Integer indentWidth) {
        this.indentWidth = indentWidth;
    }

    public Integer getIndentWidth() {
        return indentWidth!=null?indentWidth:new Integer(20);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setMenuStyle(String menuStyle) {
        this.menuStyle = menuStyle;
    }

    public String getIndentImage() {
        return indentImage;
    }

    public void setIndentImage(String indentImage) {
        this.indentImage = indentImage;
    }

    public void setStyleSelected(String styleSelected) {
        this.styleSelected = styleSelected;
    }
    public String getStyle(boolean selected) {
        if(selected && styleSelected!=null && styleSelected.length()>0) {
            return styleSelected;
        }else {
            return style;
        }
    }

    public String getItemId() {
        return "";
    }

    public int getIndent() {
        return -1;
    }

    public void release() {
        super.release();    //To change body of overriden methods use Options | File Templates.
        style = null;
        styleSelected = null;
        indentWidth = null;
        indentImage = null;
        menuStyle = null;
    }

    public int doStartTag() throws JspException {
        try {
            if(menuStyle!=null) {
                pageContext.getOut().write("<table style=\""+menuStyle+"\">");
            }else {
                pageContext.getOut().write("<table>");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print(bodyContent.getString());
            pageContext.getOut().write("</table>");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return EVAL_PAGE;
    }
}