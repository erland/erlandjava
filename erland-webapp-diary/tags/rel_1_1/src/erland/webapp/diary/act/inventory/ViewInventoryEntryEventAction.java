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

import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.inventory.InventoryEntryEvent;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.DescriptionIdPB;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewInventoryEntryEventAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InventoryEntryEventFB fb = (InventoryEntryEventFB) form;
        InventoryEntryEvent template = (InventoryEntryEvent) getEnvironment().getEntityFactory().create("diary-inventoryentryevent");
        template.setId(fb.getId());
        template.setEventId(fb.getEventId());
        InventoryEntryEvent entry = (InventoryEntryEvent) getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentryevent").load(template);
        PropertyUtils.copyProperties(fb,entry);

        DescriptionId[] types = DescriptionIdHelper.getDescriptionIdList("diary-inventoryentryeventtype");
        DescriptionIdPB[] typesPB = new DescriptionIdPB[types.length];
        for (int i = 0; i < types.length; i++) {
            typesPB[i] = new DescriptionIdPB();
            PropertyUtils.copyProperties(typesPB[i],types[i]);
        }
        request.getSession().setAttribute("inventoryEntryEventTypesPB",typesPB);
    }
}
