package erland.game.tetris;

import erland.util.*;
import erland.game.*;


public class TetrisServer extends GameServer implements GameServerEnvironmentInterface {
    private HighScore highScore;
    private ParameterValueStorageExInterface storage;
    private FileStorage file;
    private boolean bEndGame;

    public static void main(String[] args) {
        TetrisServer me = new TetrisServer();
        me.run(1123);
    }

    protected int getMaxPlayers() {
        return 2;
    }

    protected GamePlayerInterface createPlayer() {
        return new TetrisPlayer(this);
    }

    public void initPlayer(GamePlayerInterface player) {
        sendConnectionStatus(player);
        updateConnectionStatus(player,"CONNECTED");
    }

    public void disconnected(GamePlayerInterface player) {
        updateConnectionStatus(player,"DISCONNECTED");
    }

    public void command(GamePlayerInterface player, String message) {
            ((TetrisPlayer)player).doCommand(message);
    }

    public boolean startGame() {

        bEndGame = false;
        BlockFactory.getInstance().reset();
        return true;
    }

    protected void startPlayer(GamePlayerInterface player) {
        ((TetrisPlayer)player).startNewGame();
    }

    public void sendConnectionStatus(GamePlayerInterface me) {

        GamePlayerInterface[] players = getPlayers();
        for(int i = 0; i < players.length;i++) {
            TetrisPlayer player = (TetrisPlayer) players[i];
            if(player==me) {
                me.getConnection().write("1"+player.toString()+"CONNECTED");
            }else {
                me.getConnection().write("2"+player.toString()+"CONNECTED");
            }
        }
    }

    public void updateConnectionStatus(GamePlayerInterface me, String status) {
        GamePlayerInterface[] players = getPlayers();
        for (int i = 0; i < players.length;i++) {
            TetrisPlayer player = (TetrisPlayer) players[i];
            if(player!=me) {
                player.getConnection().write("2"+me.toString()+status);
            }
        }
    }

    public void sendUpdate(TetrisPlayer me, String message) {
        GamePlayerInterface[] players = getPlayers();
        for (int i = 0; i < players.length;i++) {
            TetrisPlayer player = (TetrisPlayer) players[i];
            if(player==me) {
                player.getConnection().write("1"+message);
            }else {
                player.getConnection().write("2"+message);
            }
        }
    }



    protected boolean isEndGame() {
        return bEndGame;
    }

    protected void updatePlayer(GamePlayerInterface player) {
        TetrisPlayer p = (TetrisPlayer) player;
        if(!bEndGame && !p.isGameOver()) {
            if(p.update() || p.needUpdate()) {
                String s = player.toString();
                if(p.isGameOver()) {
                    if(p.isCompleted()) {
                        sendUpdate(p,s+"COMPLETE");
                    }else {
                        sendUpdate(p,s+"GAMEOVER");
                    }
                }else {
                    sendUpdate(p,s+"RUNNING");
                }
            }
        }
    }
    protected void updateGame() {
        boolean bGameOver = true;
        GamePlayerInterface[] players = getPlayers();
        for(int i = 0; i < players.length;i++) {
            TetrisPlayer player = (TetrisPlayer) players[i];
            if(!player.isGameOver()) {
                bGameOver = false;
            }
        }
        if(bGameOver) {
            bEndGame = true;
        }
    }


    public ParameterValueStorageExInterface getStorage() {
        if(storage==null) {
            file = new FileStorage("tetris.xml");
            storage = new ParameterStorageString(file,null,"tetris");
        }
        return storage;
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
}
