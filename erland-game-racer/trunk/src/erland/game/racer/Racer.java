package erland.game.racer;

import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.util.*;

import java.awt.*;


public class Racer extends GamePanelHandler {
    protected ImageHandlerInterface images;
    protected ParameterValueStorageInterface storage;
    protected StorageInterface file;

    public static void main(String args[])
    {
        Log.setLog("racerlog.xml");
        Racer game = new Racer();
        game.run();
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
        handler.addPanel("Game","Starting the game",new RacerMain());
        handler.addPanel("Editor","Starting the map editor",new RacerMapEditor());
        handler.addPanel("Block Edit","Starting the block editor",new RacerMapBlockEditor());
        handler.addPanel("Icon Edit","Starting the icon editor",new RacerMapIconEditor());
        getImageHandler().getImage("car.gif");
        getImageHandler().getImage("mapicons.gif");
    }

    public ImageHandlerInterface getImageHandler()
    {
        if(images==null) {
            images = new ImageHandlerForApplication(getScreenHandler().getFrame(),"images");
        }
        return images;
    }

    public ParameterValueStorageInterface getStorage()
    {
        if(storage==null) {
            file = new FileStorage("racer.xml");
            storage = new ParameterStorageString(file,"racer");
        }
        return storage;
    }
}
