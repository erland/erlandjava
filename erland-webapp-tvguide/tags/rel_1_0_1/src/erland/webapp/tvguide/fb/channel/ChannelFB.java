package erland.webapp.tvguide.fb.channel;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

public class ChannelFB extends BaseFB {
    private String idDisplay;
    private String tag;
    private String name;
    private String description;
    private String logo;
    private String link;
    private String serviceDisplay;
    private String serviceParameters;

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getService() {
        return StringUtil.asInteger(serviceDisplay,null);
    }

    public void setService(Integer service) {
        this.serviceDisplay = StringUtil.asString(service,null);
    }

    public String getServiceDisplay() {
        return serviceDisplay;
    }

    public void setServiceDisplay(String serviceDisplay) {
        this.serviceDisplay = serviceDisplay;
    }

    public String getServiceParameters() {
        return serviceParameters;
    }

    public void setServiceParameters(String serviceParameters) {
        this.serviceParameters = serviceParameters;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        idDisplay = null;
        tag = null;
        name = null;
        description = null;
        logo = null;
        link = null;
        serviceDisplay = null;
        serviceParameters = null;
    }
}
