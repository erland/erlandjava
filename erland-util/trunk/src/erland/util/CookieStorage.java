package erland.util;

import java.applet.Applet;

/**
 * An implementation of {@link StorageInterface} that uses a
 * cookie to store inforamtion
 * @author Erland Isaksson
 */
public class CookieStorage implements StorageInterface {
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
        //System.out.println("CookieStorage.load s = " + s);
        return s;
    }
}
