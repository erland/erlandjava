package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;
import erland.util.*;

public class LevelGroup extends Level {
    protected LevelManager blockManager;
    public LevelGroup(LevelManager blockManager, BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
        this.blockManager = blockManager;
    }

    public void write(ParameterValueStorageInterface out)
    {
        return;
        /*
        if(blocks!=null) {
            StringStorage levelStorage = new StringStorage();
            ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block",true);
            int i=0;
            for(int x=0;x<blocks.length;x++) {
                for(int y=0;y<blocks[x].length;y++) {
                    if(blocks[x][y]!=null) {
                        i++;
                        StringStorage blockStorage = new StringStorage();
                        ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,true);
                        oneBlock.setParameter("class",blocks[x][y].getClass().getName());
                        blocks[x][y].write(oneBlock);
                        allBlocks.setParameter("block"+i,blockStorage.load());
                    }
                }
            }
            out.setParameter("data",levelStorage.load());
        }
        */
    }

    public void read(ParameterValueStorageInterface in)
    {
        clearBlocks();
        StringStorage levelStorage = new StringStorage(in.getParameter("data"));
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block");
        int i=0;
        boolean bQuit = false;
        while(!bQuit) {
            i++;
            String s = allBlocks.getParameter("block"+i);
            if(s!=null && s.length()>0) {
                StringStorage blockStorage = new StringStorage(s);
                ParameterStorageString oneBlock = new ParameterStorageString(blockStorage);
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
                    MapEditorBlockInterface[][] blocks = blockManager.getLevel(blockType);
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
