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
