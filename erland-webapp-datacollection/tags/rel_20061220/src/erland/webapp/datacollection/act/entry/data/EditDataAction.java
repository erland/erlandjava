package erland.webapp.datacollection.act.entry.data;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.datacollection.fb.entry.data.DataFB;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.logic.entry.data.DataHelper;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class EditDataAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataFB fb = (DataFB) form;
        if(fb.getId()!=null) {
            DataHelper.storeHistory(getEnvironment(),fb.getId());
        }
        Entry entry = (Entry) getEnvironment().getEntityFactory().create("datacollection-entry");
        entry.setId(fb.getEntryId());
        entry = (Entry) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").load(entry);
        entry.setLastChanged(new Date());
        getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").store(entry);

        Data data = (Data) getEnvironment().getEntityFactory().create("datacollection-data");
        PropertyUtils.copyProperties(data, fb);
        if(StringUtil.asNull(fb.getUrl())!=null) {
            data.setContent(null);
        }
        getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").store(data);
    }
}
