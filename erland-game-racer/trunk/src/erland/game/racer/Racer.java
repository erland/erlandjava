package erland.game.racer;

import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandlerApplicationInterface;
import erland.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * This is the main class for the client part of the racer game
 */
public class Racer extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface {
    /** Indicates if this is a multiplayer game or not */
    private boolean bMultiplayer;
    /** User name when in multiplayer mode */
    private String username;
    /** Hostname of server when in multiplayer mode */
    private String hostname;

    /**
     * Main method that starts the game
     * @param args hostname username
     */
    public static void main(String args[])
    {
        Log.setLog("racerlog.xml");
        Racer game;
        if(args.length>1) {
            game = new Racer(args[0],args[1]);
        }else {
            game = new Racer("erland.homeip.net","player");
        }
        GamePanelHandler.run(game,game);
        System.exit(-1);
    }

    /**
     * Creates the main game object
     * @param hostname Hostname (when in multiplayer mode)
     * @param name User name (when in multiplayer mode)
     */
    public Racer(String hostname,String name) {
        this.hostname = hostname;
        this.username = name;
    }
    public DisplayMode[] getDisplayModes()
    {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }
    public void askUserForOptions() {
        Object[] possibleValues = { "Standalone single player", "Networked multiplayer"};
        int selectedValue  = JOptionPane.showOptionDialog(null,
                "Choose game mode", "Game mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        if(selectedValue==0) {
            bMultiplayer = false;
        }else {
            bMultiplayer = true;
        }
        if(bMultiplayer) {
            String hostname = JOptionPane.showInputDialog("Please enter hostname",this.hostname);
            if(hostname!=null) {
                this.hostname=hostname;
            }
            String name = JOptionPane.showInputDialog("Please enter your name",this.username);
            if(name!=null) {
                this.username = name;
            }
        }
    }

    public void initGames(GamePanelHandlerInterface handler)
    {
        StorageInterface jarfile = new JarFileStorage("Racer.xml");
        StorageInterface file = new FileStorage("Racer.xml");
        setStorage(new ParameterStorageString(file,jarfile,"racer"));
        setImageHandler(new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images"));
        if(bMultiplayer) {
            handler.addPanel("Game","Starting the game",new RacerMain(new RacerModelNetworked(hostname,username,username)));
        }else {
            handler.addPanel("Game","Starting the game",new RacerMain(new RacerModelStandalone()));
            handler.addPanel("Editor","Starting the map editor",new RacerMapTrackEditor());
            handler.addPanel("Block Edit","Starting the block editor",new RacerMapBlockEditor());
            handler.addPanel("Start Block Edit", "Staring the start block editor",new RacerMapStartBlockEditor());
            handler.addPanel("Icon Edit","Starting the icon editor",new RacerMapIconEditor());
        }
        handler.cheatWord("erland");
    }
}
