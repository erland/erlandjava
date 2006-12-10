package erland.webapp.datacollection.fb.entry.data;

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

import erland.util.StringUtil;
import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class DataFB extends BaseFB {
    private String entryIdDisplay;
    private String idDisplay;
    private String type;
    private String content;
    private String url;

    public String getEntryIdDisplay() {
        return entryIdDisplay;
    }

    public void setEntryIdDisplay(String entryIdDisplay) {
        this.entryIdDisplay = entryIdDisplay;
    }

    public Integer getEntryId() {
        return StringUtil.asInteger(entryIdDisplay,null);
    }

    public void setEntryId(Integer entryId) {
        this.entryIdDisplay = StringUtil.asString(entryId,null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        entryIdDisplay = null;
        idDisplay = null;
        type = null;
        content = null;
        url = null;
    }
}