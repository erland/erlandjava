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

import erland.game.GamePanelInterface;
import erland.game.FpsCounter;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerData;

import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;

/**
 * This is the class that controls the drawing on the screen and
 * receives the commands from the user
 */
public class IsoAdventureMain implements GamePanelInterface {
    /** Block container for the racer map */
    protected IsoBlockContainerData cont;
    /** Indicates if the game should be quit */
    protected boolean bQuit;
    /** Key listener that listens on keyboard command */
    protected KeyListener keyListener;
    /** FPS counter object */
    protected FpsCounter fps;
    /** Game environment object */
    protected GameEnvironmentInterface environment;
    /** Indicates if cheat mode is activated */
    protected boolean cheatMode;

    /** IsoAdventure model object that implements all game logic */
    private IsoAdventureModelInterface model;

    /**
     * Creates a RacerMain object using the specified racer model
     * @param model The racer model that implements the game logic
     */
    public IsoAdventureMain(IsoAdventureModelInterface model) {
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
                    model.startMoveLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.startMoveRight();
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    model.startMoveUp();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.startMoveDown();
                }else if(e.getKeyCode() == KeyEvent.VK_F1) {
                    model.start();
                }else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    model.jump();
                }
                if(cheatMode) {
                    if(e.getKeyCode()==KeyEvent.VK_1) {
                        //model.setCheatModeParameter("ALLCARSSHOWSERVERCAR","NONE");
                    }
                }
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.stopMoveLeft();
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.stopMoveRight();
                }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    model.stopMoveUp();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.stopMoveDown();
                }
            }
        };
        environment.getScreenHandler().setCursor(null);
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addKeyListener(keyListener);

        fps = new FpsCounter(50);
        cont = new IsoBlockContainerData(20, 20, 38,38, 32, 16,8,600,400);

        model.init(environment,cont);

        environment.getScreenHandler().getContainer().setBackground(Color.black);
    }

    private void updateScrollOffset(IsoObjectInterface obj) {
        // Scroll map
        int scrollBorderLeft = 100;
        int scrollBorderRight = 100;
        int scrollBorderUp = 100;
        int scrollBorderDown = 100;
        if(obj.getPixelPosX()<(cont.getScrollingOffsetX()+scrollBorderLeft)) {
            cont.setScrollingOffsetX(obj.getPixelPosX()-scrollBorderLeft);
        }
        if(obj.getPixelPosX()>(cont.getScrollingOffsetX()+cont.getDrawingSizeX()-scrollBorderRight)) {
            cont.setScrollingOffsetX(obj.getPixelPosX()-cont.getDrawingSizeX()+scrollBorderRight);
        }
        if(obj.getPixelPosY()<(cont.getScrollingOffsetY()+scrollBorderUp)) {
            cont.setScrollingOffsetY(obj.getPixelPosY()-scrollBorderUp);
        }
        if(obj.getPixelPosY()>(cont.getScrollingOffsetY()+cont.getDrawingSizeY()-scrollBorderDown)) {
            cont.setScrollingOffsetY(obj.getPixelPosY()-cont.getDrawingSizeY()+scrollBorderDown);
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
    public void update() {
        if(model.isInitialized()) {
            model.update();
            updateScrollOffset(model.getPlayerObject());
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
            model.getMap().draw(g);
            if(!model.isStarted()) {
                g.setColor(Color.white);
                g.clearRect(50,50,400,150);
                g.drawString("Press space to start game",100,100);
                if(model.isMultiplayer()) {
                    if(model.getNoOfHumanPlayers()>1) {
                        g.drawString("Currently "+(model.getNoOfHumanPlayers()-1)+" other human player(s) connected",100,120);
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
