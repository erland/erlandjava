package erland.webapp.diary.act.gallery;

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
import erland.webapp.diary.entity.gallery.Gallery;
import erland.webapp.diary.entity.gallery.GalleryEntry;
import erland.webapp.diary.fb.gallery.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchGalleryEntriesAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectGalleryFB fb = (SelectGalleryFB) form;
        Gallery gallery = getGallery(fb.getGallery());
        GalleryPB pbGallery = new GalleryPB();
        PropertyUtils.copyProperties(pbGallery,gallery);

        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }
        Map parameters = new HashMap();
        parameters.put("gallery",gallery.getId());
        parameters.put("user",username);
        ActionForward forward = mapping.findForward("update-gallery-link");
        if(forward!=null) {
            pbGallery.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("delete-gallery-link");
        if(forward!=null) {
            pbGallery.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        if (gallery.getGallery() != null && gallery.getGallery().intValue() != 0) {
            request.setAttribute("galleryInfoPB",pbGallery);
            request.setAttribute("externalgallery",gallery.getGallery());
            return;
        } else {
            forward = mapping.findForward("new-picture-link");
            if(forward!=null) {
                pbGallery.setNewEntryLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            request.setAttribute("galleryInfoPB",pbGallery);

            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery", fb.getGallery());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-galleryentry").search(filter);

            ActionForward updateForward = mapping.findForward("update-picture-link");
            ActionForward deleteForward = mapping.findForward("delete-picture-link");
            GalleryEntryPB[] pbPictures = new GalleryEntryPB[entities.length];
            for (int i = 0; i < entities.length; i++) {
                pbPictures[i] = new GalleryEntryPB();
                PropertyUtils.copyProperties(pbPictures[i],entities[i]);
                parameters.put("picture",pbPictures[i].getId());
                if(updateForward!=null) {
                    pbPictures[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateForward.getPath(),parameters));
                }
                if(deleteForward!=null) {
                    pbPictures[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(deleteForward.getPath(),parameters));
                }
            }
            GalleryEntryCollectionPB pb = new GalleryEntryCollectionPB();
            pb.setPictures(pbPictures);
            request.setAttribute("picturesPB",pb);
        }
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Object obj = request.getAttribute("picturesPB");
        if(obj instanceof GalleryEntryCollectionPB) {
            return mapping.findForward(FORWARD_SUCCESS);
        }else {
            return mapping.findForward("success-external");
        }
    }

    public Gallery getGallery(Integer id) {
        Gallery template = (Gallery) getEnvironment().getEntityFactory().create("diary-gallery");
        template.setId(id);
        Gallery gallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").load(template);
        return gallery;
    }
}
