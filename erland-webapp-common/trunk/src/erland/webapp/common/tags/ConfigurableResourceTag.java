package erland.webapp.common.tags;

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.Log;
import erland.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
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

public class ConfigurableResourceTag extends TagSupport {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        if(Log.isEnabled(this,Log.DEBUG)) {
            Log.println(this,StringUtil.beanToString(this,null,TagSupport.class,true));
        }
        try {
            // getJspWriter to output content
            JspWriter out = pageContext.getOut();
            if (out!= null) {
                String language = pageContext.getRequest().getLocale().getLanguage();
                String value = null;
                if(language!=null) {
                    value = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter(name+"_"+language);
                }
                if(value==null) {
                    value = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter(name);
                }
                if(value!=null) {
                    out.print(value);
                }
            }
        } catch (IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }
        return SKIP_BODY;
    }
}