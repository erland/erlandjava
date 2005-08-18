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
import erland.webapp.cdcollection.fb.collection.SelectCollectionFB;
import erland.webapp.cdcollection.fb.collection.SearchCollectionMediaFB;
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

public class SearchNewCollectionMediasAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchCollectionMediaFB fb = (SearchCollectionMediaFB) form;
        String username = request.getRemoteUser();
        Collection collection = null;
        if (fb.getCollection() != null) {
            Collection template = (Collection) getEnvironment().getEntityFactory().create("cdcollection-collection");
            template.setId(fb.getCollection());
            collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").load(template);
        }
        System.out.println("collection = "+collection);
        MediaPB[] pb = new MediaPB[0];
        if(isAllowed(collection,username)) {
            System.out.println("Querying");
            QueryFilter filter = null;
            if(StringUtil.asNull(fb.getArtist())!=null && StringUtil.asNull(fb.getTitle())!=null) {
                filter = new QueryFilter("allfortitlelikeandartistlike");
                filter.setAttribute("artist",fb.getArtist());
                filter.setAttribute("title",fb.getTitle());
            }else if(StringUtil.asNull(fb.getArtist())!=null) {
                filter = new QueryFilter("allforartistlike");
                filter.setAttribute("artist",fb.getArtist());
            }else if(StringUtil.asNull(fb.getTitle())!=null) {
                filter = new QueryFilter("allfortitlelike");
                filter.setAttribute("title",fb.getArtist());
            }else {
                filter = new QueryFilter("all");
            }
            System.out.println("filter = "+filter);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").search(filter);
            System.out.println("Got "+entities.length);
            ActionForward addForward = mapping.findForward("media-add-link");
            ActionForward coverForward = mapping.findForward("media-cover-link");
            Map parameters = new HashMap();
            parameters.put("collection",fb.getCollection());
            pb = new MediaPB[entities.length];
            for (int i = 0; i < entities.length; i++) {
                System.out.println("media = "+entities[i]);
                pb[i] = new MediaPB();
                PropertyUtils.copyProperties(pb[i], entities[i]);
                parameters.put("media",pb[i].getId());
                if(addForward!=null) {
                    pb[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(addForward.getPath(),parameters));
                }
                if(coverForward!=null) {
                    pb[i].setCoverLink(ServletParameterHelper.replaceDynamicParameters(coverForward.getPath(),parameters));
                }
            }
        }
        request.setAttribute("mediasPB", pb);
    }

    protected boolean isAllowed(Collection collection, String user) {
        return user.equals(collection.getUsername());
    }
}
