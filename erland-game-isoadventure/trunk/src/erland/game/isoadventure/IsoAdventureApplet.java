package erland.game.isoadventure;

import erland.game.GamePanelHandlerInterface;
import erland.game.GamePanelHandlerForApplet;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerApplicationInterface;
import erland.game.component.EComponentMode;
import erland.util.*;

import java.awt.*;
import java.applet.Applet;

/**
 * This class implements the applet version of the game
 */
public class IsoAdventureApplet extends Applet implements GamePanelHandlerApplicationInterface, Runnable {
    /** Main thread in the applet */
    protected Thread animator;

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        handler.addPanel("Game","Starting the game",new IsoAdventureMain(new IsoAdventureModelStandalone()));
        handler.cheatWord("erland");
    }

    public void start()
    {
        if(animator==null) {
            animator= new Thread(this);
            animator.start();
        }
    }

    public void stop()
    {
        if((animator != null) && (animator.isAlive())) {
            animator = null;
        }
    }

    public void run() {
        ParameterValueStorageExInterface storage = null;
        String mayScript = getParameter("MAYSCRIPT");
        if(mayScript!=null) {
            storage = new ParameterStorageString(
                    new CookieStorage(this,"isoadventure"),
                    new JarFileStorage("IsoAdventure.xml"),
                    "isoadventure");
        }
        ImageHandlerInterface imageHandler = new ImageHandlerForApplet(this,"images");
        GamePanelHandlerForApplet handler = new GamePanelHandlerForApplet(this);
        handler.setImageHandler(imageHandler);
        handler.setStorage(storage);
        EComponentMode.setAppletMode(true);
        while(animator!=null) {
            GamePanelHandler.run(this,handler);
        }
    }
}
