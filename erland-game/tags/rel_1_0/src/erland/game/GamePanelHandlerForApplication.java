package erland.game;
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

import erland.util.*;
import erland.util.Timer;
import erland.game.FullScreenHandler;
import erland.game.component.EButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Abstract class of a game panel handler for a full screen game
 * @author Erland Isaksson
 */
public class GamePanelHandlerForApplication implements GamePanelHandlerImplementationInterface, TimerListenerInterface, GamePanelHandlerInterface, ActionListener {
    /** The application object */
    private GamePanelHandlerApplicationInterface application;
    /** The screen handler object */
    private ScreenHandlerInterface screen;
    /** The image creator object */
    private ImageCreatorInterface imageCreator;
    /** Indicates if the game should end */
    private boolean bQuit;
    /** Indicates if frame timer has elapsed and it is time to redraw the screen */
    private boolean bReadyToDraw = true;
    /** Currently active game panel */
    private int currentGame;
    /** Vector of all GamePanel object */
    private Vector games;
    /** Array with all buttons which selects the game panels */
    private EButton buttons[];
    /** Array with all extra buttons for game panel handler */
    private EButton extraButtons[];
    /** The currently active cursor */
    private Cursor cursor;
    /** Message that is displayed when a new game panel loads */
    private String waitMessage;
    /** Word that is used to enter cheat mode */
    private String cheatWord;
    /** Number of characters in the cheatword that has been entered */
    private int cheatWordCounter;
    /** Indicates if cheat mode is active(true) or disabled(false) */
    private boolean cheatMode = false;
    /** High score object */
    private HighScoreInterface highScore;
    /** Indicates if the user choosed to run in windowed och full screen mode */
    private boolean bWindowed=false;
    /** Timer object */
    private Timer timer;
    /** Storage object */
    private ParameterValueStorageExInterface storage;
    /** Image handler object */
    private ImageHandlerInterface imageHandler;

    public GamePanelHandlerForApplication() {
        storage = null;
        imageHandler = null;
    }

    public void actionPerformed(ActionEvent e) {
        if(currentGame==0) {
            if(extraButtons!=null && e.getSource()==extraButtons[0].getComponent()) {
                bQuit = true;
            }else {
                for(int i=0;i<buttons.length;i++) {
                    if(e.getSource()==buttons[i].getComponent()) {
                        for(int j=0;j<buttons.length;j++) {
                            buttons[j].getComponent().setVisible(false);
                        }
                        setExtraButtonsVisible(false);
                        waitMessage = ((GamePanelData)games.elementAt(i)).getDescription();
                        cheatMode = false;
                        cheatWordCounter = 0;
                        timer.stop();
                        ((GamePanelData)games.elementAt(i)).getPanel().init(this);
                        timer.start();
                        waitMessage = null;
                        currentGame=i+1;
                    }
                }
            }
        }
    }

    public void addPanel(String name, String description, GamePanelInterface panel)
    {
        GamePanelData game = new GamePanelData(name,description,panel);
        games.addElement(game);
    }


    /**
     * returns delay of number of milliseconds between each frame
     * @return Number of milliseconds requested between each frame
     */
    public int getFrameDelay()
    {
        return 15;
    }

    public boolean getFullScreen() {
        return true;
    }

    protected ScreenHandlerInterface createScreenHandler() {
        return new FullScreenHandler(getApplication().getDisplayModes(),bWindowed?false:getFullScreen(),bWindowed?true:!getFullScreen());
    }

    protected ImageCreatorInterface createImageCreator() {
        return (ImageCreatorInterface) getScreenHandler();
    }

