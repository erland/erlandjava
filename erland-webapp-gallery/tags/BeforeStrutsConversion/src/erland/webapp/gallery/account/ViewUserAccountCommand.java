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
import erland.webapp.gallery.gallery.GalleryInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewUserAccountCommand implements CommandInterface, ViewUserAccountInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount account;
    private GalleryInterface[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        String username = request.getParameter("user");
        if(user!=null) {
            username = user.getUsername();
            if(user.hasRole("manager")) {
                username = request.getParameter("user");
                if(username==null || username.length()==0) {
                    username = user.getUsername();
                }
            }
        }
        UserAccount template = (UserAccount)environment.getEntityFactory().create("gallery-useraccount");
        template.setUsername(username);
        account = (UserAccount) environment.getEntityStorageFactory().getStorage("gallery-useraccount").load(template);
        String differentPages = request.getParameter("withdifferentpages");
        if(differentPages!=null && differentPages.equals("1")) {
            if(account!=null && account.getDefaultGallery().intValue()!=0) {
                request.setAttribute("defaultgallery",account.getDefaultGallery());
                return "withdefaultgallery";
            }else {
                return "withoutdefaultgallery";
            }
        }
        return null;
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
            QueryFilter filter = new QueryFilter("allforuser");
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
