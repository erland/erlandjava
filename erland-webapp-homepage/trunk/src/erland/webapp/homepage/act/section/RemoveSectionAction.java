package erland.webapp.homepage.act.section;

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
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.BaseAction;
import erland.webapp.homepage.entity.section.Section;
import erland.webapp.homepage.fb.section.SectionFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveSectionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SectionFB fb = (SectionFB) form;
        Section section = (Section) getEnvironment().getEntityFactory().create("homepage-section");
        section.setId(fb.getId());
        section = (Section) getEnvironment().getEntityStorageFactory().getStorage("homepage-section").load(section);
        if (section.getUsername().equals(request.getRemoteUser())) {
            QueryFilter filter = new QueryFilter("allforparenttree");
            filter.setAttribute("username", section.getUsername());
            filter.setAttribute("parent", section.getId());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("homepage-section").search(filter);
            for (int i = 0; i < entities.length; i++) {
                getEnvironment().getEntityStorageFactory().getStorage("homepage-section").delete(entities[i]);
            }
            getEnvironment().getEntityStorageFactory().getStorage("homepage-section").delete(section);
        }
    }
}
