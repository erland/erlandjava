package erland.webapp.common.tags;

import erland.util.StringUtil;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.ServletParameterHelper;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.RequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

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

public class HelpLinkTag extends TagSupport {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(HelpLinkTag.class);
    private String style;
    private String target;
    private String onClickMessage;
    private String onClickMessageKey;
    private String title;
    private String titleKey;
    private String context;
    private String link;

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setOnClickMessage(String onClickMessage) {
        this.onClickMessage = onClickMessage;
    }

    public void setOnClickMessageKey(String onClickMessageKey) {
        this.onClickMessageKey = onClickMessageKey;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setContext(String context) {
        this.context = context;
    }
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if(LOG.isTraceEnabled()) {
            LOG.trace(StringUtil.beanToString(this,null,TagSupport.class,true));
        }
        link=WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("helppath");

        String onClickMessage = this.onClickMessage;
        if(onClickMessage==null && onClickMessageKey!=null) {
            onClickMessage = RequestUtils.message(pageContext,null,null,onClickMessageKey);
        }
        String title = this.title;
        if(title==null && titleKey!=null) {
            title = RequestUtils.message(pageContext,null,null,titleKey);
        }
        if(link!=null && link.length()>0) {
            Map parameters = new HashMap();
            if(context!=null) {
                parameters.put("context",context);
            }
            link = ServletParameterHelper.replaceDynamicParameters(link,parameters);

            try {
                out.write("<a href=\""+addContextPath(link)+"\" "+(style!=null?"class=\""+style+"\" ":"")+" "+(target!=null?" target=\""+target+"\"":"")+(onClickMessage!=null?" onClick=\"return confirm('"+onClickMessage+"')\"":"")+(title!=null?" title=\""+title+"\" ":"")+">");
                return EVAL_BODY_INCLUDE;
            } catch (IOException e) {
                link = null;
                LOG.error("Unable to write html",e);
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            if(link!=null) {
                pageContext.getOut().write("</a>");
            }
        } catch (IOException e) {
            LOG.error("Unable to write html",e);
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        link = null;
        style = null;
        target = null;
        onClickMessage = null;
        onClickMessageKey = null;
        title = null;
        titleKey = null;
        context = null;
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