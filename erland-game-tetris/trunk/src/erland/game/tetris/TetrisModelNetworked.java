package erland.game.tetris;

import erland.network.NetworkConnectionInterface;
import erland.network.NetworkClientListenerInterface;
import erland.network.NetworkClient;
import erland.game.GameEnvironmentInterface;

public class TetrisModelNetworked implements TetrisModelInterface,NetworkClientListenerInterface {
    private BlockContainerData mainContainer;
    private Block nextBlock;
    private BlockContainerData opponentMainContainer;
    private boolean bEnd;
    private boolean bStarted;
    private boolean bCompleted;
    private String highScore;
    private String score;
    private String opponentScore;
    private String level;
    private String opponentLevel;
    private boolean bOpponentConnected;
    private NetworkConnectionInterface connection;
    private String hostname;
    private String username;
    private String password;

    public TetrisModelNetworked(String hostname,String username,String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public BlockContainerData getMainContainer() {
        return mainContainer;
    }


    public Block getNextBlock() {
        return nextBlock;
    }

    public BlockContainerData getOpponentMainContainer() {
        return opponentMainContainer;
    }

    public boolean isEnd() {
        return bEnd;
    }

    public boolean isStarted() {
        return bStarted;
    }

    public boolean isCompleted() {
        return bCompleted;
    }

    public String getHighScore() {
        return highScore!=null?highScore:"";
    }

    public String getScore() {
        return score!=null?score:"";
    }

    public String getOpponentScore() {
        return opponentScore!=null?opponentScore:"";
    }

    public String getLevel() {
        return level!=null?level:"";
    }

    public String getOpponentLevel() {
        return opponentLevel!=null?opponentLevel:"";
    }

    public boolean isMultiplayer() {
        return true;
    }

    public boolean isOpponentConnected() {
        return bOpponentConnected;
    }

    public void init(GameEnvironmentInterface environment) {
        mainContainer = new BlockContainerData(10,30);
        opponentMainContainer = new BlockContainerData(10,30);
        bEnd = false;
        bCompleted = false;
        bStarted=false;
        bOpponentConnected=false;
        System.out.println("Please wait, connecting to "+hostname+"...");
        connection = NetworkClient.connect(hostname,1123,5,username,password,this);
        connection.write("INIT");
    }
    protected void initNewGame() {
        bEnd = false;
        bStarted=true;
        bCompleted = false;
    }

    public void moveLeft() {
        connection.write("LEFT");
    }

    public void moveRight() {
        connection.write("RIGHT");
    }

    public void rotate() {
        connection.write("ROTATE");
    }

    public void moveDown() {
        connection.write("DOWN");
    }

    public void startGame() {
        connection.write("START");
    }

    public void update() {
        // Do nothing, the model update is done on the server
    }

    public void connected(NetworkConnectionInterface connection) {
        System.out.println("Tetris.connected");
    }

    public void disconnected(NetworkConnectionInterface connection) {
        System.out.println("Tetris.disconnected");
    }

    public void message(NetworkConnectionInterface connection, String message) {
        if(message.charAt(0)=='1') {
            String status = message.substring(327);
            if(status.equals("CONNECTED")) {
                score = message.substring(313,313+6);
                highScore = message.substring(319,319+6);
                level = message.substring(325,325+2);
            }else {
                nextBlock = getBlockFromString(nextBlock,message.substring(7,12));
                mainContainer.fromString(message.substring(13));
                score = message.substring(313,313+6);
                highScore = message.substring(319,319+6);
                level = message.substring(325,325+2);
                //System.out.println("message = " + message);
                if(status.equals("GAMEOVER")) {
                    bEnd = true;
                }else if(status.equals("COMPLETE")) {
                    bEnd = true;
                    bCompleted = true;
                }
            }
        }else if(message.charAt(0)=='2') {
            opponentMainContainer.fromString(message.substring(13));
            opponentScore = message.substring(313,313+6);
            opponentLevel = message.substring(325,325+2);
            String status = message.substring(327);
            if(status.equals("CONNECTED")) {
                bOpponentConnected = true;
            }else if(status.equals("DISCONNECTED")) {
                bOpponentConnected = false;
            }
        }else if(message.equals("STARTED")) {
            initNewGame();
        }else if(message.equals("ENDED") || message.equals("STOPPED")) {
            bStarted = false;
        }
    }
    protected Block getBlockFromString(Block oldBlock, String message) {
        char c = message.charAt(0);
        Block block = oldBlock;
        if(c=='0') {
            return null;
        }else if(c=='1') {
            if(!(oldBlock instanceof Block1)) {
                block = new Block1();
            }
        }else if(c=='2') {
            if(!(oldBlock instanceof Block2)) {
                block = new Block2();
            }
        }else if(c=='3') {
            if(!(oldBlock instanceof Block3)) {
                block = new Block3();
            }
        }else if(c=='4') {
            if(!(oldBlock instanceof Block4)) {
                block = new Block4();
            }
        }else if(c=='5') {
            if(!(oldBlock instanceof Block5)) {
                block = new Block5();
            }
        }else if(c=='6') {
            if(!(oldBlock instanceof Block6)) {
                block = new Block6();
            }
        }else if(c=='7') {
            if(!(oldBlock instanceof Block7)) {
                block = new Block7();
            }
        }
        block.init(Integer.parseInt(message.substring(2,3)),Integer.parseInt(message.substring(2,3)),(c-'0')*90);
        return block;
    }
}
