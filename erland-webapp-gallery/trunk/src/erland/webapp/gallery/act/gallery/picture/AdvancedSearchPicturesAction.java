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
    private Integer numberOfCategories;
    private Boolean allCategories;

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchPictureFB fb = (SearchPictureFB) form;
        allCategories = fb.getAllCategories();
        super.executeLogic(mapping, form, request, response);    //To change body of overriden methods use Options | File Templates.
    }

    protected Collection getCategories(ActionForm form) {
        SearchPictureFB fb = (SearchPictureFB) form;
        if (fb.getCategories() != null && fb.getCategories().length > 0) {
            Collection result = new HashSet();
            for (int i = 0; i < fb.getCategories().length; i++) {
                Integer category = fb.getCategories()[i];
                if (fb.getAllCategories().booleanValue()) {
                    result.add(category);
                } else {
                    Category[] categories = getCategoryTree(getGalleryId(), category);
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

    protected void setFilterAttributes(ActionForm form, QueryFilter filter) {
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
        if (numberOfCategories != null && fb.getAllCategories().booleanValue()) {
            filter.setAttribute("numberofcategories", numberOfCategories);
        }
    }

    protected String getAllFilter() {
        return "allforgallerybetweendates";
    }

    protected Boolean getAllCategories() {
        return allCategories;
    }

    protected String getCategoryTreeFilter() {
        if (!allCategories.booleanValue()) {
            return "allforgalleryandcategorylistbetweendates";
        } else {
            return "allforgalleryandcategorylistallrequiredbetweendates";
        }
    }
}