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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

/**
 * This is the class that controls the drawing on the screen and
 * receives the commands from the user
 */
public class RacerMain implements GamePanelInterface {
    /** Block container for the racer map */
    protected BlockContainerData cont;
    /** Indicates if the game should be quit */
    protected boolean bQuit;
    /** Key listener that listens on keyboard command */
    protected KeyListener keyListener;
    /** FPS counter object */
    protected FpsCounter fps;
    /** The racer map/track */
    protected BlockMap map;
    /** Game environment object */
    protected GameEnvironmentInterface environment;
    /** Indicates if cheat mode is activated */
    protected boolean cheatMode;
    /** Racer model object that implements all game logic */
    private RacerModelInterface model;

    /**
     * Creates a RacerMain object using the specified racer model
     * @param model The racer model that implements the game logic
     */
    public RacerMain(RacerModelInterface model) {
        this.model = model;
    }
    public boolean isExit()
    {
        return bQuit;
    }

    public void setCheatmode(boolean enable)
    {
        cheatMode = enable;
        if(!enable) {
            cheatMode = false;
        }
    }
    public void exit()
    {
        environment.getScreenHandler().getContainer().removeKeyListener(keyListener);
    }
    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
        bQuit = false;
        cheatMode = false;

        keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    bQuit = true;
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.startTurnLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.startTurnRight();
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    model.startAccellerating();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.startBraking();
                }
                if(cheatMode) {
                    if(e.getKeyCode()==KeyEvent.VK_1) {
                        model.setCheatModeParameter("ALLCARSSHOWSERVERCAR","NONE");
                    }
                    if(e.getKeyCode()==KeyEvent.VK_2) {
                        model.setCheatModeParameter("ALLCARSSHOWSERVERCAR","BOTH");
                    }
                    if(e.getKeyCode()==KeyEvent.VK_3) {
                        model.setCheatModeParameter("ALLCARSSHOWSERVERCAR","ONLY");
                    }
                    if(e.getKeyCode()==KeyEvent.VK_4) {
                        model.setCheatModeParameter("MYCARSERVERUPDATETYPE","1");
                    }
                    if(e.getKeyCode()==KeyEvent.VK_5) {
                        model.setCheatModeParameter("MYCARSERVERUPDATETYPE","2");
                    }
                }
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.stopTurnLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.stopTurnRight();
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    model.stopAccellerating();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.stopBraking();
                }

            }



        };
        environment.getScreenHandler().setCursor(null);
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addKeyListener(keyListener);

        fps = new FpsCounter(50);
        cont = new BlockContainerData(20, 20, 30,30, 32, 15,12);

        model.init(environment,cont);

        environment.getScreenHandler().getContainer().setBackground(Color.black);
        // Preload images if not already loaded
        environment.getImageHandler().getImage("car1.gif");
        environment.getImageHandler().getImage("car2.gif");
        environment.getImageHandler().getImage("car3.gif");
        environment.getImageHandler().getImage("car4.gif");
        environment.getImageHandler().getImage("mapicons.gif");
        environment.getImageHandler().getImage("specialmapicons.gif");
    }


    public void update() {
        if(model.isInitialized()) {
            model.update();

            // Scroll map
            int scrollBorderLeft = 200;
            int scrollBorderRight = 200;
            int scrollBorderUp = 200;
            int scrollBorderDown = 200;
            CarDrawInterface car = model.getMyCar();
            if(car.getPosX()<(cont.getScrollingOffsetX()+scrollBorderLeft)) {
                cont.setScrollingOffsetX(car.getPosX()-scrollBorderLeft);
            }
            if(car.getPosX()>(cont.getScrollingOffsetX()+cont.getDrawingSizeX()-scrollBorderRight)) {
                cont.setScrollingOffsetX(car.getPosX()-cont.getDrawingSizeX()+scrollBorderRight);
            }
            if(car.getPosY()<(cont.getScrollingOffsetY()+scrollBorderUp)) {
                cont.setScrollingOffsetY(car.getPosY()-scrollBorderUp);
            }
            if(car.getPosY()>(cont.getScrollingOffsetY()+cont.getDrawingSizeY()-scrollBorderDown)) {
                cont.setScrollingOffsetY(car.getPosY()-cont.getDrawingSizeY()+scrollBorderDown);
            }

            // Make sure we don't scroll the map to much
            if(cont.getScrollingOffsetX()<0) {
                cont.setScrollingOffsetX(0);
            }
            if(cont.getScrollingOffsetX()>cont.getScrollingSizeX()-cont.getDrawingSizeX()) {
                cont.setScrollingOffsetX(cont.getScrollingSizeX()-cont.getDrawingSizeX());
            }
            if(cont.getScrollingOffsetY()<0) {
                cont.setScrollingOffsetY(0);
            }
            if(cont.getScrollingOffsetY()>cont.getScrollingSizeY()-cont.getDrawingSizeY()) {
                cont.setScrollingOffsetY(cont.getScrollingSizeY()-cont.getDrawingSizeY());
            }
        }
    }

    public void draw()
    {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
        fps.update();
        fps.draw(g,Color.red, 10,10);
        g.setColor(Color.white);
        g.drawString(model.getInfoString(),50,10);
        g.setColor(Color.white);
        if(cheatMode) {
            g.setColor(Color.white);
            g.drawString("CHEATMODE",80,10);
        }
        g.setClip(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        if(model.isInitialized()) {
            model.getMap().draw(g,0);

            model.getMyCar().draw(g,0);
            CarDrawInterface cars[] = model.getOpponentCars();
            for(int i=0;i<cars.length;i++) {
                cars[i].draw(g,0);
            }
            if(!model.isStarted()) {
                g.setColor(Color.white);
                g.clearRect(50,50,400,150);
                g.drawString("Accellerate to start game",100,100);
                if(model.isMultiplayer()) {
                    if(model.getNoOfHumanCars()>1) {
                        g.drawString("Currently "+(model.getNoOfHumanCars()-1)+" other human player(s) connected",100,120);
                    }else {
                        g.drawString("Only you and computer contolled cars are connected",100,120);
                    }
                    g.drawString("You can wait for more human players to connect",100,140);
                }
            }
        }else {
            g.setColor(Color.white);
            g.clearRect(50,50,400,150);
            g.drawString("Loading game data, please wait...",100,100);
        }
    }
}
