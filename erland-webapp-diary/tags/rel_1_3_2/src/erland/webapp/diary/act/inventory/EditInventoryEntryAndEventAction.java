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
import erland.webapp.diary.fb.inventory.InventoryEntryFB;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.InventoryEntryAndEventFB;
import erland.webapp.usermgmt.User;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditInventoryEntryAndEventAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InventoryEntryAndEventFB fb = (InventoryEntryAndEventFB) form;
        InventoryEntry entry = (InventoryEntry) getEnvironment().getEntityFactory().create("diary-inventoryentry");
        PropertyUtils.copyProperties(entry,fb);
        entry.setUsername(request.getRemoteUser());
        getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentry").store(entry);

        InventoryEntryEvent event = (InventoryEntryEvent) getEnvironment().getEntityFactory().create("diary-inventoryentryevent");
        event.setDate(fb.getEventDate());
        event.setDescription(fb.getEventDescription());
        event.setSize(fb.getEventSize());
        event.setId(entry.getId());
        event.setContainer(fb.getContainer());
        getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentryevent").store(event);
    }
}