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

import netscape.javascript.*;
import java.applet.Applet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Makes it possible to read, write and delete cookies 
 * from an applet
 * @author Erland Isaksson
 */
public class CookieHandler
	implements ParameterValueStorageInterface
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(CookieHandler.class);
	/**
	 * The applet object which will be used to access the cookies
	 */
	protected Applet applet;
	
	/**
	 * @param applet The applet object which should be used to access the cookies
	 */
	public CookieHandler(Applet applet)
	{
		this.applet = applet;
	}
	
	/**
	 * Reads all available cookies
	 * @return The complete cookie string
	 */
	protected String getCookie() {
		/*
		** get all cookies for a document
		*/
		try {
			JSObject myBrowser = JSObject.getWindow(applet);
			JSObject myDocument =  (JSObject) myBrowser.getMember("document");
			String myCookie = (String)myDocument.getMember("cookie");
			if (myCookie!=null && myCookie.length() > 0)
				return myCookie;
		}
		catch (Exception e){
			LOG.error("getCookie failed",e);
		}
		return "?";
	}

	/**
	 * Read a cookie
	 * @param name The name of the cookie
	 * @return The value of the cookie, if the cookie does
	 *         not exist it will return an empty string
	 */
	protected String getCookie(String name) {
		/*
		** get a specific cookie by its name, parse the cookie.
		**    not used in this Applet but can be useful
		*/
		String myCookie = getCookie();
		String search = name + "=";
		if (myCookie.length() > 0) {
			int offset = myCookie.indexOf(search);
			if (offset != -1) {
				offset += search.length();
				int end = myCookie.indexOf(";", offset);
				if (end == -1) {
					end = myCookie.length();
				}
				LOG.debug("get: " + myCookie.substring(offset,end));
				if(!myCookie.substring(offset,end).equals(" ")) {
					return myCookie.substring(offset,end);
				}else {
					return "";
				}
			}
			else 
				LOG.debug("Did not find cookie: "+name);
		}
		return "";
	}

    protected String cleanValue(String value) {
        LOG.debug("cleanValue value = " + value);
        int i = value.indexOf('\n');
        if(i==-1) {
            i = value.indexOf('\r');
        }
        if(i!=-1) {
            LOG.debug("cleanValue removing newline");
            StringBuffer sb = new StringBuffer(value);
            i = sb.indexOf("\n");
            while(i!=-1) {
                sb.deleteCharAt(i);
                i = sb.indexOf("\n");
            }
            i = sb.indexOf("\r");
            while(i!=-1) {
                sb.deleteCharAt(i);
                i = sb.indexOf("\r");
            }
            LOG.debug("cleanValue value ut = " + sb.toString());
            return sb.toString();
        }else {
            LOG.debug("cleanValue not modified");
            return value;
        }
    }
	/**
	 * Write a cookie
	 * @param name The name of the cookie
	 * @param value The value to write
	 */
	protected void setCookie(String name,String value)
	{
        value = cleanValue(value);
		/*  
		**  write a cookie
		**    computes the expiration date, good for 1 month
		*/
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.add(java.util.Calendar.YEAR, 1);
		String expires = "; expires=" + c.getTime().toString();
		
		String s1 = name +"="+value + expires; 
		LOG.debug(s1);
		try {
			JSObject myBrowser = JSObject.getWindow(applet);
			JSObject myDocument =  (JSObject) myBrowser.getMember("document");
			
			myDocument.setMember("cookie", s1);
			LOG.debug("set:" + s1);
		}catch(JSException e) {
			LOG.error("setCookie "+name+"="+value+" failed",e);
		}
	}
	
	/**
	 * Delete a cookie
	 * @param name The name of the cookie
	 */	 
	protected void deleteCookie(String name)
	{
		setCookie(name," ");
		/*
		**  delete a cookie, set the expiration in the past
		*/
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.add(java.util.Calendar.MONTH, -1);
		String expires = "; expires=" + c.getTime().toString();
		
		String s1 = name + expires; 
		try {
			JSObject myBrowser = JSObject.getWindow(applet);
			JSObject myDocument =  (JSObject) myBrowser.getMember("document");
			LOG.debug("del: " + s1);
			myDocument.setMember("cookie", s1);	
		}catch(JSException e) {
			LOG.error("deleteCookie "+name+" failed",e);
		}
	}

	public String getParameter(String name)
	{
		return getCookie(name);
	}
	public void setParameter(String name, String value)
	{
		setCookie(name,value);
	}
	public void delParameter(String name)
	{
		deleteCookie(name);
	}

}
