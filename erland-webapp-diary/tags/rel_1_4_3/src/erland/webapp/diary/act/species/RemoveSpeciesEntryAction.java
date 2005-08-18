package erland.webapp.diary.act.species;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
import erland.webapp.common.EntityStorageInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.fb.species.SpeciesFB;
import erland.webapp.diary.entity.species.Species;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveSpeciesEntryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpeciesFB fb = (SpeciesFB) form;
        Species template = (Species) getEnvironment().getEntityFactory().create("diary-species");
        template.setId(fb.getId());
        Species entity = (Species) getEnvironment().getEntityStorageFactory().getStorage("diary-species").load(template);
        if(entity!=null && entity.getUsername().equals(request.getRemoteUser())) {
            getEnvironment().getEntityStorageFactory().getStorage("diary-species").delete(template);
        }
    }
}
