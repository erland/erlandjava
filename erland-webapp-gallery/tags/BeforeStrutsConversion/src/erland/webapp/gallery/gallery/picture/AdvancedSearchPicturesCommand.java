package erland.webapp.gallery.gallery.picture;
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

import erland.webapp.common.QueryFilter;
import erland.webapp.gallery.gallery.category.Category;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

public class AdvancedSearchPicturesCommand extends SearchPicturesCommand {
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Boolean allCategories;
    private Integer numberOfCategories;
    protected Collection getCategories(HttpServletRequest request) {
        String[] categoryIds = request.getParameterValues("categories");
        if(categoryIds!=null && categoryIds.length>0) {
            Collection result = new HashSet();
            for (int i = 0; i < categoryIds.length; i++) {
                Integer category = Integer.valueOf(categoryIds[i]);
                if(allCategories.booleanValue()) {
                    result.add(category);
                }else {
                    Category[] categories = getCategoryTree(getGalleryId(),category);
                    for (int j = 0; j < categories.length; j++) {
                        result.add(categories[j].getCategory());
                    }
                }
            }
            numberOfCategories = new Integer(result.size());
            return result;
        }
        return null;
    }

    protected void setFilterAttributes(HttpServletRequest request, QueryFilter filter) {
        String dateBeforeString = request.getParameter("datebefore");
        Date dateBefore = null;
        if(dateBeforeString!=null && dateBeforeString.length()>0) {
            try {
                dateBefore = dateFormat.parse(dateBeforeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(dateBefore==null) {
            dateBefore = new Date();
        }
        String dateAfterString = request.getParameter("dateafter");
        Date dateAfter = null;
        if(dateAfterString!=null && dateAfterString.length()>0) {
            try {
                dateAfter = dateFormat.parse(dateAfterString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(dateAfter==null) {
            dateAfter = new Date(0);
        }
        filter.setAttribute("dateafter",dateAfter);
        filter.setAttribute("datebefore",dateBefore);
        if(numberOfCategories!=null && allCategories.booleanValue()) {
            filter.setAttribute("numberofcategories",numberOfCategories);
        }
    }

    protected String getAllFilter() {
        return "allforgallerybetweendates";
    }

    protected Boolean getAllCategories() {
        return allCategories;
    }
    protected String getCategoryTreeFilter() {
        if(!allCategories.booleanValue()) {
            return "allforgalleryandcategorylistbetweendates";
        }else {
            return "allforgalleryandcategorylistallrequiredbetweendates";
        }
    }

    protected void initRequestParameters(HttpServletRequest request) {
        String allCategoriesString = request.getParameter("allcategories");
        allCategories = Boolean.FALSE;
        if(allCategoriesString!=null && allCategoriesString.equalsIgnoreCase("true")) {
            allCategories = Boolean.TRUE;
        }
    }
}