package erland.webapp.common.tags;

import erland.webapp.common.html.HTMLEncoder;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

public class BeanImageTag extends TagSupport {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(BeanImageTag.class);
    private String name;
    private String property;
    private String border;
    private String style;
    private String width;
    private String height;
    private String parameters;

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if(LOG.isTraceEnabled()) {
            LOG.trace(StringUtil.beanToString(this,null,TagSupport.class,true));
        }
        Object bean = pageContext.findAttribute(name);
        String link = null;
        if(bean!=null && property!=null) {
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
        if(link!=null && link.length()>0) {
            if(StringUtil.asNull(getParameters())!=null) {
                link = ServletParameterHelper.replaceParametersInUrl(link,getParameters());
            }
            try {
                out.write("<img src=\""+addContextPath(link)+"\" "+(style!=null?"class=\""+style+"\" ":"")+" "+(border!=null?" border=\""+border+"\"":"")+(width!=null?" width=\""+width+"\"":"")+(height!=null?" height=\""+height+"\"":"")+"></img>");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        style = null;
        border = null;
        name = null;
        property = null;
        width = null;
        height = null;
        parameters = null;
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
