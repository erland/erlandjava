package erland.webapp.common.tags;

import erland.webapp.common.html.HTMLEncoder;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.RequestUtils;

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

public class BeanLinkTag extends BodyTagSupport {
    private String style;
    private String styleSelected;
    private String name;
    private String property;
    private String nameSelected;
    private String propertySelected;
    private String selectedValue = "true";
    private String link;
    private String target;
    private String onClickMessage;
    private String onClickMessageKey;
    private String title;
    private String titleKey;
    private String propertyTitle;
    private String propertyTitleKey;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleSelected() {
        return styleSelected;
    }

    public void setStyleSelected(String styleSelected) {
        this.styleSelected = styleSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getNameSelected() {
        return nameSelected;
    }

    public void setNameSelected(String nameSelected) {
        this.nameSelected = nameSelected;
    }

    public String getPropertySelected() {
        return propertySelected;
    }

    public void setPropertySelected(String propertySelected) {
        this.propertySelected = propertySelected;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOnClickMessage() {
        return onClickMessage;
    }

    public void setOnClickMessage(String onClickMessage) {
        this.onClickMessage = onClickMessage;
    }

    public String getOnClickMessageKey() {
        return onClickMessageKey;
    }

    public void setOnClickMessageKey(String onClickMessageKey) {
        this.onClickMessageKey = onClickMessageKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public String getPropertyTitleKey() {
        return propertyTitleKey;
    }

    public void setPropertyTitleKey(String propertyTitleKey) {
        this.propertyTitleKey = propertyTitleKey;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        Object bean = pageContext.findAttribute(name);
        Object beanSelected = bean;
        if(styleSelected!=null && nameSelected!=null) {
            beanSelected = pageContext.findAttribute(nameSelected);
        }
        if(property!=null) {
            try {
                link = (String) PropertyUtils.getProperty(bean,property);
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }else {
            link=(String) bean;
        }
        Object selected = null;
        if(styleSelected!=null) {
            if(propertySelected!=null) {
                try {
                    selected = PropertyUtils.getProperty(beanSelected,propertySelected);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }else {
                try {
                    selected = PropertyUtils.getProperty(beanSelected,property);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        if(styleSelected!=null && (selectedValue==selected || (selectedValue!=null && selectedValue.equalsIgnoreCase(String.valueOf(selected))))) {
            style = styleSelected;
        }
        if(onClickMessage==null && onClickMessageKey!=null) {
            onClickMessage = RequestUtils.message(pageContext,null,null,onClickMessageKey);
        }
        if(title==null && titleKey!=null) {
            title = RequestUtils.message(pageContext,null,null,titleKey);
        }
        if(title==null && propertyTitle!=null) {
            try {
                Object value = PropertyUtils.getProperty(bean,propertyTitle);
                if(value!=null) {
                    title = String.valueOf(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if(title==null && propertyTitleKey!=null) {
            try {
                Object key = PropertyUtils.getProperty(bean,propertyTitleKey);
                if(key!=null) {
                    title = RequestUtils.message(pageContext,null,null,String.valueOf(key));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if(link!=null) {
            try {
                out.write("<a href=\""+addContextPath(link)+"\" "+(style!=null?"class=\""+style+"\" ":"")+" "+(target!=null?" target=\""+target+"\"":"")+(onClickMessage!=null?" onClick=\"return confirm('"+onClickMessage+"')\"":"")+(title!=null?" title=\""+title+"\" ":"")+">");
                return EVAL_BODY_BUFFERED;
            } catch (IOException e) {
                link = null;
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            if(link!=null) {
                pageContext.getOut().print(bodyContent.getString());
                pageContext.getOut().write("</a>");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        link = null;
        style = null;
        styleSelected = null;
        name = null;
        property = null;
        nameSelected = null;
        propertySelected = null;
        selectedValue = "true";
        target = null;
        onClickMessage = null;
        onClickMessageKey = null;
        title = null;
        titleKey = null;
        propertyTitle = null;
        propertyTitleKey = null;
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
}
