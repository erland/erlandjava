package erland.game.crackout;

import erland.game.*;
import erland.game.component.EComponentMode;
import erland.util.*;

import java.applet.Applet;
import java.awt.*;

public class CrackOutApplet extends Applet implements GamePanelHandlerApplicationInterface, Runnable {
    protected Thread animator;

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        handler.addPanel("Game","Starting the game",new CrackOutMain(10,10,15));
        handler.addPanel("Editor","Starting the editor",new CrackOutEditor(10,10,15));
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
                    new CookieStorage(this,"crackout"),
                    null,
                    "crackout",
                    "level");
        }
        ImageHandlerInterface imageHandler = new ImageHandlerForApplet(this,"images/crackout");
        GamePanelHandlerForApplet handler = new GamePanelHandlerForApplet(this);
        handler.setImageHandler(imageHandler);
        handler.setStorage(storage);
        EComponentMode.setAppletMode(true);
        while(animator!=null) {
            GamePanelHandler.run(this,handler);
        }
    }
}
