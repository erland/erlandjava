package erland.webapp.diary.act.container;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.diary.entity.container.Container;
import erland.webapp.diary.fb.container.ContainerFB;
import erland.webapp.diary.fb.gallery.GalleryPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewContainerAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContainerFB fb = (ContainerFB) form;
        Container template = (Container) getEnvironment().getEntityFactory().create("diary-container");
        template.setId(fb.getId());
        template.setUsername(request.getRemoteUser());
        Container entry = (Container) getEnvironment().getEntityStorageFactory().getStorage("diary-container").load(template);
        PropertyUtils.copyProperties(fb, entry);

        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").search(filter);
        GalleryPB[] pb= new GalleryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new GalleryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.getSession().setAttribute("galleriesPB",pb);
    }
}
