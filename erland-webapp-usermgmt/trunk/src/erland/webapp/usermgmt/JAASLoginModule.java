package erland.webapp.usermgmt;

import erland.webapp.common.act.WebAppEnvironmentPlugin;

import javax.security.auth.spi.LoginModule;
import javax.security.auth.login.LoginException;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import java.util.Map;
import java.util.Vector;
import java.util.Set;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class JAASLoginModule implements LoginModule {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(JAASLoginModule.class);
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Vector pendingPrincipals = null;
    private Vector principals = null;
    private boolean comitted = false;
    private String application;

    public boolean abort() throws LoginException {
        if (pendingPrincipals == null) {
            return false;
        } else if (pendingPrincipals != null && !comitted) {
            pendingPrincipals = null;
        } else {
            logout();
        }
        LOG.debug("Aborted login");
        return true;
    }

    public boolean commit() throws LoginException {
        if (pendingPrincipals == null) {
            return false;
        }

        principals = new Vector();
        Set s = subject.getPrincipals();

        // Add pendingPrincipals for this login module
        s.addAll(pendingPrincipals);
        principals.addAll(pendingPrincipals);

        comitted = true;
        LOG.debug("Comitted login");
        return true;
    }

    public boolean login() throws LoginException {
        // username and password
        String	username;
        char	password[] = null;

        if (callbackHandler == null){
            LOG.error("No callback handler for JAAS found");
            throw new LoginException("No CallbackHandler available");
        }

        Callback[] callbacks = new Callback[] {
            new NameCallback("Username: "),
            new PasswordCallback("Password: ", false)
        };

        try {
            callbackHandler.handle(callbacks);

            username = ((NameCallback) callbacks[0]).getName();
            password = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback)callbacks[1]).clearPassword();
        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException("Error: " + uce.getCallback().toString() +
                    " not available to garner authentication information from the user");
        }

        // Atempt to login
        pendingPrincipals = null;
        pendingPrincipals = validateUser(username, String.copyValueOf(password));
        return true;
    }

    public boolean logout() throws LoginException {
        pendingPrincipals = null;
        comitted = false;

        // Remove all principals for this login module
        Set s = subject.getPrincipals();
        int sz = principals.size();
        for (int p = 0; p < sz; p++) {
            s.remove(principals.get(p));
        }
        principals = null;

        LOG.debug("Logout");
        return true;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.application = (String) options.get("application");
    }

    private Vector validateUser(String username, String password) {
        User user = (User)WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
        user.setUsername(username);
        user.setPassword(password);
        LOG.debug("Trying to login as "+user.getUsername()+","+user.getPassword());
        if(user.login(application)) {
            LOG.debug("Successfully login as "+user.getUsername());
            Vector principals = new Vector();
            UserPrincipal principal = user.createPrincial();
            RolePrincipal[] rolePrincipals = user.createRolePrincials();
            principals.add(principal);
            for (int i = 0; i < rolePrincipals.length; i++) {
                principals.add(rolePrincipals[i]);
            }
            return principals;
        }else {
            LOG.debug("Fail to login as "+user.getUsername());
            return null;
        }
    }
}