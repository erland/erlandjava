package erland.webapp.usermgmt;
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

import erland.webapp.common.BaseServlet;
import erland.util.Log;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public abstract class UserMgmtServlet extends BaseServlet {
    protected boolean isCommandAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String cmd = getCommandClassName(request);
        if(session!=null) {
            User user = (User) session.getAttribute("user");
            if(user!=null && user.isValid()) {
                String role = getEnvironment().getResources().getParameter("commands."+cmd+".role");
                if(role==null || user.hasRole(role)) {
                    Log.println(this,"isCommandAllowed: allowed user and role is valid");
                    return true;
                }else {
                    Log.println(this,"isCommandAllowed: not allowed role is not valid");
                    return false;
                }
            }
        }else {
            Log.println(this,"isCommandAllowed: no session");
        }
        if(getEnvironment().getResources().getParameter("commands."+cmd+".role")==null) {
            Log.println(this,"isCommandAllowed: command is valid"+cmd);
            return true;
        }
        Log.println(this,"isCommandAllowed: command is not valid "+cmd);
        return false;
    }

    protected String getNotAllowedPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null) {
            User user = (User)session.getAttribute("user");
            if(user!=null && user.isValid()) {
                return "accessdenied";
            }
        }
        return super.getNotAllowedPage(request);
    }
}
