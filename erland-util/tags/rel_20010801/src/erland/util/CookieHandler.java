package erland.util;

import netscape.javascript.*;
import java.applet.Applet;

public class CookieHandler
	implements ParameterValueStorageInterface
{
	Applet applet;
	
	public CookieHandler(Applet applet)
	{
		this.applet = applet;
	}
	
	public String getCookie() {
		/*
		** get all cookies for a document
		*/
		try {
			JSObject myBrowser = (JSObject) JSObject.getWindow(applet);
			JSObject myDocument =  (JSObject) myBrowser.getMember("document");
			String myCookie = (String)myDocument.getMember("cookie");
			if (myCookie.length() > 0) 
				return myCookie;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return "?";
	}

	public String getCookie(String name) {
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
				System.out.println("get: " + myCookie.substring(offset,end));
				return myCookie.substring(offset,end);
			}
			else 
				System.out.println("Did not find cookie: "+name);
		}
		return "";
	}

	public void setCookie(String name,String value)
	{

		/*  
		**  write a cookie
		**    computes the expiration date, good for 1 month
		*/
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.add(java.util.Calendar.YEAR, 1);
		String expires = "; expires=" + c.getTime().toString();
		
		String s1 = name +"="+value + expires; 
		System.out.println(s1);
		try {
			JSObject myBrowser = JSObject.getWindow(applet);
			JSObject myDocument =  (JSObject) myBrowser.getMember("document");
			
			myDocument.setMember("cookie", s1);
			System.out.println("set:" + s1);
		}catch(JSException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCookie(String name)
	{

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
			System.out.println("del: " + s1);
			myDocument.setMember("cookie", s1);	
		}catch(JSException e) {
			e.printStackTrace();
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
