package erland.webapp.diary.gallery;
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

import javax.servlet.http.HttpServletRequest;

public class SearchGalleriesCommand implements CommandInterface, ViewGalleriesInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary-gallery").search(filter);
        galleries = new Gallery[entities.length];
        for (int i = 0; i < entities.length; i++) {
            galleries[i]= (Gallery) entities[i];
        }
        return null;
    }

    public Gallery[] getGalleries() {
        return galleries;
    }
    protected String getQueryFilter() {
        return "allforuser";
    }
}
