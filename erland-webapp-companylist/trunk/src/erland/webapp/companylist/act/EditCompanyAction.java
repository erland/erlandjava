package erland.webapp.companylist.act;

import erland.webapp.common.act.BaseAction;
import erland.webapp.companylist.fb.CompanyFB;
import erland.webapp.companylist.entity.Company;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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

public class EditCompanyAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompanyFB fb = (CompanyFB) form;
        String username = request.getRemoteUser();
        Company template = (Company) getEnvironment().getEntityFactory().create("companylist-company");
        template.setId(fb.getId());
        Company company = (Company) getEnvironment().getEntityStorageFactory().getStorage("companylist-company").load(template);
        if(company!=null) {
            if(!request.isUserInRole("manager") && !request.getRemoteUser().equals(company.getUsername())) {
                saveErrors(request, Arrays.asList(new String[]{"companylist.error.unauthorized"}));
                return;
            }else {
                username = company.getUsername();
            }
        }else {
            company = template;
        }
        PropertyUtils.copyProperties(company,fb);
        company.setUsername(username);
        getEnvironment().getEntityStorageFactory().getStorage("companylist-company").store(company);
    }
}