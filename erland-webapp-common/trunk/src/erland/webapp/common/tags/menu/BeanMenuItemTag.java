package erland.webapp.common.tags.menu;

import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.beans.PropertyDescriptor;
import java.net.URL;
import java.net.MalformedURLException;

import erland.webapp.common.ServletParameterHelper;

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

public class BeanMenuItemTag extends TagSupport {
    private String bean;
    private String childs;
    private String page;
    private String titleKey;
    private String id;
    private String style;
    private String styleSelected;
    private String roles;

    public void setBean(String bean) {
        this.bean = bean;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public void setStyleSelected(String styleSelected) {
        this.styleSelected = styleSelected;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        String parentId = null;
        if (parentId == null) {
            MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this, MenuItemInterface.class);
            if (parentItem != null) {
                parentId = parentItem.getItemId();
            } else {
                parentId = "";
            }
            System.out.println("" + id + ".getParentId()=" + parentId);
        }
        return parentId;
    }

    private String getMenuId() {
        MenuTag parentItem = (MenuTag) findAncestorWithClass(this, MenuTag.class);
        if (parentItem != null) {
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
        if (indent == -1) {
            MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this, MenuItemInterface.class);
            if (parentItem != null) {
                indent = parentItem.getIndent() + 1;
            } else {
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
        if (selected) {
            if (styleSelected != null && styleSelected.length() > 0) {
                return styleSelected;
            } else {
                MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this, MenuItemInterface.class);
                if (parentItem != null) {
                    return parentItem.getStyle(selected);
                } else {
                    return style;
                }
            }
        } else {
            if (style != null && style.length() > 0) {
                return style;
            } else {
                MenuItemInterface parentItem = (MenuItemInterface) findAncestorWithClass(this, MenuItemInterface.class);
                if (parentItem != null) {
                    return parentItem.getStyle(selected);
                } else {
                    return style;
                }
            }
        }
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String menuObjName = getMenuId();
        String menuObj = (String) pageContext.getAttribute(menuObjName, PageContext.SESSION_SCOPE);
        if ((getParentId().length() == 0 || (menuObj != null && menuObj.startsWith(getParentId()))) && (isUserInRole(roles))) {
            try {
                Object o = pageContext.getAttribute(bean, PageContext.SESSION_SCOPE);
                if (o instanceof Object[]) {
                    writeMenu((Object[]) o, getIndent(), getParentId(), menuObj, out);
                }else {
                    writeMenu(new Object[] {o}, getIndent(), getParentId(), menuObj, out);
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        page = null;
        titleKey = null;
        id = null;
        style = null;
        styleSelected = null;
        roles = null;
        bean = null;
        childs = null;
    }

    private String addContextPath(String link) {
        if(link==null) {
            return link;
        }
        if(Pattern.matches("[a-z]*:",link)) {
            return link;
        }else {
            return ((HttpServletRequest) pageContext.getRequest()).getContextPath()+link;
        }
    }
 
    private void writeMenu(Object[] items, int indent, String parentId, String menuObj, JspWriter out) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (int i = 0; i < items.length; i++) {
            Object item = items[i];
            String id = (String) PropertyUtils.getProperty(item, this.id);
            String title = (String) PropertyUtils.getProperty(item, this.titleKey);
            String page = (String) PropertyUtils.getProperty(item, this.page);
            if (parentId != null && parentId.length() > 0) {
                id = parentId + "-" + id;
            }
            boolean selected = false;
            if (menuObj != null && menuObj.equals(id)) {
                selected = true;
            }
            out.write("<tr>");
            out.write("<td nowrap>");
            String style = getStyle(selected);
            if(indent>0) {
                out.write("<img src=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+getIndentImage()+"\" width=\""+(getIndentWidth()*indent)+"\" height=\"1\"></img>");
            }
            out.write("<a " + (style != null ? "class=\"" + style + "\" " : "") + "href=\"" + addContextPath(ServletParameterHelper.replaceDynamicParameters((String) page, getParameterMap(item, getMenuId(), (String) id))) + "\">");
            if (title != null) {
                out.write((String) title);
            }
            out.write("</a>");
            out.write("</td>");
            out.write("</tr>");
            if(this.childs!=null && this.childs.length()>0 && menuObj!=null && menuObj.startsWith((String)id)) {
                Object childs = PropertyUtils.getProperty(item, this.childs);
                if (childs != null) {
                    if(childs instanceof Object[]) {
                        writeMenu((Object[]) childs, indent + 1, (String) id, menuObj, out);
                    }else  {
                        writeMenu( new Object[] {childs}, indent + 1, (String) id, menuObj, out);
                    }
                }
            }
        }
    }

    private Map getParameterMap(Object bean, String menuId, String menuItemId) {
        Map result = new HashMap();

        Enumeration attributes = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
        while (attributes.hasMoreElements()) {
            String name = (String) attributes.nextElement();
            Object val = pageContext.getAttribute(name,PageContext.SESSION_SCOPE);
            if(val!=null) {
                result.put(name, val);
            }
        }

        attributes = pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
        while (attributes.hasMoreElements()) {
            String name = (String) attributes.nextElement();
            Object val = pageContext.getAttribute(name,PageContext.REQUEST_SCOPE);
            if(val!=null) {
                result.put(name, val);
            }
        }

        attributes = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
        while (attributes.hasMoreElements()) {
            String name = (String) attributes.nextElement();
            Object val = pageContext.getAttribute(name,PageContext.PAGE_SCOPE);
            if(val!=null) {
                result.put(name, val);
            }
        }

        result.putAll(pageContext.getRequest().getParameterMap());
        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(bean);
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor property = properties[i];
            try {
                result.put(property.getName(),PropertyUtils.getProperty(bean,property.getName()));
            } catch (IllegalAccessException e) {
                //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (InvocationTargetException e) {
                //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        result.put("menuId",menuId);
        result.put("menuItemId",menuItemId);
        return result;
    }

    private boolean isUserInRole(String roles) {
        if (roles != null && roles.length() > 0) {
            StringTokenizer tokens = new StringTokenizer(roles, ",");
            while (tokens.hasMoreTokens()) {
                String role = tokens.nextToken();
                if (((HttpServletRequest) pageContext.getRequest()).isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}