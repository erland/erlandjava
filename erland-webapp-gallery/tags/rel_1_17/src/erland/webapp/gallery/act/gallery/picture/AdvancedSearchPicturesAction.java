package erland.webapp.gallery.act.gallery.picture;

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
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.fb.gallery.picture.SearchPictureFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class AdvancedSearchPicturesAction extends SearchPicturesAction {
    private final static String NUMBER_OF_CATEGORIES = AdvancedSearchPicturesAction.class + "-numberOfCategories";
    private final static String ALL_CATEGORIES = AdvancedSearchPicturesAction.class + "-allCategories";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchPictureFB fb = (SearchPictureFB) form;
        setAllCategories(request,fb.getAllCategories());
        super.executeLogic(mapping, form, request, response);    
    }

    protected Collection getCategories(HttpServletRequest request, ActionForm form) {
        SearchPictureFB fb = (SearchPictureFB) form;
        if (fb.getCategories() != null && fb.getCategories().length > 0) {
            Collection result = new HashSet();
            for (int i = 0; i < fb.getCategories().length; i++) {
                Integer category = fb.getCategories()[i];
                if (fb.getAllCategories().booleanValue()) {
                    result.add(category);
                } else {
                    Category[] categories = getCategoryTree(getGalleryId(request), category);
                    for (int j = 0; j < categories.length; j++) {
                        result.add(categories[j].getCategory());
                    }
                }
            }
            setNumberOfCategories(request,new Integer(result.size()));
            return result;
        }
        return null;
    }

    protected void setFilterAttributes(HttpServletRequest request, ActionForm form, QueryFilter filter) {
        SearchPictureFB fb = (SearchPictureFB) form;
        Date dateBefore = fb.getDateBefore();
        if (dateBefore == null) {
            dateBefore = new Date();
        }
        Date dateAfter = fb.getDateAfter();
        if (dateAfter == null) {
            dateAfter = new Date(0);
        }
        filter.setAttribute("dateafter", dateAfter);
        filter.setAttribute("datebefore", dateBefore);
        if (getNumberOfCategories(request) != null && fb.getAllCategories().booleanValue()) {
            filter.setAttribute("numberofcategories", getNumberOfCategories(request));
        }
    }

    protected String getAllFilter(HttpServletRequest request) {
        return "allforgallerybetweendates";
    }

    protected String getCategoryTreeFilter(HttpServletRequest request) {
        if (!getAllCategories(request).booleanValue()) {
            return "allforgalleryandcategorylistbetweendates";
        } else {
            return "allforgalleryandcategorylistallrequiredbetweendates";
        }
    }

    protected Boolean getAllCategories(HttpServletRequest request) {
        return (Boolean) request.getAttribute(ALL_CATEGORIES);
    }
    protected void setAllCategories(HttpServletRequest request, Boolean allCategories) {
        request.setAttribute(ALL_CATEGORIES,allCategories);
    }
    protected Integer getNumberOfCategories(HttpServletRequest request) {
        return (Integer) request.getAttribute(NUMBER_OF_CATEGORIES);
    }
    protected void setNumberOfCategories(HttpServletRequest request, Integer numberOfCategories) {
        request.setAttribute(NUMBER_OF_CATEGORIES, numberOfCategories);
    }
}