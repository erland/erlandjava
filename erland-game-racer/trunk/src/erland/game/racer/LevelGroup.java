package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.util.*;

/**
 * Implements a block that consists of other sub blocks
 */
public class LevelGroup extends Level {
    /** The level manager that loads the sub blocks */
    protected LevelManager blockManager;
    /**
     * Creates a block
     * @param blockManager Level manager that should be used to load the sub blocks
     * @param blockCloneManager Object that should be used when cloning blocks
     * @param sizeX Horizontal number of blocks in the container
     * @param sizeY Vertical number of blocks in the container
     */
    public LevelGroup(LevelManager blockManager, BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
        this.blockManager = blockManager;
    }

    /**
     * This method is NOT IMPLEMENTED in this class
     * @param out
     */
    public void write(ParameterValueStorageExInterface out)
    {
        return;
    }

    public void readBlocks(ParameterValueStorageExInterface in)
    {
        int i=0;
        boolean bQuit = false;
        while(!bQuit) {
            i++;
            StorageInterface blockStorage = in.getParameterAsStorage("block"+i);
            if(blockStorage!=null) {
                ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,null);
                String cls = oneBlock.getParameter("class");
                MapEditorBlockInterface b = null;
                try {
                    b = (MapEditorBlockInterface)Class.forName(cls).newInstance();
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                } catch (ClassNotFoundException e) {
                }
                if(b instanceof BlockGroup) {
                    b.init(environment);
                    b.setContainer(cont);
                    b.read(oneBlock);
                    int blockType = ((BlockGroup)b).getBlockType();
                    LevelInfoInterface levelInfo = blockManager.getLevel(blockType);
                    MapEditorBlockInterface[][] blocks = null;
                    if(levelInfo!=null) {
                        blocks = levelInfo.getBlocks();
                    }
                    if(blocks!=null) {
                        for(int x1=0;x1<blocks.length;x1++) {
                            for(int y1=0;y1<blocks[x1].length;y1++) {
                                if(blocks[x1][y1]!=null) {
                                    int x = b.getPosX()*blocks.length+x1;
                                    int y = b.getPosY()*blocks[x1].length+y1;
                                    if(x>=0 && x<sizeX && y>=0 && y<sizeY) {
                                        try {
                                            this.blocks[x][y] = (MapEditorBlockInterface)((Block)blocks[x1][y1]).clone();
                                            this.blocks[x][y].init(environment);
                                            this.blocks[x][y].setContainer(cont);
                                            this.blocks[x][y].setPos(x,y);
                                            //Log.println(this,"block at: "+x+","+y+","+((BlockBitmap)blocks[x1][y1]).subImage+","+((BlockBitmap)this.blocks[x][y]).subImage);
                                        } catch (CloneNotSupportedException e) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                bQuit = true;
            }
        }
    }
}
