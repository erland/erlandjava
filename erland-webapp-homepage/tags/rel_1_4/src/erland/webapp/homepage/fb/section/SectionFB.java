package erland.webapp.homepage.fb.section;

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

public class SectionFB extends BaseFB {
    private String idDisplay;
    private String parentDisplay;
    private String orderNoDisplay;
    private String name;
    private String nameEnglish;
    private String title;
    private String titleEnglish;
    private String description;
    private String descriptionEnglish;
    private String serviceDisplay;
    private String serviceParameters;
    private String officialDisplay;
    private String hosts;
    private String ipAddr;

    public Integer getId() {
        return StringUtil.asInteger(idDisplay, null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id, null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public Integer getOrderNo() {
        return StringUtil.asInteger(orderNoDisplay,null);
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNoDisplay = StringUtil.asString(orderNo,null);
    }

    public String getOrderNoDisplay() {
        return orderNoDisplay;
    }

    public void setOrderNoDisplay(String orderNoDisplay) {
        this.orderNoDisplay = orderNoDisplay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public Integer getParent() {
        return StringUtil.asInteger(parentDisplay, null);
    }

    public void setParent(Integer parent) {
        this.parentDisplay = StringUtil.asString(parent, null);
    }

    public String getParentDisplay() {
        return parentDisplay;
    }

    public void setParentDisplay(String parentDisplay) {
        this.parentDisplay = parentDisplay;
    }

    public Boolean getOfficial() {
        return StringUtil.asBoolean(officialDisplay, Boolean.FALSE);
    }

    public void setOfficial(Boolean official) {
        this.officialDisplay = StringUtil.asString(official, null);
    }

    public String getOfficialDisplay() {
        return officialDisplay;
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.officialDisplay = officialDisplay;
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

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        idDisplay = null;
        parentDisplay = null;
        name = null;
        nameEnglish = null;
        orderNoDisplay = null;
        title = null;
        titleEnglish = null;
        description = null;
        descriptionEnglish = null;
        officialDisplay = null;
        serviceDisplay = null;
        serviceParameters = null;
        hosts = null;
        ipAddr = null;
    }
}