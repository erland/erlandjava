package erland.game.tetris;

import erland.network.NetworkConnectionInterface;
import erland.game.GameServerEnvironmentInterface;
import erland.game.GamePlayerInterface;

import java.awt.*;

public class TetrisPlayer implements GamePlayerInterface {
    private NetworkConnectionInterface connection;
    /** Current active block in the main game area */
    protected Block block;
    /** Current active block in the preview block area */
    protected Block blockNext;
    /** Counter that is increased every update, when it reaches the current game
        speed the active block will be moved down one step */
    protected int updateCounter;
    /** Number of levels */
    protected final int MAX_LEVEL=9;
    /** Maximum block speed */
    protected final int MAX_SPEED=20;
    /** Current score */
    protected long score;
    /** Current level */
    protected int level;
    /** Number of rows that has been removed in this level */
    protected int rowsThisLevel;
    /** Container representing the main game area */
    protected BlockContainerData mainContainer;
    /** Number of rows that has to be removed before level is increased */
    protected final int ROWS_PER_LEVEL=10;
    /** true if game is completed or game over has occurred */
    protected boolean bEnd=false;
    /** Indicates if the player has updated data */
    private boolean bUpdated;
    /** Game environment */
    protected GameServerEnvironmentInterface environment;

    public TetrisPlayer(GameServerEnvironmentInterface environment) {
        this.environment = environment;
        mainContainer = new BlockContainerData(10,30);
        init();
    }

    public void setConnection(NetworkConnectionInterface connection) {
        this.connection = connection;
    }

    public NetworkConnectionInterface getConnection() {
        return connection;
    }

    public void doCommand(String command) {
        if(command.equals("RIGHT")) {
            moveRight();
        }else if(command.equals("LEFT")) {
            moveLeft();
        }else if(command.equals("ROTATE")) {
            rotateRight();
        }else if(command.equals("DOWN")) {
            moveDownBottom();
        }
        bUpdated = true;
    }

    public void init() {
        mainContainer.clear();
    }

