package erland.webapp.gallery.gallery.picturestorage;
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
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditPictureStorageCommand implements CommandInterface, ViewPictureStorageInterface {
    private WebAppEnvironmentInterface environment;
    private PictureStorage storage;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String path = request.getParameter("path");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        storage = (PictureStorage) environment.getEntityFactory().create("gallery-picturestorage");
        if(idString!=null && idString.length()>0) {
            storage.setId(Integer.valueOf(idString));
        }
        storage.setName(name);
        storage.setPath(path);
        storage.setUsername(username);
        environment.getEntityStorageFactory().getStorage("gallery-picturestorage").store(storage);
        return null;
    }

    public PictureStorage getStorage() {
        return storage;
    }
}