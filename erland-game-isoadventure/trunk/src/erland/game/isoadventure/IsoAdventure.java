package erland.game.isoadventure;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
