/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 2, 2003
 * Time: 3:25:13 PM
 * To change this template use Options | File Templates.
 */
package erland.game.pipes;

import erland.game.GamePanelHandlerApplicationInterface;
import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.util.*;

import java.awt.*;

public class Pipes extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface {
    private ParameterValueStorageExInterface storage;
    private ImageHandlerInterface images;

    public static void main(String[] args) {
        Log.setLog("pipeslog.xml");
        Pipes game = new Pipes();
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
        handler.addPanel("Game 1","Starting the game",new PipesMain(10,10,LevelFactory.GameType.UntilWaterStopped));
        handler.addPanel("Game 2","Starting the game",new PipesMain(10,10,LevelFactory.GameType.UntilPoolsFilled));
        handler.addPanel("Options","Starting the options",new PipesOptions(10,10));
        handler.cheatWord("erland");
    }
    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            storage = new ParameterStorageString(new FileStorage("pipes.xml"),null,"pipes");
        }
        return storage;
    }

    public ImageHandlerInterface getImageHandler() {
        if(images==null) {
            images = new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images/pipes");
        }
        return images;
    }
}
