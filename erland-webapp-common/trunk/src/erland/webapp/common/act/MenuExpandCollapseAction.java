package erland.webapp.common.act;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.common.fb.MenuExpandCollapseFB;

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

public class MenuExpandCollapseAction extends BaseAction {
    protected void preProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        MenuExpandCollapseFB fb = (MenuExpandCollapseFB) form;
        if(fb.getMenuName()==null) {
            saveErrors(request,Arrays.asList(new String[] {"errors.invalid-parameter","menuName"}));
        }
        if(fb.getMenuItemId()==null) {
            saveErrors(request,Arrays.asList(new String[] {"errors.invalid-parameter","menuId"}));
        }
    }

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuExpandCollapseFB fb = (MenuExpandCollapseFB) form;
        String menuObj = (String) request.getSession().getAttribute(fb.getMenuName());
        System.out.println(fb.getMenuName()+" Current: "+menuObj);
        if(menuObj!=null && menuObj.startsWith(fb.getMenuItemId())) {
            String id = fb.getMenuItemId();
            int pos = id.lastIndexOf("-");
            if(pos>0) {
                id = id.substring(0,pos);
            }else {
                id = "";
            }
            System.out.println(fb.getMenuName()+" New: "+id);
            request.getSession().setAttribute(fb.getMenuName(),id);
        }else {
            System.out.println(fb.getMenuName()+" New: "+fb.getMenuItemId());
            request.getSession().setAttribute(fb.getMenuName(),fb.getMenuItemId());
        }
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        MenuExpandCollapseFB fb = (MenuExpandCollapseFB) form;
        String id=fb.getMenuItemId();
        ActionForward forward = mapping.findForward(fb.getMenuName()+"-"+id);
        while(forward==null && id.length()>0) {
            int pos = id.lastIndexOf("-");
            if(pos>0) {
                id = id.substring(0,pos);
                forward = mapping.findForward(fb.getMenuName()+"-"+id);
            }else {
                id="";
            }
        }
        if(forward==null) {
            forward = mapping.findForward(fb.getMenuName());
        }
        if(forward==null) {
            forward = mapping.findForward(FORWARD_SUCCESS);
        }
        return forward;
    }
}