package erland.game;


import java.applet.Applet;

/**
 * Abstract class of a game panel handler for a full screen game
 * @author Erland Isaksson
 */
public class GamePanelHandlerForApplet extends GamePanelHandlerForApplication {
    /** Applet object */
    private Applet applet;

    /**
     * Creates a new instance using the specified applet object
     * @param applet The applet object
     */
    public GamePanelHandlerForApplet(Applet applet) {
        this.applet = applet;
    }

    public boolean getFullScreen() {
        return false;
    }

    public boolean askUserForFullscreen() {
        return false;
    }

    public ScreenHandlerInterface createScreenHandler() {
        return new AppletScreenHandler(applet,getApplication().getDisplayModes());

    }

    public void initExtraButtons(int firstX, int firstY, int buttonWidth, int buttonHeight, int buttonSpaceBetween) {
        // No extra buttons
    }
}
