package erland.webapp.common.tags.menu;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.StringTokenizer;

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

public class MenuItemSeparatorTag extends BodyTagSupport implements MenuItemInterface {
    private String id;
    private String style;
    private String styleSelected;
    private String roles;

    public void setStyleSelected(String styleSelected) {
        this.styleSelected = styleSelected;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        String itemId = null;
        if(itemId==null) {
            MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this,MenuItemInterface.class);
            if(parentItem!=null && parentItem.getItemId().length()>0) {
                itemId = parentItem.getItemId()+"-"+id;
            }else {
                itemId = id;
            }
            System.out.println(""+id+".getItemId()="+itemId);
        }
        return itemId;
    }

    public String getParentId() {
        String parentId = null;
        if(parentId==null) {
            MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this,MenuItemInterface.class);
            if(parentItem!=null) {
                parentId = parentItem.getItemId();
            }else {
                parentId = "";
            }
            System.out.println(""+id+".getParentId()="+parentId);
        }
        return parentId;
    }

    private String getMenuId() {
        MenuTag parentItem = (MenuTag) findAncestorWithClass(this,MenuTag.class);
        if(parentItem!=null) {
            return parentItem.getId();
        }
        return "";
    }

    private String getIndentImage() {
        MenuTag parentItem = (MenuTag) findAncestorWithClass(this,MenuTag.class);
        if(parentItem!=null) {
            return parentItem.getIndentImage();
        }
        return null;
    }

    public int getIndent() {
        int indent = -1;
        if(indent==-1) {
            MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this,MenuItemInterface.class);
            if(parentItem!=null) {
                indent = parentItem.getIndent()+1;
            }else {
                indent = 0;
            }
        }
        return indent;
    }

    private int getIndentWidth() {
        MenuTag parentItem = (MenuTag) findAncestorWithClass(this,MenuTag.class);
        if(parentItem!=null) {
            return parentItem.getIndentWidth().intValue();
        }
        return 20;
    }

    public String getStyle(boolean selected) {
        if(selected) {
            if(styleSelected!=null && styleSelected.length()>0) {
                return styleSelected;
            }else {
                MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this,MenuItemInterface.class);
                if(parentItem!=null) {
                    return parentItem.getStyle(selected);
                }else {
                    return style;
                }
            }
        }else {
            if(style!=null && style.length()>0) {
                return style;
            }else {
                MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this,MenuItemInterface.class);
                if(parentItem!=null) {
                    return parentItem.getStyle(selected);
                }else {
                    return style;
                }
            }
        }
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String menuObjName = getMenuId();
        String menuObj = (String) pageContext.getAttribute(menuObjName,PageContext.SESSION_SCOPE);
        if((getParentId().length()==0 || (menuObj!=null && menuObj.startsWith(getParentId()))) && (isUserInRole(roles))) {
            try {
                out.write("<tr>");
                int indent = getIndent();
                out.write("<td nowrap>");
                String style = getStyle(false);
                if(indent>0) {
                    out.write("<img src=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+getIndentImage()+"\" width=\""+(getIndentWidth()*indent)+"\" height=\"1\"></img>");
                }
                out.write("<p "+(style!=null?"class=\""+style+"\" ":"")+">&nbsp;</p>");
                out.write("</td>");
                out.write("</tr>");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        id = null;
        style = null;
        styleSelected = null;
        roles = null;
    }

    private boolean isUserInRole(String roles) {
        if(roles!=null && roles.length()>0) {
            StringTokenizer tokens = new StringTokenizer(roles,",");
            while(tokens.hasMoreTokens()) {
                String role = tokens.nextToken();
                if(((HttpServletRequest)pageContext.getRequest()).isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}