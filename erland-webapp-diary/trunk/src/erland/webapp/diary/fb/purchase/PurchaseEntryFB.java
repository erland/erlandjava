package erland.webapp.diary.fb.purchase;

import org.apache.struts.action.ActionMapping;

import java.util.Date;

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;

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

public class PurchaseEntryFB extends BaseFB {
    private Integer id;
    private Date date;
    private String store;
    private Integer category;
    private String categoryDescription;
    private String description;
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return ServletParameterHelper.asString(date,null);
    }

    public void setDateDisplay(String dateDisplay) {
        this.date = ServletParameterHelper.asDate(dateDisplay,null);
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDisplay() {
        return ServletParameterHelper.asString(category,null);
    }

    public void setCategoryDisplay(String categoryDisplay) {
        this.category = ServletParameterHelper.asInteger(categoryDisplay,null);
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceDisplay() {
        return ServletParameterHelper.asString(price,null);
    }

    public void setPriceDisplay(String priceDisplay) {
        this.price = ServletParameterHelper.asDouble(priceDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        date = null;
        store = null;
        category = null;
        description = null;
        price = null;
    }
}