package erland.webapp.datacollection.act.entry;

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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.datacollection.fb.entry.EntryFB;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.logic.entry.EntryHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveEntryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntryFB fb = (EntryFB) form;
        EntryHelper.storeHistory(getEnvironment(),fb.getId());
        Entry template = (Entry) getEnvironment().getEntityFactory().create("datacollection-entry");
        template.setId(fb.getId());
        Entry entry = (Entry) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").load(template);
        request.setAttribute("collection",entry.getCollection());
        getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").delete(template);
        QueryFilter filter = new QueryFilter("allforentry");
        filter.setAttribute("entry", template.getId());
        getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").delete(filter);
    }
}
