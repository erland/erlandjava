package erland.webapp.gallery.fb.gallery.filter;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class GalleryFilterFB extends BaseFB {
    private Integer id;
    private Integer gallery;
    private Integer filter;
    private Integer orderNo;
    private String name;
    private String parameters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return StringUtil.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = StringUtil.asInteger(idDisplay,null);
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return StringUtil.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = StringUtil.asInteger(galleryDisplay,null);
    }

    public Integer getFilter() {
        return filter;
    }

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    public String getFilterDisplay() {
        return StringUtil.asString(filter,null);
    }

    public void setFilterDisplay(String filterDisplay) {
        this.filter = StringUtil.asInteger(filterDisplay,null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNoDisplay() {
        return StringUtil.asString(orderNo,null);
    }

    public void setOrderNoDisplay(String orderNoDisplay) {
        this.orderNo = StringUtil.asInteger(orderNoDisplay,null);
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        gallery = null;
        filter = null;
        name = null;
        orderNo = null;
        parameters = null;
    }
}