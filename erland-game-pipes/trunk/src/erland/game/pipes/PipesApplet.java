package erland.game.pipes;

import erland.game.*;
import erland.game.component.EComponentMode;
import erland.util.*;

import java.awt.*;
import java.applet.Applet;

public class PipesApplet extends Applet implements GamePanelHandlerApplicationInterface, Runnable {
    protected Thread animator;

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        handler.addPanel("Game 1","Starting the game",new PipesMain(10,10,LevelFactory.GameType.UntilWaterStopped));
        handler.addPanel("Game 2","Starting the game",new PipesMain(10,10,LevelFactory.GameType.UntilPoolsFilled));
        handler.addPanel("Options","Starting the options",new PipesOptions(10,10));
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
            storage = new ParameterStorageString(
                    new CookieStorage(this,"boulderdash"),
                    null,
                    "pipes");
        }
        ImageHandlerInterface imageHandler = new ImageHandlerForApplet(this,"images/pipes");
        GamePanelHandlerForApplet handler = new GamePanelHandlerForApplet(this);
        handler.setImageHandler(imageHandler);
        handler.setStorage(storage);
        EComponentMode.setAppletMode(true);
        while(animator!=null) {
            GamePanelHandler.run(this,handler);
        }
    }
}
