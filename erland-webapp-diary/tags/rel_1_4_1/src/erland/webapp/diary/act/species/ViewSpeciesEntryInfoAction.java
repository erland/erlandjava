package erland.webapp.diary.act.species;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.diary.fb.gallery.GalleryPB;
import erland.webapp.diary.fb.species.SpeciesPB;
import erland.webapp.diary.fb.species.SelectSpeciesFB;
import erland.webapp.diary.fb.inventory.DescriptionIdPB;
import erland.webapp.diary.entity.species.Species;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.diary.logic.appendix.SourceAppendixStringReplace;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ViewSpeciesEntryInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectSpeciesFB fb = (SelectSpeciesFB) form;
        Species template = (Species) getEnvironment().getEntityFactory().create("diary-species");
        template.setId(fb.getId());
        Species entry = (Species) getEnvironment().getEntityStorageFactory().getStorage("diary-species").load(template);

        SpeciesPB pb = new SpeciesPB();
        PropertyUtils.copyProperties(pb,entry);

        Map parameters = new HashMap();
        parameters.put("gallery",pb.getGallery());
        String user = request.getRemoteUser();
        if(user==null) {
            user=(String) request.getSession().getAttribute("user");
        }
        parameters.put("user",user);
        parameters.put("id",entry.getId());
        parameters.put("gallery",entry.getGallery());

        ActionForward forward = mapping.findForward("view-gallery-link");
        if(forward!=null && entry.getGallery()!=null && entry.getGallery().intValue()!=0) {
            pb.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("view-entry-link");
        if(forward!=null) {
            pb.setViewLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("update-entry-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("delete-entry-link");
        if(forward!=null) {
            pb.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        if(StringUtil.asNull(pb.getLink())!=null) {
            String link = new SourceAppendixStringReplace().replace(pb.getLink());
            if(!pb.getLink().equals(link)) {
                pb.setLinkSource(link);
            }
        }

        request.setAttribute("speciesEntryPB",pb);

        DescriptionId[] types = DescriptionIdHelper.getDescriptionIdList("diary-speciestype");
        DescriptionIdPB[] typesPB = new DescriptionIdPB[types.length];
        for (int i = 0; i < types.length; i++) {
            typesPB[i] = new DescriptionIdPB();
            PropertyUtils.copyProperties(typesPB[i],types[i]);
        }
        request.getSession().setAttribute("speciesTypesPB",typesPB);

        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").search(filter);
        GalleryPB[] pbGalleries= new GalleryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pbGalleries[i] = new GalleryPB();
            PropertyUtils.copyProperties(pbGalleries[i],entities[i]);
        }
        request.getSession().setAttribute("galleriesPB",pbGalleries);
    }
}
