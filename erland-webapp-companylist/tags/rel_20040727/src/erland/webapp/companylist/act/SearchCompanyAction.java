package erland.webapp.companylist.act;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.companylist.fb.CompanyPB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class SearchCompanyAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter filter = getFilter(form);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("companylist-company").search(filter);
        CompanyPB[] pb = new CompanyPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new CompanyPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.setAttribute("companiesPB",pb);
    }
    protected QueryFilter getFilter(ActionForm form) {
        return new QueryFilter("all");
    }
}