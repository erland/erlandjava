package erland.webapp.tvguide.fb.service;

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

import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ServiceFB extends BaseFB {
    private String idDisplay;
    private String name;
    private String serviceClass;
    private String serviceData;
    private String customizedServiceData;
    private String transformerClass;
    private String transformerData;
    private String customizedTransformerData;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServiceData() {
        return serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
    }

    public String getCustomizedServiceData() {
        return customizedServiceData;
    }

    public void setCustomizedServiceData(String customizedServiceData) {
        this.customizedServiceData = customizedServiceData;
    }

    public String getTransformerClass() {
        return transformerClass;
    }

    public void setTransformerClass(String transformerClass) {
        this.transformerClass = transformerClass;
    }

    public String getTransformerData() {
        return transformerData;
    }

    public void setTransformerData(String transformerData) {
        this.transformerData = transformerData;
    }

    public String getCustomizedTransformerData() {
        return customizedTransformerData;
    }

    public void setCustomizedTransformerData(String customizedTransformerData) {
        this.customizedTransformerData = customizedTransformerData;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        idDisplay = null;
        name = null;
        serviceClass = null;
        serviceData = null;
        customizedServiceData = null;
        transformerClass = null;
        transformerData = null;
        customizedTransformerData = null;
    }
}