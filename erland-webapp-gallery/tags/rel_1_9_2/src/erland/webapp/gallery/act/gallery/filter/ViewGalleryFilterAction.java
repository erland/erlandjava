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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.entity.gallery.filter.Filter;
import erland.webapp.gallery.entity.gallery.filter.GalleryFilter;
import erland.webapp.gallery.fb.gallery.filter.FilterFB;
import erland.webapp.gallery.fb.gallery.filter.GalleryFilterFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewGalleryFilterAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GalleryFilterFB fb = (GalleryFilterFB) form;
        GalleryFilter template = (GalleryFilter) getEnvironment().getEntityFactory().create("gallery-galleryfilter");
        template.setId(fb.getId());
        GalleryFilter filter = (GalleryFilter) getEnvironment().getEntityStorageFactory().getStorage("gallery-galleryfilter").load(template);
        PropertyUtils.copyProperties(fb, filter);
        Filter filterTemplate = (Filter) getEnvironment().getEntityFactory().create("gallery-filter");
        filterTemplate.setId(filter.getFilter());
        Filter filterEntity = (Filter) getEnvironment().getEntityStorageFactory().getStorage("gallery-filter").load(filterTemplate);
        if(filterEntity!=null) {
            fb.setName(filterEntity.getName());
        }

        QueryFilter query = new QueryFilter("all");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-filter").search(query);
        FilterFB[] pb = new FilterFB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new FilterFB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.getSession().setAttribute("filtersPB", pb);
    }
}