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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.EntityStorageInterface;
import erland.webapp.common.QueryFilter;
import erland.util.Log;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.HashSet;

public class User implements EntityInterface {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isValid;
    private Set roles;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    protected WebAppEnvironmentInterface getEnvironment() {
        return this.environment;
    }

    public String getUsername() {
        return username==null?"":username;
    }

    public void setUsername(String name) {
        this.username = name!=null?name.trim():null;
    }

    public String getPassword() {
        return password==null?"":password;
    }

    public void setPassword(String password) {
        this.password = password!=null?password.trim():null;
    }

    public boolean login(String application) {
        try {
            Log.println(this,"Connecting to user management database");
            EntityStorageInterface storage = getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user");
            User user = (User) storage.load(this);
            if(user!=null) {
                roles = user.getApplicationRoleList(application);
                Log.println(this,"Got "+roles.size()+" roles");
                if(roles.size()>0) {
                    Log.println(this,"Got password "+user.getPassword());
                    if(user.getPassword() != null) {
                        Log.println(this,"Comparing password with "+getPassword());
                        if(user.getPassword().equals(getPassword())) {
                            PropertyUtils.copyProperties(this,user);
                            setValid(true);
                            return isValid();
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        setValid(false);
        return isValid();
    }

    public boolean isValid()
    {
        return isValid;
    }
    protected void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private Set getApplicationRoleList(String application) {
        EntityStorageInterface storage = environment.getEntityStorageFactory().getStorage("usermgmt-userapplicationrole");
        QueryFilter filter = new QueryFilter("allforuserandapplication");
        filter.setAttribute("username",getUsername());
        filter.setAttribute("application",application);
        EntityInterface[] roles = storage.search(filter);
        Set result = new HashSet();
        for (int i = 0; i < roles.length; i++) {
            result.add(((UserApplicationRole)roles[i]).getRole());
        }
        return result;
    }

    public boolean hasRole(String role) {
        return roles!=null?roles.contains(role):false;
    }

    public UserPrincipal createPrincial() {
        return new UserPrincipal(username,(String[])roles.toArray(new String[0]));
    }
}
