package erland.webapp.gallery.act.gallery;

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
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.webapp.gallery.fb.gallery.SelectGalleryFB;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchGalleriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectGalleryFB fb = (SelectGalleryFB) form;
        String username = request.getRemoteUser();
        String language = fb.getLanguage();
        if(StringUtil.asNull(language)==null) {
            language = request.getLocale().getLanguage();
        }
        boolean useEnglish = !language.equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        if (fb != null) {
            username = fb.getUser();
        }
        Gallery[] entities = GalleryHelper.searchGalleries(getEnvironment(),getEntityName(),username,getQueryFilter(request));
        GalleryPB[] pb = new GalleryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new GalleryPB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
            if(useEnglish && StringUtil.asNull(entities[i].getTitleEnglish())!=null) {
                pb[i].setTitle(entities[i].getTitleEnglish());
            }
            if(useEnglish && StringUtil.asNull(entities[i].getDescriptionEnglish())!=null) {
                pb[i].setDescription(entities[i].getDescriptionEnglish());
            }
        }
        request.setAttribute("galleriesPB", pb);
    }

    protected String getEntityName() {
        return "gallery-gallery";
    }

    protected String getQueryFilter(HttpServletRequest request) {
        return "allforuser";
    }
}
