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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.diary.entity.container.Container;
import erland.webapp.diary.entity.inventory.InventoryEntry;
import erland.webapp.diary.fb.container.ContainerPB;
import erland.webapp.diary.fb.container.SelectContainerFB;
import erland.webapp.diary.fb.inventory.InventoryEntryFB;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.InventorySummaryPB;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class ViewContainerInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectContainerFB fb = (SelectContainerFB) form;
        String username = request.getRemoteUser();
        if (username == null) {
            username = fb.getUser();
        }
        Container template = (Container) getEnvironment().getEntityFactory().create("diary-container");
        template.setId(fb.getContainer());
        template.setUsername(username);
        Container entry = (Container) getEnvironment().getEntityStorageFactory().getStorage("diary-container").load(template);
        if(entry==null) {
            saveErrors(request, Arrays.asList(new String[]{"diary.container.container-dont-exists"}));
            return;
        }
        ContainerPB pb = new ContainerPB();
        PropertyUtils.copyProperties(pb, entry);

        ActionForward updateForward = mapping.findForward("update-container-link");
        ActionForward deleteForward = mapping.findForward("delete-container-link");
        ActionForward galleryForward = mapping.findForward("view-gallery-link");
        ActionForward inventoryForward = mapping.findForward("view-inventory-link");
        Map parameters = new HashMap();
        parameters.put("user", username);
        parameters.put("gallery", entry.getGallery());
        parameters.put("container", entry.getId());
        if (updateForward != null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateForward.getPath(), parameters));
        }
        if (deleteForward != null) {
            pb.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(deleteForward.getPath(), parameters));
        }
        if(galleryForward!=null && entry.getGallery()!=null && entry.getGallery().intValue()!=0) {
            pb.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(galleryForward.getPath(),parameters));
        }
        if (inventoryForward != null) {
            pb.setInventoryLink(ServletParameterHelper.replaceDynamicParameters(inventoryForward.getPath(), parameters));
        }
        request.getSession().setAttribute("containerPB",pb);

        QueryFilter filter = new QueryFilter("allcurrentforuserandcontainer");
        filter.setAttribute("username",username);
        filter.setAttribute("container",entry.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventorysummary").search(filter);
        InventorySummaryPB[] pbEntries = new InventorySummaryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pbEntries[i] = new InventorySummaryPB();
            PropertyUtils.copyProperties(pbEntries[i],entities[i]);
        }
        request.setAttribute("inventorySummaryEntriesPB",pbEntries);
    }
}
