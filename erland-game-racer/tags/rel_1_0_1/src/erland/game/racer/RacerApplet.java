package erland.game.racer;
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

import erland.game.*;
import erland.game.component.EComponentMode;
import erland.util.*;

import java.applet.Applet;
import java.awt.*;
/**
 * This class implements the applet version of the game
 */
public class RacerApplet extends Applet implements GamePanelHandlerApplicationInterface, Runnable {
    /** Main thread in the applet */
    protected Thread animator;

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] displayModes = new DisplayMode[] {
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,16,0),
            new DisplayMode(640,480,8,0)};
        return displayModes;
    }

    public void initGames(GamePanelHandlerInterface handler) {
        handler.addPanel("Game Multiplayer","Starting the game",new RacerMain(new RacerModelNetworked(this.getDocumentBase().getHost(),"player","player")));
        handler.addPanel("Game","Starting the game",new RacerMain(new RacerModelStandalone()));
        //handler.addPanel("Editor","Starting the map editor",new RacerMapTrackEditor());
        //handler.addPanel("Block Edit","Starting the block editor",new RacerMapBlockEditor());
        //handler.addPanel("Start Block Edit", "Staring the start block editor",new RacerMapStartBlockEditor());
        //handler.addPanel("Icon Edit","Starting the icon editor",new RacerMapIconEditor());
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
                    new CookieStorage(this,"racer"),
                    new JarFileStorage("Racer.xml"),
                    "racer");
        }
        ImageHandlerInterface imageHandler = new ImageHandlerForApplet(this,"images");
        GamePanelHandlerForApplet handler = new GamePanelHandlerForApplet(this);
        handler.setImageHandler(imageHandler);
        handler.setStorage(storage);
        EComponentMode.setAppletMode(true);
        while(animator!=null) {
            GamePanelHandler.run(this,handler);
        }
    }
}
