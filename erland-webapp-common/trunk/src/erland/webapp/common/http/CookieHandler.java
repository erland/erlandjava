/*
 * Copyright (C) Erland Isaksson (erland_i@hotmail.com)
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
 *
 */

package erland.webapp.common.http;

import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.net.URLConnection;

/**
 * This class is responsible to manage cookies in HTTP request, this also includes to update cookies from the response
 *
 */
public class CookieHandler {
    private Map cookieContainer = new HashMap();

    /**
     * Creates an empty cookie handler
     */
    public CookieHandler() {}

    /**
     * Creates a cookie handler with an initial set of cookies
     * @param cookieContainer A map which contains all cookies that should be initialized
     */
    public CookieHandler(Map cookieContainer) {
        this.cookieContainer.putAll(cookieContainer);
    }

    /**
     * Set the specified cookie
     * @param name The name of the cookie
     * @param value The new value of the cookie
     */
    public void setCookie(String name, String value) {
        cookieContainer.put(name,value!=null?value:"");
    }

    /**
     * Get the value of the specified cookie
     * @param name The name of the cookie
     * @return The value of the cookie
     */
    public String getCookie(String name) {
        return (String) cookieContainer.get(name);
    }

    /**
     * Set the specified cookie
     * @param cookieString A cookie string in the format "name=value"
     */
    public void setCookie(String cookieString) {
        int pos = cookieString.indexOf("=");
        if(pos>0) {
            if(pos<cookieString.length()-1) {
                setCookie(cookieString.substring(0,pos),cookieString.substring(pos+1));
            }else {
                setCookie(cookieString.substring(0,pos),"");
            }
        }else {
            setCookie(cookieString,"");
        }
    }

    /**
     * Set the specified set of cookies
     * @param cookies A map with all cookies that should be set
     */
    public void setCookies(Map cookies) {
        if(cookies==null) return;
        cookieContainer.putAll(cookies);
    }

    /**
     * Set all cookies specified as Set-Cookie header fields in the specified connection
     * @param conn The url connection to read cookies from
     */
    public void setCookies(URLConnection conn) {
        Map fields = conn.getHeaderFields();
        Object cookieField = fields.get("Set-Cookie");
        if(cookieField instanceof Collection) {
            Collection cookieFields = (Collection) cookieField;
            for (Iterator iterator = cookieFields.iterator(); iterator.hasNext();) {
                String cookieString = iterator.next().toString();
                setCookie(cookieString);
            }
        }else if(cookieField!=null){
            setCookie(cookieField.toString());
        }
    }

    /**
     * Post all managed cookies to the specified connection
     * @param conn The url connection to post cookies to
     */
    public void postCookies(URLConnection conn) {
        String cookieList = getCookieString();
        if (cookieList.length() > 0) {
          conn.setRequestProperty("Cookie", cookieList);
        }
    }

    /**
     * Get a string with all managed cookies in the format suitable to use when setting the Cookie request field
     * @return
     */
    public String getCookieString() {
        StringBuffer cookieList = new StringBuffer();

        for (Iterator i = cookieContainer.entrySet().iterator(); i.hasNext();) {
          Map.Entry entry = (Map.Entry)(i.next());
          cookieList.append(entry.getKey().toString() + "=" + entry.getValue());

          if (i.hasNext()) {
            cookieList.append("; ");
          }
        }
        return cookieList.toString();
    }
}
