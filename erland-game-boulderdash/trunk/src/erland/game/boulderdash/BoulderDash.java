/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 2, 2003
 * Time: 10:10:44 AM
 * To change this template use Options | File Templates.
 */
package erland.game.boulderdash;

import erland.util.*;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandlerApplicationInterface;

import java.awt.*;

public class BoulderDash extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface{
    private ParameterValueStorageExInterface storage;
    private ImageHandlerInterface images;

    public static void main(String[] args) {
        Log.setLog("boulderdashlog.xml");
        BoulderDash game = new BoulderDash();
        GamePanelHandler.run(game,game);
        System.exit(-1);
    }

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
    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            storage = new ParameterStorageGroup(new FileStorage("boulderdash.xml"),null,"boulderdash","level");
        }
        return storage;
    }

    public ImageHandlerInterface getImageHandler() {
        if(images==null) {
            images = new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images/boulderdash");
        }
        return images;
    }
}
