package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


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
        return allCategories!=null?allCategories.toString():null;
    }

    public void setAllCategoriesDisplay(String allCategoriesDisplay) {
        this.allCategories = Boolean.valueOf(allCategoriesDisplay);
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
                result[i] = categories[i]!=null?categories[i].toString():null;
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
                try {
                    this.categories[i] = Integer.valueOf(categoriesDisplay[i]);
                } catch (NumberFormatException e) {
                    this.categories[i] = null;
                }
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
        return dateAfter!=null?dateFormat.format(dateAfter):"";
    }

    public void setDateAfterDisplay(String dateAfterDisplay) {
        try {
            this.dateAfter = dateFormat.parse(dateAfterDisplay);
        } catch (ParseException e) {
            this.dateAfter = null;
        }
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public String getDateBeforeDisplay() {
        return dateBefore!=null?dateFormat.format(dateBefore):"";
    }

    public void setDateBeforeDisplay(String dateBeforeDisplay) {
        try {
            this.dateBefore = dateFormat.parse(dateBeforeDisplay);
        } catch (ParseException e) {
            this.dateBefore = null;
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        categories = null;
        allCategories = Boolean.FALSE;
        dateBefore = null;
        dateAfter = null;
    }
}