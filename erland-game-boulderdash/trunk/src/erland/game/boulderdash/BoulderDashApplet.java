/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 2, 2003
 * Time: 2:03:29 PM
 * To change this template use Options | File Templates.
 */
package erland.game.boulderdash;

import erland.game.*;
import erland.game.component.EComponentMode;
import erland.util.*;

import java.applet.Applet;
import java.awt.*;

public class BoulderDashApplet extends Applet implements GamePanelHandlerApplicationInterface, Runnable {
    protected Thread animator;

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        handler.addPanel("Game","Starting the game",new BoulderDashMain(10,10));
        handler.addPanel("Editor","Starting the editor",new BoulderDashEditor(10,10));
        handler.cheatWord("erland");
    }


    public void init() {
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
            storage = new ParameterStorageGroup(
                    new CookieStorage(this,"boulderdash"),
                    null,
                    "boulderdash",
                    "level");
        }
        ImageHandlerInterface imageHandler = new ImageHandlerForApplet(this,"images/boulderdash");
        GamePanelHandlerForApplet handler = new GamePanelHandlerForApplet(this);
        handler.setImageHandler(imageHandler);
        handler.setStorage(storage);
        EComponentMode.setAppletMode(true);
        while(animator!=null) {
            GamePanelHandler.run(this,handler);
        }
    }
}
