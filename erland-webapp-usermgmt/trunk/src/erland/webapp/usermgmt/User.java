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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements EntityInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(User.class);
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private boolean isValid;
    private Set roles;
    private WebAppEnvironmentInterface environment;
    private MessageDigest md;
    private String encryptedPassword;
    private static final String ENCRYPTION_PREFIX = "encrypted";

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
        return null;
    }

    public void setPassword(String password) {
        try {
            this.password =password!=null?encrypt(password.trim()):null;
            this.encryptedPassword = this.password;
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Unable to find encryption library",e);
            this.password = null;
            this.encryptedPassword = null;
        }
    }
    /**
     * Should only be used internally, should never be called from outside, use {@link #getPassword()} instead
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    /**
     * Should only be used internally, should never be called from outside, use {@link #setPassword(String)} instead
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean login(String application) {
        try {
            LOG.debug("Connecting to user management database");
            EntityStorageInterface storage = getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user");
            User user = (User) storage.load(this);
            if(user!=null) {
                if(user.getEncryptedPassword()!=null && !user.getEncryptedPassword().startsWith(ENCRYPTION_PREFIX)) {
                    user.setPassword(user.getEncryptedPassword());
                    storage.store(user);
                }
                roles = user.getApplicationRoleList(application);
                LOG.debug("Got "+roles.size()+" roles");
                if(roles.size()>0) {
                    LOG.debug("Got password "+user.getEncryptedPassword());
                    if(user.getEncryptedPassword() != null) {
                        LOG.debug("Comparing password with "+this.password);
                        if(user.getEncryptedPassword().equals(this.password)) {
                            PropertyUtils.copyProperties(this,user);
                            setValid(true);
                            return isValid();
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOG.error("Unable to copy properties",e);
        } catch (InvocationTargetException e) {
            LOG.error("Unable to copy properties",e);
        } catch (NoSuchMethodException e) {
            LOG.error("Unable to copy properties",e);
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
    public RolePrincipal[] createRolePrincials() {
        Vector result = new Vector();
        for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
            result.add(new RolePrincipal((String)iterator.next()));
        }
        return (RolePrincipal[]) result.toArray(new RolePrincipal[0]);
    }

    private String encrypt(String pwd) throws java.security.NoSuchAlgorithmException
    {
        if (null == md) { md = MessageDigest.getInstance("MD5"); }
        md.reset();
        byte pwdb[] = new byte[pwd.length()];
        for (int b = 0; b < pwd.length(); b++) {
            pwdb[b] = (byte) pwd.charAt(b);
        }
        return ENCRYPTION_PREFIX+toHex(md.digest(pwdb));
    }

    private String toHex(byte src[])
    {
        char buf[] = new char[src.length * 2];
        for (int b = 0; b < src.length; b++) {
            String byt = Integer.toHexString((int) src[b] & 0xFF);
            if (byt.length() < 2) {
                buf[b * 2 + 0] = '0';
                buf[b * 2 + 1] = byt.charAt(0);
            } else {
                buf[b * 2 + 0] = byt.charAt(0);
                buf[b * 2 + 1] = byt.charAt(1);
            }
        }
        return new String(buf);
    }
}
