package erland.game.isoadventure;

import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandlerApplicationInterface;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.util.*;

import java.awt.*;

/**
 * This is the main class for the client part of the racer game
 */
public class IsoAdventure extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface {

    /**
     * Main method that starts the game
     * @param args hostname username
     */
    public static void main(String[] args) {
        IsoAdventure game = new IsoAdventure();
        GamePanelHandler.run(game,game);
        System.exit(-1);
    }

    public DisplayMode[] getDisplayModes()
    {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler)
    {
        StorageInterface jarfile = new JarFileStorage("IsoAdventure.xml");
        StorageInterface file = new FileStorage("IsoAdventure.xml");
        setStorage(new ParameterStorageString(file,jarfile,"isoadventure"));
        setImageHandler(new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images"));
        handler.addPanel("Game","Starting the game",new IsoAdventureMain(new IsoAdventureModelStandalone()));
        handler.cheatWord("erland");
    }
}
