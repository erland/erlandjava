package erland.webapp.diary.act.appendix;

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
import erland.webapp.common.html.HTMLEncoder;
import erland.webapp.diary.entity.appendix.AppendixEntry;
import erland.webapp.diary.fb.appendix.AppendixFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditAppendixEntryAction extends BaseAction {
    protected String getEntity() {
        return "diary-appendixentry";
    }

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppendixFB fb = (AppendixFB) form;
        AppendixEntry entry = (AppendixEntry) getEnvironment().getEntityFactory().create(getEntity());
        PropertyUtils.copyProperties(entry,fb);
        getEnvironment().getEntityStorageFactory().getStorage(getEntity()).store(entry);
        HTMLEncoder.reset();
    }
}
