package erland.webapp.gallery.account;
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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.usermgmt.UserApplicationRole;
import erland.webapp.gallery.gallery.GalleryInterface;

import javax.servlet.http.HttpServletRequest;

public class NewUserAccountCommand implements CommandInterface, ViewUserAccountInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount account;
    private GalleryInterface[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String welcomeText = request.getParameter("welcometext");
        if(username!=null&&username.length()>0 &&
            firstname!=null&&firstname.length()>0 &&
            lastname!=null&&lastname.length()>0 &&
            password1!=null&&password1.length()>0 &&
            password2!=null&&password2.length()>0) {

            if(password1.equals(password2)) {
                User template = (User) environment.getEntityFactory().create("usermgmt-user");
                template.setUsername(username);
                User user = (User) environment.getEntityStorageFactory().getStorage("usermgmt-user").load(template);
                if(user==null) {
                    template.setFirstName(firstname);
                    template.setLastName(lastname);
                    template.setPassword(password1);
                    environment.getEntityStorageFactory().getStorage("usermgmt-user").store(template);
                    UserApplicationRole templateRole = (UserApplicationRole) environment.getEntityFactory().create("usermgmt-userapplicationrole");
                    templateRole.setUsername(username);
                    templateRole.setApplication("gallery");
                    templateRole.setRole("user");
                    environment.getEntityStorageFactory().getStorage("usermgmt-userapplicationrole").store(templateRole);
                    UserAccount templateAccount = (UserAccount) environment.getEntityFactory().create("gallery-useraccount");
                    templateAccount.setUsername(username);
                    templateAccount.setWelcomeText(welcomeText);
                    environment.getEntityStorageFactory().getStorage("gallery-useraccount").store(templateAccount);
                    account = templateAccount;
                    return "success";
                }
            }
        }
        return "failure";
    }

    public UserAccount getAccount() {
        return account;
    }
    public User getUser() {
        User template = (User) environment.getEntityFactory().create("usermgmt-userinfo");
        template.setUsername(account.getUsername());
        User user = (User) environment.getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
        return user;
    }
    public GalleryInterface[] getGalleries() {
        if(galleries==null) {
            QueryFilter filter = new QueryFilter("addforuser");
            filter.setAttribute("username",account.getUsername());
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("gallery-gallery").search(filter);
            galleries = new GalleryInterface[entities.length];
            for (int i = 0; i < entities.length; i++) {
                galleries[i] = (GalleryInterface) entities[i];
            }
        }
        return galleries;
    }
}
