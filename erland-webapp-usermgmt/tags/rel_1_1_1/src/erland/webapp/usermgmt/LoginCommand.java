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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginCommand implements CommandInterface, CommandOptionsInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(LoginCommand.class);
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface options;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public void setOptions(ParameterValueStorageExInterface options) {
        this.options = options;
    }
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        User user = (User)environment.getEntityFactory().create("usermgmt-user");
        user.setUsername(req.getParameter("name"));
        user.setPassword(req.getParameter("password"));
        LOG.debug("Trying to login as "+user.getUsername()+","+user.getPassword());
        if(user.login(req.getParameter("application"))) {
            LOG.debug("Successfully login as "+user.getUsername());
            String userAttribute = options.getParameter("userattribute");
            if(userAttribute==null) {
                userAttribute = "user";
            }
            session.setAttribute(userAttribute,user);
            return "success";
        }else {
            LOG.debug("Fail to login as "+user.getUsername());
            return "failure";
        }
    }
}
