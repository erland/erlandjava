package erland.webapp.diary.act.species;

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
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.inventory.InventoryEntryEventFB;
import erland.webapp.diary.fb.inventory.SelectInventoryFB;
import erland.webapp.diary.fb.inventory.InventoryEntryFB;
import erland.webapp.diary.fb.species.SpeciesPB;
import erland.webapp.diary.fb.species.SelectSpeciesFB;
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

public class SearchSpeciesAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        QueryFilter filter = new QueryFilter("allforuser");
        String username = request.getRemoteUser();
        if(username==null) {
            username=fb.getUser();
        }
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-species").search(filter);

        ActionForward forwardUpdate = null;
        ActionForward forwardDelete = null;
        ActionForward forwardGallery = mapping.findForward("view-gallery-link");
        ActionForward forwardView = mapping.findForward("view-entry-link");
        forwardUpdate = mapping.findForward("update-entry-link");
        forwardDelete = mapping.findForward("delete-entry-link");

        Map parameters = new HashMap();
        parameters.put("user",username);
        List result = new ArrayList();
        StringReplaceInterface sourceReplace = new SourceAppendixStringReplace();
        for (int i = 0; i < entities.length; i++) {
            SpeciesPB entry = new SpeciesPB();
            PropertyUtils.copyProperties(entry,entities[i]);
            if(StringUtil.asNull(entry.getLink())!=null) {
                String link = sourceReplace.replace(entry.getLink());
                if(!entry.getLink().equals(link)) {
                    entry.setLinkSource(link);
                }
            }
            entry.setTypeDescription(DescriptionIdHelper.getDescription("diary-speciestype",entry.getType()));
            parameters.put("id",entry.getId());
            parameters.put("gallery",entry.getGallery());
            if(forwardUpdate!=null) {
                entry.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forwardUpdate.getPath(),parameters));
            }
            if(forwardDelete!=null) {
                entry.setDeleteLink(ServletParameterHelper.replaceDynamicParameters(forwardDelete.getPath(),parameters));
            }
            if(forwardGallery!=null) {
                entry.setGalleryLink(ServletParameterHelper.replaceDynamicParameters(forwardGallery.getPath(),parameters));
            }
            if(forwardView!=null) {
                entry.setViewLink(ServletParameterHelper.replaceDynamicParameters(forwardView.getPath(),parameters));
            }
            result.add(entry);
        }
        request.getSession().setAttribute("speciesPB",result.toArray(new SpeciesPB[0]));
    }
}
