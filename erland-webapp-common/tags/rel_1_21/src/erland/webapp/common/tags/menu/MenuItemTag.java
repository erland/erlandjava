package erland.webapp.common.tags.menu;

import erland.webapp.common.html.HTMLBasicStringReplace;
import erland.webapp.common.html.StringReplaceInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.MalformedURLException;

import org.apache.struts.util.RequestUtils;
import org.apache.commons.beanutils.PropertyUtils;
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

public class MenuItemTag extends TagSupport implements MenuItemInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(MenuItemTag.class);
    private String page;
    private String titleKey;
    private String id;
    private String style;
    private String styleSelected;
    private String target;
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

    public void setPage(String page) {
        this.page = page;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setTarget(String target) {
        this.target = target;
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
        if(LOG.isTraceEnabled()) {
            LOG.trace(StringUtil.beanToString(this,null,TagSupport.class,true));
        }
        String menuObjName = getMenuId();
        String menuObj = (String) pageContext.getAttribute(menuObjName,PageContext.SESSION_SCOPE);
        if((getParentId().length()==0 || (menuObj!=null && menuObj.startsWith(getParentId()) && (menuObj.equals(getParentId())|| menuObj.charAt(getParentId().length())=='-'))) && (isUserInRole(roles))) {
            boolean selected = false;
            if(menuObj!=null && menuObj.equals(getItemId())) {
                selected = true;
            }
            try {
                out.write("<tr>");
                int indent = getIndent();
                out.write("<td nowrap>");
                String style = getStyle(selected);
                if(indent>0) {
                    out.write("<img src=\""+addContextPath(getIndentImage())+"\" width=\""+(getIndentWidth()*indent)+"\" height=\"1\"></img>");
                }
                out.write("<a "+(style!=null?"class=\""+style+"\" ":"")+"href=\""+addContextPath(ServletParameterHelper.replaceDynamicParameters(page, getParameterMap(getMenuId(),getItemId())))+"\""+(target!=null?" target=\""+target+"\"":"")+"\">");
                if(titleKey!=null) {
                    String title = RequestUtils.message(pageContext,null,null,titleKey);
                    if(title!=null) {
                        out.write(title);
                    }
                }
                out.write("</a>");
                out.write("</td>");
                out.write("</tr>");
            } catch (IOException e) {
                LOG.error("Unable to write html",e);
            }
            if(menuObj!=null && menuObj.startsWith(getItemId())) {
                return EVAL_BODY_INCLUDE;
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
    }

    private Map getParameterMap(String menuId, String menuItemId) {
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
        result.put("menuId",menuId);
        result.put("menuItemId",menuItemId);
        return result;
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
    private String addContextPath(String link) {
        if(link==null) {
            return link;
        }
        if(Pattern.matches("[a-z]*:.*",link)) {
            return link;
        }else {
            return ((HttpServletRequest) pageContext.getRequest()).getContextPath()+link;
        }
    }
}