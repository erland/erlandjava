package erland.webapp.common.act;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.common.fb.MenuExpandCollapseFB;
import erland.webapp.common.ServletParameterHelper;

import java.util.ArrayList;
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

public class MenuExpandCollapseAction extends MenuExpandAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuExpandCollapseFB fb = (MenuExpandCollapseFB) form;
        String menuName = getConfigurableParameter("menuName",fb.getMenuName(),mapping.getParameter(),request);
        String menuItemId = getConfigurableParameter("menuItemId",fb.getMenuItemId(),mapping.getParameter(),request);
        String menuObj = (String) request.getSession().getAttribute(menuName);
        if(menuObj!=null && menuObj.startsWith(menuItemId)) {
            String id = menuItemId;
            int pos = id.lastIndexOf("-");
            if(pos>0) {
                id = id.substring(0,pos);
            }else {
                id = "";
            }
            request.getSession().setAttribute(menuName,id);
        }else {
            request.getSession().setAttribute(menuName,menuItemId);
        }
    }
}