package erland.game.component;

/**
 * Static class that is used to select which implementation of
 * the Exxxx components that should be used
 * @author Erland Isaksson
 */
public class EComponentMode {
    /** Indicates if applet compatible components should be used */
    private static boolean bApplet = false;

    /**
     * Sets if applet compatible components should be used or not
     * @param applet true/false (Applet compatible/Not applet compatible)
     */
    static public void setAppletMode(boolean applet) {
        bApplet = applet;
    }
    /**
     * Check if the current component mode is applet compatible
     * @return true/false (Applet compatible/Not applet compatible)
     */
    static boolean isApplet() {
        return bApplet;
    }
}
