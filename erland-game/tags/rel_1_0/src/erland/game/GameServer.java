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

import erland.network.NetworkServer;
import erland.network.NetworkConnectionFactory;
import erland.network.NetworkServerListenerInterface;
import erland.network.NetworkConnectionInterface;
import erland.util.Timer;
import erland.util.TimerListenerInterface;

import java.util.Vector;
import java.io.IOException;

/**
 * A network game server that receive and manage game player connections on a specified port
 * @author Erland Isaksson
 */
public abstract class GameServer implements NetworkServerListenerInterface, TimerListenerInterface, GameServerEnvironmentInterface {
    /** The network server object */
    private NetworkServer server;
    /** The currently connected game player */
    private Vector players;
    /** The currently connected game players */
    private GamePlayerInterface[] playerList;
    /** Indicates if server needs to be updated */
    private boolean bUpdate;
    /** Timer object that is used to update the server at regular interval */
    private Timer timer;
    /** Indicates if the game has been started */
    private boolean bStarted;
    /** Indicates if the game has been initialied */
    private boolean bInitialized;
    /** Highscore object */
    private HighScore highScore;
    /** Synchronization object, used to make sure that only one client command is handled at one time */
    private Object syncObj = new Object();

    /**
     * Start and run the game server on the specified port
     * This method will not return until the game server is shutdown
     * @param port The port to use when listening for new game players
     */
    public void run(int port) {
        server = new NetworkServer();
        players = new Vector();
        bUpdate = false;
        timer = new Timer(getUpdateDelay(),this);
        init();
        try {
            server.start(new NetworkConnectionFactory(),port,this);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            System.exit(-1);
        }

        while(true) {
            while(!isStarted()) {
                try {
                    Thread.sleep(getCheckStartedDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
            timer.start();
            while(isStarted()) {
                while(!bUpdate) {
                    Thread.yield();
                }
                bUpdate = false;
                synchronized (syncObj) {
                    if(!isEndGame()) {
                        if(isStarted()) {
                            update();
                        }
                    }else {
                        if(endGame()) {
                            writeMessage("ENDED");
                            bStarted = false;
                        }

                    }
                }
            }
            timer.stop();
        }

    }

    public void tick() {
        bUpdate = true;
    }

    /**
     * Update player list with new or disconnected game players
     */
    private void updatePlayerList() {
        playerList = new GamePlayerInterface[players.size()];
        for (int i = 0; i < players.size(); i++) {
            GamePlayerInterface player = (GamePlayerInterface) players.elementAt(i);
            playerList[i]=player;
        }
    }

    /**
     * Get an array with all connected players
     * @return An array with all connected players
     */
    protected GamePlayerInterface[] getPlayers() {
        return playerList;
    }

    /**
     * Get currently number of players
     * @return Currently number of players
     */
    protected int getNoOfPlayers() {
        return players.size();
    }

    public HighScoreInterface getHighScore() {
        if(highScore==null) {
            highScore = new HighScore(getStorage());
            highScore.load();
        }
        return highScore;
    }

    public HighScoreListInterface getHighScoreList() {
        return null;
    }

    /**
     * Writes a message to all connected players
     * @param message The message to write
     */
    protected void writeMessage(String message) {
        synchronized (syncObj) {
            server.write(message);
        }
    }

    /**
     * Calls the {@link #message(erland.game.GamePlayerInterface, java.lang.String)} method for the player
     * from which the message is recieved from
     * @param connection Connection which the messages was received from
     * @param message The received message
     */
    public void message(NetworkConnectionInterface connection, String message) {
        synchronized (syncObj) {
            for (int i = 0; i < players.size(); i++) {
                GamePlayerInterface player = (GamePlayerInterface) players.elementAt(i);
                if(player.getConnection()==connection) {
                    message(player,message);
                }
            }
        }
    }

    /**
     * Update all players and the game logic. This method will do the following:
     * <ol>
     * <li>Call {@link #initUpdate()}</li>
     * <li>Call {@link #updatePlayer(GamePlayerInterface)} on each player</li>
     * <li>Call {@link #updateGame()}</li>
     * <li>Call {@link #endUpdate()}</li>
     * </ol>
     * You should try to override the methods above instead of overriding the {@link #update()} method
     */
    protected void update() {
        initUpdate();
        for (int i = 0; i < players.size(); i++) {
            GamePlayerInterface player = (GamePlayerInterface) players.elementAt(i);
            updatePlayer(player);
        }
        updateGame();
        endUpdate();
    }

    /**
     * Creates and initializes a new player, this method will call the following methods
     * <ol>
     * <li>Call {@link #createPlayer()} to create the new player object</li>
     * <li>Call {@link GamePlayerInterface#setConnection(NetworkConnectionInterface)} on the new player object</li>
     * <li>Call {@link #connected(GamePlayerInterface)}
     * </ol>
     * You should try to override the above methods instead of overriding this method
     * @param connection
     */
    public void connected(NetworkConnectionInterface connection) {
        synchronized (syncObj) {
            GamePlayerInterface player = createPlayer();
            player.setConnection(connection);
            players.add(player);
            updatePlayerList();
            connected(player);
        }
    }

    /**
     * Disconnects a player, this method will call {@link #disconnected(GamePlayerInterface)} for the disconnected player.
     * You should try to override the {@link #disconnected(GamePlayerInterface)} instead of overriding this method
     * @param connection
     */
    public void disconnected(NetworkConnectionInterface connection) {
        synchronized (syncObj) {
            GamePlayerInterface player = null;
            for (int i = 0; i < players.size(); i++) {
                player = (GamePlayerInterface) players.elementAt(i);
                if(player.getConnection()==connection) {
                    players.remove(i);
                    updatePlayerList();
                    break;
                }
            }
            if(player!=null) {
                disconnected(player);
            }
            if(players.size()==0) {
                bStarted = !endGame();
            }
        }
    }

    /**
     * Called when a message is received from a specified player.
     * This method will handle the following commands automatically and calls {@link #command(erland.game.GamePlayerInterface, java.lang.String)}
     * for unknown commands:
     * <ul>
     * <li><code>INIT</code> - Will call {@link #initGame()} once and {@link #initPlayer(erland.game.GamePlayerInterface)} for the player sending the command</li>
     * <li><code>START</code> - Will call {@link #startGame()} onde and {@link #startPlayer(erland.game.GamePlayerInterface)} for each connected player</li>
     * <li><code>STOP</code> - Will call {@link #stopGame()}</li>
     * </ul>
     * Override the {@link #command(erland.game.GamePlayerInterface, java.lang.String)} method instead if you want to have the standard commands
     * @param player The player which the messages was received from
     * @param message The received message
     */
    protected void message(GamePlayerInterface player,String message) {
        if(message.equals("INIT")) {
            if(!isInitialized()) {
                bInitialized = initGame();
            }
            if(isInitialized()) {
                initPlayer(player);
                player.getConnection().write("INITED");
            }
        }else if(message.equals("START")) {
            if(!isStarted()) {
                bStarted = startGame();
                for(int i=0;i<players.size();i++) {
                    GamePlayerInterface p = (GamePlayerInterface) players.elementAt(i);
                    startPlayer(p);
                }
            }
            if(isStarted()) {
                writeMessage("STARTED");
            }
        }else if(message.equals("STOP")) {
            if(isStarted()) {
                bStarted = !stopGame();
                if(!isStarted()) {
                    writeMessage("STOPPED");
                }
            }
        }else {
            command(player,message);
        }
    }

    /**
     * The number of milliseconds between the checks if the game should be started.
     * Override this method if you want to control the delay from requested start of the
     * game until the game starts.
     * @return
     */
    protected int getCheckStartedDelay() {
        return 500;
    }

    /**
     * The number of milliseconds between game logic is updated when the game is started.
     * Override this method if you want to control how often the game logic is updated.
     * @return The number of milliseconds between game logic is updated
     */
    protected int getUpdateDelay() {
        return 15;
    }

    /**
     * Get maximum number of players, -1 means unlimited
     * Override this method if you want to set a maximum number of allowed players
     * @return Maximum number of players
     */
    protected int getMaxPlayers() {
        return -1;
    }

    /**
     * Called to check if game has been initialized
     * @return true/false (initialized/not initialized)
     */
    protected boolean isInitialized() {
        return bInitialized;
    }

    /**
     * Called to check if the game has been ended
     * @return
     */
    protected boolean isEndGame() {
        return false;
    }

    /**
     * Called to check if game is started
     * @return true/false (started/not started)
     */
    protected boolean isStarted() {
        return bStarted;
    }

    /**
     * Called once when the game server starts to initialize the game server
     * Override this method if you want to do some initialization before the game
     * server starts
     */
    protected void init() {

    }

    /**
     * Initialize a new game.
     * Override this method if you want to do some initialization when a new game is initiated
     * @return true/false (success/failure)
     */
    protected boolean initGame() {
        return true;
    }

    /**
     * Starts the game.
     * Override this method if you want to do something when a new game starts
     * @return true/false (success/failure)
     */
    protected boolean startGame() {
        return true;
    }

    /**
     * Stop the game.
     * Override this method if you want to controll when the game is allowed to be stopped
     * @return true/false (success/failure)
     */
    protected  boolean stopGame() {
        return true;
    }

    /**
     * Requests that the game is ended.
     * Override this method if you want to do something when the game is requested to end
     * @return true/false (success/failure)
     */
    protected boolean endGame() {
        return true;
    }

    /**
     * Called when a new player has been created and should
     * be initialized.
     * Override this method if you want to do some initialization for a each player when a new game is initialized
     * @param player
     */
    protected void initPlayer(GamePlayerInterface player) {

    }

    /**
     * Starts a player.
     * Override this method if you want to do something for each player when a new game starts
     * @param player The player that should be started
     */
    protected void startPlayer(GamePlayerInterface player) {
        // Do nothing
    }

    /**
     * Called once before each time the game logic should be updated.
     * Override this method if you want to do some initialization before each
     * update of the game logic
     */
    protected void initUpdate() {
        // Do nothing
    }
    /**
     * Called when a specific player should be updated.
     * Override this method if you want to do some update of each player before
     * each time the game logic is updated
     * @param updatePlayer The player that should be updated
     */
    protected void updatePlayer(GamePlayerInterface updatePlayer) {
        // Do nothing
    }
    /**
     * Called once when the main game logic should be updated.
     * Override this method if you want to update the game logic at regular interval
     */
    protected void updateGame() {
        // Do nothing
    }

    /**
     * Called once after each time the game logic should be updated
     * Override this method if you need to do some clean-up after each time the game logic
     * has been updated
     */
    protected void endUpdate() {
        // Do nothing
    }

    /**
     * Checks if game is started and if maximum players has been reach. If game has been started
     * or if maximum players has been reach it will not allow new players to connect.
     * Override this method if you want to do some checking when a game player tries to login
     * @param connection The connection object of the new player
     * @param username The username of the new player
     * @param password The password of the new player
     * @return true/false (Allow player/Do not allow player)
     */
    public boolean login(NetworkConnectionInterface connection, String username, String password) {
        if(!isStarted() && (getMaxPlayers()==-1 || players.size()<getMaxPlayers())) {
            return true;
        }else {
            return false;
        }
    }


    /**
     * Called when a new player has been connected to the game.
     * Override this method if you want to do something when a player connects to the game server
     * @param player The newly connected player
     */
    protected void connected(GamePlayerInterface player) {
    }

    /**
     * Called when a player has been disconnected/removed.
     * Override this method if you want to do something when a player disconnects from the game
     * @param player The removed player
     */
    protected void disconnected(GamePlayerInterface player) {
    }

    /**
     * Creates a new player object.
     * This method must be implemented by the subclass
     * @return The newly created player object
     */
    protected abstract GamePlayerInterface createPlayer();


    /**
     * Called when a command is received from a specific player.
     * This method must be implemented by the subclass
     * @param player The player which the command was received from
     * @param message The command string
     */
    protected abstract void command(GamePlayerInterface player, String message);
}
