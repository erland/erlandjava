/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 1, 2003
 * Time: 3:26:45 PM
 * To change this template use Options | File Templates.
 */
package erland.game.crackout;

import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandlerApplicationInterface;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerInterface;
import erland.util.*;

import java.awt.*;

public class CrackOut extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface {
    private ParameterValueStorageExInterface storage;
    private ImageHandlerInterface images;

    public static void main(String[] args) {
        Log.setLog("crackoutlog.xml");
        CrackOut game = new CrackOut();
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
        handler.addPanel("Game","Starting the game",new CrackOutMain(10,10,15));
        handler.addPanel("Editor","Starting the editor",new CrackOutEditor(10,10,15));
        handler.cheatWord("erland");
    }
    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            storage = new ParameterStorageGroup(new FileStorage("crackout.xml"),null,"crackout","level");
        }
        return storage;
    }

    public ImageHandlerInterface getImageHandler() {
        if(images==null) {
            images = new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images/crackout");
        }
        return images;
    }
}
