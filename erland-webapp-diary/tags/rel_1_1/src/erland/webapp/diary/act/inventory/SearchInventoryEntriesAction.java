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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.inventory.InventoryEntry;
import erland.webapp.diary.entity.inventory.InventoryEntryEvent;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.SelectInventoryFB;
import erland.webapp.diary.fb.inventory.InventoryEntryFB;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.usermgmt.User;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.InvocationTargetException;

public class SearchInventoryEntriesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectInventoryFB fb = (SelectInventoryFB) form;
        QueryFilter filter = new QueryFilter("allforuser");
        String username = request.getRemoteUser();
        if(username==null) {
            username=fb.getUser();
        }
        if(fb.getDate()==null) {
            fb.setDate(new Date());
        }
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentry").search(filter);

        ActionForward forwardUpdate = null;
        ActionForward forwardDelete = null;
        ActionForward forwardNewEvent = null;
        ActionForward forwardUpdateEvent = null;
        ActionForward forwardDeleteEvent = null;
        ActionForward forwardGallery = mapping.findForward("view-gallery-link");
        ActionForward forwardView = mapping.findForward("view-entry-link");
        forwardUpdate = mapping.findForward("update-entry-link");
        forwardDelete = mapping.findForward("delete-entry-link");
        forwardNewEvent = mapping.findForward("new-event-link");
        forwardUpdateEvent = mapping.findForward("update-event-link");
        forwardDeleteEvent = mapping.findForward("delete-event-link");
        Map parameters = new HashMap();
        parameters.put("user",username);
        List result = new ArrayList();
        for (int i = 0; i < entities.length; i++) {
            InventoryEntryEventFB[] events = getEvents(((InventoryEntry) entities[i]).getId(),fb.getDate(),forwardUpdateEvent,forwardDeleteEvent);
            if(events.length>0 || getAllEvents()) {
                InventoryEntryFB entry = new InventoryEntryFB();
                PropertyUtils.copyProperties(entry,entities[i]);
                entry.setEvents(events);
                entry.setTypeDescription(DescriptionIdHelper.getDescription("diary-inventoryentrytype",entry.getType()));
                parameters.put("id",entry.getId());
                parameters.put("gallery",entry.getGallery());
                if(events.length>0) {
                    parameters.put("size",events[events.length-1].getSize());
                }
                if(forwardUpdate!=null) {
                    entry.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forwardUpdate.getPath(),parameters));
                }
                if(forwardDelete!=null) {
                    entry.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forwardDelete.getPath(),parameters));
                }
                if(forwardNewEvent!=null) {
                    entry.setNewEventLink(ServletParameterHelper.replaceDynamicParameters(forwardNewEvent.getPath(),parameters));
                }
                if(forwardGallery!=null) {
                    entry.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(forwardGallery.getPath(),parameters));
                }
                if(forwardView!=null) {
                    entry.setViewLink(ServletParameterHelper.replaceDynamicParameters(forwardView.getPath(),parameters));
                }
                result.add(entry);
            }
        }
        request.getSession().setAttribute("inventoryEntriesPB",result.toArray(new InventoryEntryFB[0]));
    }

    public InventoryEntryEventFB[] getEvents(Integer id, Date date, ActionForward forwardUpdateEvent, ActionForward forwardDeleteEvent) {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id", id);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentryevent").search(filter);
        List events = new ArrayList();
        Map parameters = new HashMap();
        parameters.put("id",id);
        for (int i = 0; i < entities.length; i++) {
            if (getAllEvents() || date==null || !((InventoryEntryEvent)entities[i]).getDate().after(date)) {
                if(getAllEvents() || ((InventoryEntryEvent)entities[i]).isActive()) {
                    InventoryEntryEventFB event = new InventoryEntryEventFB();
                    try {
                        PropertyUtils.copyProperties(event,entities[i]);
                        if(((InventoryEntryEvent)entities[i]).isSizeRelevant()) {
                            event.setSizeText(event.getSize()+ "cm");
                        }
                        event.setDescriptionText(DescriptionIdHelper.getDescription("diary-inventoryentryeventtype",event.getDescription()));
                        parameters.put("eventId",event.getEventId());
                        if(forwardUpdateEvent!=null) {
                            event.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forwardUpdateEvent.getPath(),parameters));
                        }
                        if(forwardDeleteEvent!=null) {
                            event.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forwardDeleteEvent.getPath(),parameters));
                        }
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e) {
                    } catch (NoSuchMethodException e) {
                    }
                    events.add(event);
                }else {
                    break;
                }
            }
        }
        return (InventoryEntryEventFB[]) events.toArray(new InventoryEntryEventFB[0]);
    }

    protected boolean getAllEvents() {
        return true;
    }
}
