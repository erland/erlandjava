package erland.game.tetris;
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

import erland.game.GamePanelHandlerInterface;
import erland.game.GamePanelHandler;
import erland.game.GamePanelHandlerForApplication;
import erland.game.GamePanelHandlerApplicationInterface;
import erland.util.*;

import javax.swing.*;
import java.awt.*;

public class Tetris extends GamePanelHandlerForApplication implements GamePanelHandlerApplicationInterface {
    private ParameterValueStorageExInterface storage;
    private FileStorage file;
    private ImageHandlerInterface images;
    private String hostname;
    private String name;
    private TetrisMain panel;
    private boolean bMultiplayer;

    public static void main(String[] args) {
        Tetris game;
        if(args.length>1) {
            game = new Tetris(args[1],args[0]);
        }else if(args.length==1) {
            game = new Tetris("erland.homeip.net",args[0]);
        }else {
            game = new Tetris("erland.homeip.net","player");
        }
        GamePanelHandler.run(game,game);
        System.exit(-1);
    }

    public Tetris(String hostname,String name) {
        this.hostname = hostname;
        this.name = name;
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
            String name = JOptionPane.showInputDialog("Please enter your name",this.name);
            if(name!=null) {
                this.name = name;
            }
        }
    }
    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        if(bMultiplayer) {
            panel = new TetrisMain(new TetrisModelNetworked(hostname,name,name));
        }else {
            panel = new TetrisMain(new TetrisModelStandalone());
        }
        handler.addPanel("Game","Starting the game",panel);
        handler.cheatWord("erland");
    }

    public ImageHandlerInterface getImageHandler() {
        if(images==null) {
            images = new ImageHandlerForApplicationJarFile(getScreenHandler().getContainer(),"images/tetris");
        }
        return images;
    }

    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            file = new FileStorage("tetris.xml");
            storage = new ParameterStorageString(file,null,"tetris");
        }
        return storage;
    }

}