    public void startNewGame() {
        bEnd = false;
        level = 1;
        score = 0;
        rowsThisLevel=0;
        updateCounter=0;
        mainContainer.clear();
        block=null;
        blockNext=null;
    }
    public boolean isGameOver() {
        return bEnd;
    }
    public boolean isCompleted() {
        return (bEnd && level>MAX_LEVEL);
    }
    public boolean update() {
        updateCounter++;
        if(updateCounter>(MAX_SPEED-level*2)) {
            updateCounter=0;
            if(!bEnd) {
                if(this.block==null) {
                    if(this.blockNext==null) {
                        blockNext = BlockFactory.getInstance().nextBlock(this);
                    }
                    block = blockNext;
                    blockNext = BlockFactory.getInstance().nextBlock(this);
                    blockNext.init(1,0,0);
                    block.init(mainContainer.getWidth()/2,-1,0);
                    if(!block.moveDown(mainContainer)) {
                        removeCompletedRows();
                        environment.getHighScore().save();
                        bEnd = true;
                    }
                }else {
                    if(!block.moveDown(mainContainer)) {
                        removeCompletedRows();
                        block = null;
                    }
                }
                if(rowsThisLevel>=ROWS_PER_LEVEL) {
                    rowsThisLevel-=ROWS_PER_LEVEL;
                    score+=200*level;
                    environment.getHighScore().update(score);
                    level++;
                }
                if(level>MAX_LEVEL) {
                    bEnd=true;
                    environment.getHighScore().save();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Removes all completed rows in the main game area and increases score
     */
    protected void removeCompletedRows()
    {
        for(int y=mainContainer.getHeight()-1;y>=0;y--) {
            boolean bCompleted = true;
            boolean bCompletedWithSameColor=true;
            Color c=mainContainer.getColor(0,y);
            for(int x=0;x<mainContainer.getWidth();x++) {
                if(!mainContainer.isUsed(x,y)) {
                    bCompleted = false;
                }else {
                    if(c==null || !c.equals(mainContainer.getColor(x,y))) {
                        bCompletedWithSameColor=false;
                    }
                }
            }
            if(bCompleted) {
                for(int i=y;i>0;i--) {
                    for(int j=0;j<mainContainer.getWidth();j++) {
                        if(mainContainer.isUsed(j,i-1)) {
                            mainContainer.setUsed(j,i,mainContainer.getColor(j,i-1));
                        }else {
                            mainContainer.setUnused(j,i);
                        }
                    }
                }
                mainContainer.clearRow(0);
                y++;
                if(!bCompletedWithSameColor) {
                    score+=(10*level);
                }else {
                    score+=(100*level);
                }
                environment.getHighScore().update(score);
                rowsThisLevel++;
            }
        }
    }


    public String getBlockString(Block b) {
        StringBuffer sb = new StringBuffer(2);
        Color c = null;
        if(b!=null) {
            c = b.getColor();
            if(c==Color.red) {
                sb.append('1');
            }else if(c==Color.green) {
                sb.append('2');
            }else if(c==Color.yellow) {
                sb.append('3');
            }else if(c==Color.cyan) {
                sb.append('4');
            }else if(c==Color.blue) {
                sb.append('5');
            }else if(c==Color.pink) {
                sb.append('6');
            }else if(c==Color.magenta) {
                sb.append('7');
            }else {
                sb.append('7');
            }

            int r = b.getRotation();
            r=r/90;
            sb.append(r);

            if(b.getX()<10) {
                sb.append(('0'));
            }
            if(b.getX()<0) {
                sb.append('0');
            }else {
                sb.append(b.getX());
            }
            if(b.getY()<10) {
                sb.append(('0'));
            }
            if(b.getY()<0) {
                sb.append(('0'));
            }else {
                sb.append(b.getY());
            }
        }else {
            sb.append("000000");
        }
        return sb.toString();
    }
    public String getNumberString(int length, long number) {
        StringBuffer sb = new StringBuffer(length);
        int maxNumber=1;
        for(int i=1;i<length;i++) {
            maxNumber*=10;
            if(number<maxNumber) {
                sb.append('0');
            }
        }
        sb.append(number);
        return sb.toString();
    }
    public String toString() {
        bUpdated = false;
        return getBlockString(block)+getBlockString(blockNext)+mainContainer.toString()+getNumberString(6,score)+getNumberString(6,environment.getHighScore().get())+getNumberString(2,level);
        //System.out.println("s = " + s);
        //connection.write(s);
    }
    /**
     * Move the active block in the main game area one step left
     * @return true/false (Success/Failure)
     */
    public boolean moveLeft()
    {
        if(block != null) {
            return block.moveLeft(mainContainer);
        }
        return false;
    }

    /**
     * Move the active block in the main game area one step right
     * @return true/false (Success/Failure)
     */
    public boolean moveRight()
    {
        if(block != null) {
            return block.moveRight(mainContainer);
        }
        return false;
    }

    /**
     * Rotate the active block in the main game area 90 degrees clockwize
     * @return true/false (Success/Failure)
     */
    public boolean rotateRight()
    {
        if(block != null) {
            return block.rotateRight(mainContainer);
        }
        return false;
    }

    /**
     * Move the active block in the main game area one step down
     * @return true/false (Success/Failure)
     */
    public boolean moveDown()
    {
        if(block != null) {
            return block.moveDown(mainContainer);
        }
        return false;
    }

    public boolean moveDownBottom()
    {
        while(moveDown()) {
            ;
        }
        return false;
    }

    public boolean needUpdate() {
        return bUpdated;
    }

    public BlockContainerData getMainContainer() {
        return mainContainer;
    }

    public Block getNextBlock() {
        return blockNext;
    }

    public long getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }
}
