package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;


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

public class SearchPictureFB extends SelectPictureFB {
    private Integer[] categories;
    private Boolean allCategories;
    private Date dateAfter;
    private Date dateBefore;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Boolean getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(Boolean allCategories) {
        this.allCategories = allCategories;
    }

    public String getAllCategoriesDisplay() {
        return StringUtil.asString(allCategories,null);
    }

    public void setAllCategoriesDisplay(String allCategoriesDisplay) {
        this.allCategories = StringUtil.asBoolean(allCategoriesDisplay,Boolean.FALSE);
    }

    public Integer[] getCategories() {
        return categories;
    }

    public void setCategories(Integer[] categories) {
        this.categories = categories;
    }

    public String[] getCategoriesDisplay() {
        if(categories!=null) {
            String[] result = new String[categories.length];
            for (int i = 0; i < categories.length; i++) {
                result[i] = StringUtil.asString(categories[i],null);
            }
            return result;
        }else {
            return null;
        }
    }

    public void setCategoriesDisplay(String[] categoriesDisplay) {
        if(categoriesDisplay!=null) {
            this.categories = new Integer[categoriesDisplay.length];
            for (int i = 0; i < categoriesDisplay.length; i++) {
                this.categories[i] = StringUtil.asInteger(categoriesDisplay[i],null);
            }
        }else {
            this.categories = null;
        }
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public String getDateAfterDisplay() {
        return StringUtil.asString(dateAfter,"");
    }

    public void setDateAfterDisplay(String dateAfterDisplay) {
        this.dateAfter = StringUtil.asDate(dateAfterDisplay,null);
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public String getDateBeforeDisplay() {
        return StringUtil.asString(dateBefore,"");
    }

    public void setDateBeforeDisplay(String dateBeforeDisplay) {
        this.dateBefore = StringUtil.asDate(dateBeforeDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        categories = null;
        allCategories = Boolean.FALSE;
        dateBefore = null;
        dateAfter = null;
    }
}