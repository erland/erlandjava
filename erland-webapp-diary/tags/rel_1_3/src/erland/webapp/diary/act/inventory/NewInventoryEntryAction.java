package erland.webapp.diary.act.inventory;

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
import erland.webapp.diary.entity.inventory.InventoryEntry;
import erland.webapp.diary.entity.inventory.InventoryEntryEvent;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.fb.inventory.InventoryEntryFB;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.DescriptionIdPB;
import erland.webapp.diary.fb.inventory.InventoryEntryAndEventFB;
import erland.webapp.diary.fb.gallery.GalleryPB;
import erland.webapp.diary.fb.container.ContainerPB;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.entity.gallery.Gallery;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.gallery.fb.gallery.MenuItemPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class NewInventoryEntryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InventoryEntryAndEventFB fb = (InventoryEntryAndEventFB) form;
        fb.setEvents(null);
        fb.setDescription(null);
        fb.setGallery(null);
        fb.setId(null);
        fb.setImage(null);
        fb.setLargeImage(null);
        fb.setLink(null);
        fb.setName(null);
        fb.setType(new Integer(1)); // Set a default value
        fb.setEventDate(new Date());
        fb.setEventDescription(null);
        fb.setEventSize(null);

        DescriptionId[] types = DescriptionIdHelper.getDescriptionIdList("diary-inventoryentrytype");
        DescriptionIdPB[] typesPB = new DescriptionIdPB[types.length];
        for (int i = 0; i < types.length; i++) {
            typesPB[i] = new DescriptionIdPB();
            PropertyUtils.copyProperties(typesPB[i],types[i]);
        }
        request.getSession().setAttribute("inventoryEntryTypesPB",typesPB);

        types = DescriptionIdHelper.getDescriptionIdList("diary-inventoryentryeventtype");
        typesPB = new DescriptionIdPB[types.length];
        for (int i = 0; i < types.length; i++) {
            typesPB[i] = new DescriptionIdPB();
            PropertyUtils.copyProperties(typesPB[i],types[i]);
        }
        request.getSession().setAttribute("inventoryEntryEventTypesPB",typesPB);

        types = DescriptionIdHelper.getDescriptionIdList("diary-inventoryentrysex");
        typesPB = new DescriptionIdPB[types.length];
        for (int i = 0; i < types.length; i++) {
            typesPB[i] = new DescriptionIdPB();
            PropertyUtils.copyProperties(typesPB[i],types[i]);
        }
        request.getSession().setAttribute("inventoryEntrySexesPB",typesPB);

        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").search(filter);
        GalleryPB[] pb= new GalleryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new GalleryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.getSession().setAttribute("galleriesPB",pb);

        filter = new QueryFilter("allforuser");
        filter.setAttribute("username", request.getRemoteUser());
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-container").search(filter);
        ContainerPB[] pbContainers= new ContainerPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pbContainers[i] = new ContainerPB();
            PropertyUtils.copyProperties(pbContainers[i],entities[i]);
        }
        request.getSession().setAttribute("containersPB",pbContainers);
    }
}
