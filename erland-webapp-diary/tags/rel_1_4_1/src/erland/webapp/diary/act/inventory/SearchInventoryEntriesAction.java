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
import erland.webapp.common.html.StringReplaceInterface;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.inventory.InventoryEntry;
import erland.webapp.diary.entity.inventory.InventoryEntryEvent;
import erland.webapp.diary.entity.species.Species;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.inventory.*;
import erland.webapp.diary.fb.species.SpeciesPB;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.diary.logic.appendix.SourceAppendixStringReplace;
import erland.webapp.usermgmt.User;
import erland.util.StringUtil;
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
        Integer container = null;
        if(fb.getContainer()!=null && fb.getContainer().intValue()!=0) {
            container = fb.getContainer();
        }
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentry").search(filter);

        ActionForward forwardUpdate = null;
        ActionForward forwardDelete = null;
        ActionForward forwardNewEvent = null;
        ActionForward forwardUpdateEvent = null;
        ActionForward forwardDeleteEvent = null;
        ActionForward forwardContainer = null;
        ActionForward forwardGallery = mapping.findForward("view-gallery-link");
        ActionForward forwardView = mapping.findForward("view-entry-link");
        forwardUpdate = mapping.findForward("update-entry-link");
        forwardDelete = mapping.findForward("delete-entry-link");
        forwardNewEvent = mapping.findForward("new-event-link");
        forwardUpdateEvent = mapping.findForward("update-event-link");
        forwardDeleteEvent = mapping.findForward("delete-event-link");
        forwardContainer = mapping.findForward("view-container-link");
        Map parameters = new HashMap();
        parameters.put("user",username);
        List result = new ArrayList();
        StringReplaceInterface sourceReplace = new SourceAppendixStringReplace();
        for (int i = 0; i < entities.length; i++) {
            InventoryEntryEventPB[] events = getEvents(((InventoryEntry) entities[i]).getId(),fb.getDate(),container,forwardUpdateEvent,forwardDeleteEvent);
            if(events.length>0 || getAllEvents()) {
                InventoryEntryPB entry = new InventoryEntryPB();
                PropertyUtils.copyProperties(entry,entities[i]);
                if(StringUtil.asNull(entry.getLink())!=null) {
                    String link = sourceReplace.replace(entry.getLink());
                    if(!entry.getLink().equals(link)) {
                        entry.setLinkSource(link);
                    }
                }
                entry.setEvents(events);
                entry.setTypeDescription(DescriptionIdHelper.getDescription("diary-inventoryentrytype",entry.getType()));
                entry.setSexDescription(DescriptionIdHelper.getDescription("diary-inventoryentrysex",entry.getSex()));

                if(entry.getSpecies()!=null && entry.getSpecies().intValue()!=0) {
                    entry.setSpeciesInfo(getSpecies(request,mapping,username,entry.getSpecies()));
                }else {
                    entry.setSpeciesInfo(new SpeciesPB());
                }

                parameters.put("id",entry.getId());
                parameters.put("gallery",entry.getGallery());
                if(events.length>0) {
                    parameters.put("size",events[events.length-1].getSize());
                    parameters.put("container",events[events.length-1].getContainer());
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
                if(forwardContainer!=null) {
                    entry.setContainerLink(ServletParameterHelper.replaceDynamicParameters(forwardContainer.getPath(),parameters));
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

    public InventoryEntryEventPB[] getEvents(Integer id, Date date, Integer container, ActionForward forwardUpdateEvent, ActionForward forwardDeleteEvent) {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id", id);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentryevent").search(filter);
        List events = new ArrayList();
        Map parameters = new HashMap();
        parameters.put("id",id);
        Integer firstBeforeDate = null;
        for (int i = 0; i < entities.length; i++) {
            if (getAllEvents() || date==null || !((InventoryEntryEvent)entities[i]).getDate().after(date)) {
                if(firstBeforeDate==null) {
                    firstBeforeDate = new Integer(i);
                }
                if(getAllEvents() || ((InventoryEntryEvent)entities[i]).isActive()) {
                    if(container==null || container.equals(((InventoryEntryEvent)entities[firstBeforeDate.intValue()]).getContainer())) {
                        InventoryEntryEventPB event = new InventoryEntryEventPB();
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
                    }
                }else {
                    break;
                }
            }
        }
        return (InventoryEntryEventPB[]) events.toArray(new InventoryEntryEventPB[0]);
    }

    protected boolean getAllEvents() {
        return true;
    }
    private SpeciesPB getSpecies(HttpServletRequest request, ActionMapping mapping, String username,Integer id) {
        Map allSpecies = (Map) request.getAttribute(getClass()+"SPECIES");
        if(allSpecies==null) {
            allSpecies = new HashMap();
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username",username);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-species").search(filter);
            for (int i = 0; i < entities.length; i++) {
                Species entity = (Species) entities[i];
                SpeciesPB pb = new SpeciesPB();
                try {
                    PropertyUtils.copyProperties(pb,entity);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                } catch (NoSuchMethodException e) {
                }

                Map parameters = new HashMap();
                parameters.put("gallery",pb.getGallery());
                String user = request.getRemoteUser();
                if(user==null) {
                    user=(String) request.getSession().getAttribute("user");
                }
                parameters.put("user",user);
                parameters.put("species",entity.getId());
                parameters.put("gallery",entity.getGallery());

                ActionForward forward = mapping.findForward("view-species-gallery-link");
                if(forward!=null) {
                    pb.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                }
                forward = mapping.findForward("view-species-link");
                if(forward!=null) {
                    pb.setViewLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
                }

                if(StringUtil.asNull(pb.getLink())!=null) {
                    String link = new SourceAppendixStringReplace().replace(pb.getLink());
                    if(!pb.getLink().equals(link)) {
                        pb.setLinkSource(link);
                    }
                }

                allSpecies.put(entity.getId(),pb);
            }
        }
        return (SpeciesPB) allSpecies.get(id);
    }
}
