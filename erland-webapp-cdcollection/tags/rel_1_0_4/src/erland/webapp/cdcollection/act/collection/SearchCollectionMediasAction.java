package erland.webapp.cdcollection.act.collection;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.cdcollection.entity.collection.CollectionMediaList;
import erland.webapp.cdcollection.fb.collection.SelectCollectionFB;
import erland.webapp.cdcollection.fb.collection.CollectionPB;
import erland.webapp.cdcollection.fb.media.MediaFB;
import erland.webapp.cdcollection.fb.media.MediaPB;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class SearchCollectionMediasAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectCollectionFB fb = (SelectCollectionFB) form;
        Collection collection = null;
        if (fb.getCollection() == null) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.collection.view.collection-dont-exist"}));
            return;
        }
        Collection template = (Collection) getEnvironment().getEntityFactory().create("cdcollection-collection");
        template.setId(fb.getCollection());
        collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").load(template);
        if(collection==null) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.collection.view.collection-dont-exist"}));
            return;
        }
        if(!isAllowed(collection,request.getRemoteUser())) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.collection.view.access-denied"}));
            return;
        }
        CollectionPB pb = new CollectionPB();
        PropertyUtils.copyProperties(pb,collection);
        Map parameters = new HashMap();
        if(StringUtil.asNull(request.getServerName())!=null) {
            parameters.put("hostname",request.getServerName());
            if(request.getServerPort()!=80) {
                parameters.put("port",new Integer(request.getServerPort()));
            }
        }
        parameters.put("contextpath",request.getContextPath());
        parameters.put("collection",collection.getId());
        ActionForward forward = mapping.findForward("collection-update-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("collection-remove-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("collection-newmedia-link");
        if(forward!=null) {
            pb.setNewMediaLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        ActionForward viewLink = mapping.findForward("media-view-link");
        ActionForward coverLink = mapping.findForward("media-cover-link");
        ActionForward removeLink = mapping.findForward("media-remove-link");
        QueryFilter filter = new QueryFilter("allforcollection");
        filter.setAttribute("collection", collection.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collectionmedialist").search(filter);
        MediaPB[] pbMedias = new MediaPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            CollectionMediaList entity = (CollectionMediaList) entities[i];
            pbMedias[i] = new MediaPB();
            pbMedias[i].setId(entity.getMediaId());
            PropertyUtils.copyProperties(pbMedias[i], entity);
            parameters.put("media",entity.getMediaId());
            if(coverLink!=null && StringUtil.asNull(entity.getCoverUrl())!=null) {
                pbMedias[i].setCoverLink(ServletParameterHelper.replaceDynamicParameters(coverLink.getPath(),parameters));
            }
            if(viewLink!=null) {
                pbMedias[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(viewLink.getPath(),parameters));
            }
            if(removeLink!=null) {
                pbMedias[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(removeLink.getPath(),parameters));
            }
        }
        pb.setMedias(pbMedias);
        request.setAttribute("collectionPB", pb);
    }

    protected boolean isAllowed(Collection collection, String user) {
        return user!=null && user.equals(collection.getUsername());
    }
}
