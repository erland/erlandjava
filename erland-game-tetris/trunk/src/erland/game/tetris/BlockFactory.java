package erland.game.tetris;

import java.util.Vector;

public class BlockFactory {
    private static BlockFactory me = null;
    private Object[] players;
    private int[] playerBlockIndex;
    private Vector blocks;

    public static BlockFactory getInstance() {
        if(me==null) {
            me = new BlockFactory();
        }
        return me;
    }

    public BlockFactory() {
        reset();
    }

    private int getPlayerIndex(Object player) {
        for(int i=0;i<players.length;i++) {
            if(player == players[i]) {
                return i;
            }
        }
        return addPlayer(player);
    }
    private int addPlayer(Object player) {
        System.out.println("BlockFactory.addPlayer");
        for(int i=0;i<players.length;i++) {
            if(players[i] == null) {
                players[i] = player;
                playerBlockIndex[i]=0;
                return i;
            }
        }
        return -1;
    }

    /**
     * Creates a new random block
     * @return A new Block object
     */
    protected Block newBlock()
    {
        Block block;
        switch((int)(Math.random()*7+1.0)) {
            case 1:
                block = new Block1();
                break;
            case 2:
                block = new Block2();
                break;
            case 3:
                block = new Block3();
                break;
            case 4:
                block = new Block4();
                break;
            case 5:
                block = new Block5();
                break;
            case 6:
                block = new Block6();
                break;
            case 7:
            default:
                block = new Block7();
                break;
        }
        return block;
    }

    public Block nextBlock(Object player) {
        int i = getPlayerIndex(player);
        if(i>=0) {
            if(playerBlockIndex[i]>=blocks.size()) {
                blocks.addElement(newBlock());
            }
            Block b = (Block) ((Block) blocks.elementAt(playerBlockIndex[i])).clone();
            playerBlockIndex[i]++;
            //System.out.println("player = " + i + ", block = " + (playerBlockIndex[i]-1) + ", block = " +b);
            return b;
        }else {
            return null;
        }
    }
    public void reset() {
        System.out.println("BlockFactory.reset");
        players = new Object[2];
        playerBlockIndex = new int[2];
        blocks = new Vector();
    }
}
