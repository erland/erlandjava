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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandOptionsInterface;
import erland.util.ParameterValueStorageExInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginStatusCommand implements CommandInterface, CommandOptionsInterface {
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface options;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public void setOptions(ParameterValueStorageExInterface options) {
        this.options = options;
    }
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        String userAttribute = options.getParameter("userattribute");
        if(userAttribute==null) {
            userAttribute = "user";
        }
        if(session!=null) {
            User user = (User)session.getAttribute(userAttribute);
            if(user!=null && user.isValid()) {
                return "user";
            }
        }
        if(req.getParameter(userAttribute)!=null) {
            return "guest";
        }
        return "none";
    }
}
