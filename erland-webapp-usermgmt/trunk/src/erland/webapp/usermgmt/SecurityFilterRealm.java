package erland.webapp.usermgmt;

import org.securityfilter.realm.SimpleSecurityRealmBase;
import org.securityfilter.realm.SecurityRealmInterface;

import java.security.Principal;

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.Log;

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

public class SecurityFilterRealm implements SecurityRealmInterface {
    private String application;

    public Principal authenticate(String username, String password) {
        User user = (User)WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
        user.setUsername(username);
        user.setPassword(password);
        Log.println(this,"Trying to login as "+user.getUsername()+","+user.getPassword());
        if(user.login(application)) {
            Log.println(this,"Successfully login as "+user.getUsername());
            return new UserPrincipal(user);
        }else {
            Log.println(this,"Fail to login as "+user.getUsername());
            return null;
        }
    }

    public boolean isUserInRole(Principal principal, String rolename) {
        if(principal instanceof UserPrincipal) {
            User user = ((UserPrincipal)principal).getUser();
            if(user!=null) {
                return user.hasRole(rolename);
            }
        }
        return false;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}