    protected void setExtraButtonsVisible(boolean visible) {
        if(extraButtons!=null) {
            for(int i=0;i<extraButtons.length;i++) {
                extraButtons[i].getComponent().setVisible(visible);
            }
        }
    }
    public void initExtraButtons(int firstX, int firstY,int buttonWidth, int buttonHeight,int buttonSpaceBetween) {
        extraButtons = new EButton[1];
        extraButtons[0] = EButton.create("Exit");
        extraButtons[0].getComponent().setBounds(firstX,firstY,buttonWidth,buttonHeight);
        extraButtons[0].addActionListener(this);
        screen.add(extraButtons[0].getComponent());
    }
    /**
     * Initialization method
     */
    protected void initHandler()
    {
        bQuit = false;
        screen = getScreenHandler();
        screen.getContainer().requestFocus();

        games = new Vector(0);
        getApplication().initGames(this);
        if(getHighScore()==null) {
            if(getStorage()!=null) {
                setHighScore(new HighScore(getStorage()));
            }
        }
        KeyListener cheatListener = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==cheatWord.charAt(cheatWordCounter)) {
                    cheatWordCounter++;
                    if(cheatWordCounter>=cheatWord.length()) {
                        cheatWordCounter = 0;
                        if(currentGame>0) {
                            if(cheatMode) {
                                cheatMode = false;
                            }else {
                                cheatMode = true;
                            }
                            ((GamePanelData)games.elementAt(currentGame-1)).getPanel().setCheatmode(cheatMode);
                        }
                    }
                }
            }
        };
        screen.getContainer().addKeyListener(cheatListener);
        if(games.size()>1) {
            currentGame = 0;
            buttons = new EButton[games.size()];
            final int buttonHeight = 30;
            final int buttonWidth = 100;
            final int buttonSpaceBetween = 10;
            int buttonY=0;
            final int buttonX=10;
            for(int i=0;i<games.size();i++) {
                buttons[i] = EButton.create(((GamePanelData)games.elementAt(i)).getName());
                buttonY =buttonSpaceBetween+i*(buttonHeight+buttonSpaceBetween);
                buttons[i].getComponent().setBounds(buttonX,buttonY,buttonWidth,buttonHeight);
                buttons[i].addActionListener(this);
                screen.add(buttons[i].getComponent());
            }
            initExtraButtons(buttonX,buttonY+buttonHeight+buttonSpaceBetween,buttonWidth,buttonHeight,buttonSpaceBetween);
        }else {
            currentGame=1;
            ((GamePanelData)games.elementAt(currentGame-1)).getPanel().init(this);
            buttons = null;
            extraButtons = null;
        }
        cursor = screen.getContainer().getCursor();
        screen.getContainer().setBackground(Color.black);
    }

    public void tick()
    {
        bReadyToDraw = true;
    }

    public boolean askUserForFullscreen() {
        Object[] possibleValues = { "Full screen", "Window"};
        int selectedValue = JOptionPane.showOptionDialog(null,
                "Choose screen mode", "Screen mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        if(selectedValue==0) {
            return true;
        }else {
            return false;
        }
    }
    public void askUserForOptions() {
    }

    public GamePanelHandlerApplicationInterface getApplication() {
        return application;
    }
    public void init() {
        bWindowed = !askUserForFullscreen();
        askUserForOptions();
        initHandler();
    }
    /**
     * Main run method
     */
    public void run(GamePanelHandlerApplicationInterface application)
    {
        this.application = application;
        init();
        timer = new Timer(getFrameDelay(), this);
        timer.start();
        try {
            while(!bQuit && !screen.isExit()) {
                if(currentGame>0) {
                    ((GamePanelData)games.elementAt(currentGame-1)).getPanel().update();
                }
                while(!bReadyToDraw) {
                    if(timer.isRunning()) {
                        Thread.yield();
                    }else {
                        Thread.sleep(500);
                    }
                }
                bReadyToDraw = false;
                if(currentGame>0) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }
                    if(!((GamePanelData)games.elementAt(currentGame-1)).getPanel().isExit()) {

                        ((GamePanelData)games.elementAt(currentGame-1)).getPanel().draw();

                    }else {
                        ((GamePanelData)games.elementAt(currentGame-1)).getPanel().exit();
                        if(buttons!=null) {
                            for(int i=0;i<buttons.length;i++) {
                                buttons[i].getComponent().setVisible(true);
                            }
                            setExtraButtonsVisible(true);
                            currentGame=0;
                            getScreenHandler().getContainer().setCursor(cursor);
                        }else {
                            currentGame=0;
                            bQuit = true;
                        }
                    }
                }
                if(currentGame==0) {
                    Graphics g = getScreenHandler().getCurrentGraphics();
                    g.clearRect(0,0,getScreenHandler().getWidth(),getScreenHandler().getHeight());
                    getScreenHandler().paintComponents(g);
                    if(waitMessage!=null) {
                        g.setColor(Color.white);
                        g.drawString(waitMessage,250,200);
                        g.drawString("Please wait...",250,240);
                    }
                }
                getScreenHandler().swapScreens();
            }
            if(currentGame>0) {
                ((GamePanelData)games.elementAt(currentGame-1)).getPanel().exit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getScreenHandler().dispose();
        timer.stop();
    }

    public ScreenHandlerInterface getScreenHandler() {
        if(screen==null) {
            screen = createScreenHandler();
        }
        return screen;
    }

    public ParameterValueStorageExInterface getStorage() {
        return storage;
    }
    /**
     * Set the storage object that should be used
     * @param storage The storage object that should be used
     */
    public void setStorage(ParameterValueStorageExInterface storage) {
        this.storage = storage;
    }

    public ImageHandlerInterface getImageHandler() {
        return imageHandler;
    }
    /**
     * Set the image handler that should be used
     * @param imageHandler The image handler
     */
    public void setImageHandler(ImageHandlerInterface imageHandler) {
        this.imageHandler = imageHandler;
    }
    public ImageCreatorInterface getImageCreator() {
        if(imageCreator==null) {
            imageCreator = createImageCreator();
        }
        return imageCreator;

    }

    public HighScoreInterface getHighScore() {
        return highScore;
    }
    /**
     * Set high score object to use in game
     * @param highScore
     */
    protected void setHighScore(HighScoreInterface highScore) {
        this.highScore = highScore;
    }

    public HighScoreListInterface getHighScoreList() {
        return null;
    }
    public void cheatWord(String cheatWord) {
        this.cheatWord = cheatWord;
        this.cheatWordCounter = 0;
    }

    /**
     * Class that contains all data needed for handling each game panel object
     */
    private class GamePanelData {
        /** Name of game panel */
        private String name;
        /** Description of game panel */
        private String description;
        /** The game panel object itself */
        private GamePanelInterface panel;
        /**
         * Creates a new GamePanelData object
         * @param name Name of the game panel
         * @param description Description of the game panel
         * @param panel The GamePanel object
         */
        public GamePanelData(String name, String description, GamePanelInterface panel) {
            this.name = name;
            this.description = description;
            this.panel = panel;
        }
        /**
         * Get the name of the game panel
         * @return The name of the game panel
         */
        public String getName() {
            return name;
        }
        /**
         * Get the description of the game panel
         * @return The description of the game panel
         */
        public String getDescription() {
            return description;
        }
        /**
         * Get the GamePanel object associated with this game panel
         * @return The GamePanel object
         */
        public GamePanelInterface getPanel() {
            return panel;
        }
    }
}
