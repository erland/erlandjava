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
import erland.webapp.diary.fb.inventory.*;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.entity.container.Container;
import erland.webapp.diary.logic.inventory.DescriptionIdHelper;
import erland.webapp.diary.logic.appendix.SourceAppendixStringReplace;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ViewInventoryEntryInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectInventoryEntryFB fb = (SelectInventoryEntryFB) form;
        String username = request.getRemoteUser();
        if(username==null) {
            username=fb.getUser();
        }

        InventoryEntry template = (InventoryEntry) getEnvironment().getEntityFactory().create("diary-inventoryentry");
        template.setId(fb.getId());
        InventoryEntry entry = (InventoryEntry) getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentry").load(template);
        InventoryEntryFB pb = new InventoryEntryFB();
        PropertyUtils.copyProperties(pb,entry);

        if(StringUtil.asNull(pb.getLink())!=null) {
            String link = new SourceAppendixStringReplace().replace(pb.getLink());
            if(!pb.getLink().equals(link)) {
                pb.setLinkSource(link);
            }
        }
        pb.setTypeDescription(DescriptionIdHelper.getDescription("diary-inventoryentrytype",entry.getType()));
        pb.setSexDescription(DescriptionIdHelper.getDescription("diary-inventoryentrysex",entry.getSex()));
        Map parameters = new HashMap();
        parameters.put("user",username);
        parameters.put("gallery",entry.getGallery());
        parameters.put("id",entry.getId());
        ActionForward forward = mapping.findForward("view-gallery-link");
        if(forward!=null && entry.getGallery()!=null && entry.getGallery().intValue()!=0) {
            pb.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("update-entry-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("remove-entry-link");
        if(forward!=null) {
            pb.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id", entry.getId());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-inventoryentryevent").search(filter);
        InventoryEntryEventFB[] pbEvents = new InventoryEntryEventFB[entities.length];
        for (int i = entities.length-1; i >= 0; i--) {
            pbEvents[i] = new InventoryEntryEventFB();
            try {
                PropertyUtils.copyProperties(pbEvents[i],entities[entities.length-1-i]);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            if(((InventoryEntryEvent)entities[entities.length-1-i]).isSizeRelevant()) {
                pbEvents[i].setSizeText(pbEvents[i].getSize()+ " cm");
            }
            pbEvents[i].setDescriptionText(DescriptionIdHelper.getDescription("diary-inventoryentryeventtype",pbEvents[i].getDescription()));
            parameters.put("eventId",pbEvents[i].getEventId());
            forward = mapping.findForward("update-event-link");
            if(forward!=null) {
                pbEvents[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("remove-event-link");
            if(forward!=null) {
                pbEvents[i].setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
        }
        pb.setEvents(pbEvents);
        if(pbEvents.length>0) {
            parameters.put("size",pbEvents[pbEvents.length-1].getSize());
            parameters.put("container",pbEvents[pbEvents.length-1].getContainer());
            if(pbEvents[pbEvents.length-1].getContainer()!=null && pbEvents[pbEvents.length-1].getContainer().intValue()!=0) {
                Container templateContainer = (Container) getEnvironment().getEntityFactory().create("diary-container");
                templateContainer.setId(pbEvents[pbEvents.length-1].getContainer());
                templateContainer.setUsername(username);
                Container container = (Container) getEnvironment().getEntityStorageFactory().getStorage("diary-container").load(templateContainer);
                if(container!=null) {
                    pb.setCurrentContainerDescription(container.getName());
                }
            }
            pb.setCurrentSizeText(pbEvents[pbEvents.length-1].getSize()+ " cm");
        }
        forward = mapping.findForward("new-event-link");
        if(forward!=null) {
            pb.setNewEventLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("view-container-link");
        if(forward!=null) {
            pb.setContainerLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        request.setAttribute("inventoryEntryPB",pb);
    }
}
