package erland.webapp.common.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import erland.webapp.common.fb.MenuExpandCollapseFB;

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

public class MenuExpandCollapseAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        MenuExpandCollapseFB fb = (MenuExpandCollapseFB) actionForm;
        if(fb.getMenuName()!=null) {
            String menuObj = (String) httpServletRequest.getSession().getAttribute(fb.getMenuName());
            if(menuObj!=null && menuObj.equals(fb.getMenuItemId())) {
                String id = fb.getMenuItemId();
                int pos = fb.getMenuItemId().lastIndexOf("-");
                if(pos>0) {
                    id = id.substring(0,pos);
                }else {
                    id = "";
                }
                httpServletRequest.getSession().setAttribute(fb.getMenuName(),id);
            }else if(menuObj!=null && menuObj.startsWith(fb.getMenuItemId())) {
                httpServletRequest.getSession().setAttribute(fb.getMenuName(),fb.getMenuItemId());
            }else {
                httpServletRequest.getSession().setAttribute(fb.getMenuName(),fb.getMenuItemId());
            }
            String id=fb.getMenuItemId();
            ActionForward forward = actionMapping.findForward(fb.getMenuName()+"-"+id);
            while(forward==null && id.length()>0) {
                int pos = id.lastIndexOf("-");
                if(pos>0) {
                    id = id.substring(0,pos);
                    forward = actionMapping.findForward(fb.getMenuName()+"-"+id);
                }else {
                    id="";
                }
            }
            if(forward==null) {
                forward = actionMapping.findForward(fb.getMenuName());
            }
            if(forward==null) {
                forward = actionMapping.findForward("success");
            }
            return forward;
        }else {
            return actionMapping.findForward("failure");
        }
    }
}