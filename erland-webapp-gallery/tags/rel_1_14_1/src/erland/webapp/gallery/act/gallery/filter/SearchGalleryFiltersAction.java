package erland.webapp.gallery.act.gallery.filter;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.fb.gallery.filter.FilterFB;
import erland.webapp.gallery.fb.gallery.filter.GalleryFilterFB;
import erland.webapp.gallery.fb.gallery.filter.GalleryFilterCollectionPB;
import erland.webapp.gallery.fb.gallery.SelectGalleryFB;
import erland.webapp.gallery.entity.gallery.filter.Filter;
import erland.webapp.gallery.entity.gallery.filter.GalleryFilter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchGalleryFiltersAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectGalleryFB fb = (SelectGalleryFB) form;
        QueryFilter filter = new QueryFilter("allpreforgallery");
        filter.setAttribute("gallery",fb.getGallery());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-galleryfilter").search(filter);
        GalleryFilterFB[] pbFilters = new GalleryFilterFB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pbFilters[i] = new GalleryFilterFB();
            PropertyUtils.copyProperties(pbFilters[i], entities[i]);

            Filter filterTemplate = (Filter) getEnvironment().getEntityFactory().create("gallery-filter");
            filterTemplate.setId(((GalleryFilter)entities[i]).getFilter());
            Filter filterEntity = (Filter) getEnvironment().getEntityStorageFactory().getStorage("gallery-filter").load(filterTemplate);
            if(filterEntity!=null) {
                pbFilters[i].setName(filterEntity.getName());
            }
        }
        GalleryFilterCollectionPB pb = new GalleryFilterCollectionPB();
        pb.setFilters(pbFilters);
        ActionForward forward = mapping.findForward("new-filter-link");
        if(forward!=null) {
            Map parameters = new HashMap();
            parameters.put("gallery",fb.getGallery());
            pb.setNewLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        request.setAttribute("galleryFiltersPB", pb);
    }
}