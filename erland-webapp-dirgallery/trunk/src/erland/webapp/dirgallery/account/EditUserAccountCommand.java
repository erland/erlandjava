package erland.webapp.dirgallery.account;
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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditUserAccountCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            if (user.hasRole("manager")) {
                username = request.getParameter("username");
                if (username == null || username.length() == 0) {
                    username = user.getUsername();
                }
            }
            String welcomeText = request.getParameter("welcometext");
            String copyright = request.getParameter("copyright");
            String description = request.getParameter("description");
            String logo = request.getParameter("logo");
            Boolean official = ServletParameterHelper.asBoolean(request.getParameter("official"));
            Integer defaultGallery = ServletParameterHelper.asInteger(request.getParameter("defaultgallery"));
            UserAccount account = (UserAccount) environment.getEntityFactory().create("dirgalleryuseraccount");
            account.setUsername(username);
            account.setWelcomeText(welcomeText);
            account.setDescription(description);
            account.setLogo(logo);
            account.setOfficial(official);
            account.setDefaultGallery(defaultGallery);
            account.setCopyrightText(copyright);
            environment.getEntityStorageFactory().getStorage("dirgalleryuseraccount").store(account);
        }
        return null;
    }
}
