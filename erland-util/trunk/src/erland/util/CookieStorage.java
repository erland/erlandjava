package erland.util;
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

import org.apache.commons.logging.*;

import java.applet.Applet;

/**
 * An implementation of {@link StorageInterface} that uses a
 * cookie to store inforamtion
 * @author Erland Isaksson
 */
public class CookieStorage implements StorageInterface {
    /** Logging instance */
    private static org.apache.commons.logging.Log LOG = LogFactory.getLog(CookieStorage.class);
    /** The cookie handler to use */
    CookieHandler handler;
    /** The name of the cookie to store data in */
    String cookieName;

    /**
     * Creates a new instance that stores information in the specified cookie
     * using the specified applet object
     * @param applet The applet object to use when accessing the cookie
     * @param cookieName The name of the cookie to store information in
     */
    public CookieStorage(Applet applet, String cookieName) {
        handler = new CookieHandler(applet);
        this.cookieName = cookieName;

    }
    public void save(String str) {
        handler.setParameter(cookieName,str);
    }

    public String load() {
        String s = handler.getParameter(cookieName);
        LOG.debug("CookieStorage.load s = " + s);
        return s;
    }
}
