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

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;
import erland.util.*;

/**
 * Basic implementation of a level object
 */
public class Level implements LevelInterface {
    /** All blocks in the level */
    protected MapEditorBlockInterface blocks[][];
    /** The name of the level */
    protected String name;
    /** The block container the level exists in */
    protected BlockContainerInterface cont;
    /** The game environment */
    protected GameEnvironmentInterface environment;
    /** The number of horizontal blocks in the level */
    protected int sizeX;
    /** The number of vertical blocks in the level */
    protected int sizeY;
    /** The object that is used to clone blocks */
    protected BlockCloneInterface blockCloneManager;
    /** Extended information about the level */
    protected ParameterSerializable extendedInfo;

    /**
     * Creates a new level object
     * @param blockCloneManager  The object that should be used when cloning blocks
     * @param sizeX The number horizontal blocks in the level
     * @param sizeY The number of vertical blocks in the level
     */
    protected Level(BlockCloneInterface blockCloneManager,int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        clearBlocks();
    }
    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    /**
     * Recreate and clear all blocks in the level
     */
    protected void clearBlocks()
    {
        blocks = new MapEditorBlockInterface[sizeX][sizeY];
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[x].length;y++) {
                blocks[x][y]=null;
            }
        }
    }
    /**
     * Write the extended info of the level to the specified storage
     * @param out Storage object
     */
    protected void writeExtendedInfo(ParameterValueStorageExInterface out)
    {
        out.setParameter("class",extendedInfo.getClass().getName());
        extendedInfo.write(out);
    }

    public void write(ParameterValueStorageExInterface out)
    {
        if(blocks!=null) {
            StringStorage levelStorage = new StringStorage();
            ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage, null,"blockdata","block");
            writeBlocks(allBlocks);
            out.setParameter("data",levelStorage.load());
            if(extendedInfo!=null) {
                StringStorage extendedStorage = new StringStorage();
                ParameterStorageString extended = new ParameterStorageString(extendedStorage,null,"data");
                writeExtendedInfo(extended);
                out.setParameter("extendedinfo",extendedStorage.load());
            }
        }
    }

    /**
     * Write all blocks in the level to the specified storage
     * @param out Storage object
     */
    protected void writeBlocks(ParameterValueStorageExInterface out)
    {
        int i=0;
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[x].length;y++) {
                if(blocks[x][y]!=null) {
                    i++;
                    StringStorage blockStorage = new StringStorage();
                    ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,null);
                    oneBlock.setParameter("class",blocks[x][y].getClass().getName());
                    blocks[x][y].write(oneBlock);
                    out.setParameter("block"+i,blockStorage.load());
                }
            }
        }
    }

    public void read(ParameterValueStorageExInterface in)
    {
        clearBlocks();
        StorageInterface levelStorage = in.getParameterAsStorage("data");
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,null,"blockdata","block");
        readBlocks(allBlocks);
        StorageInterface extendedStorage = in.getParameterAsStorage("extendedinfo");
        if(extendedStorage!=null) {
            ParameterStorageString extended = new ParameterStorageString(extendedStorage,null,"data");
            readExtendedInfo(extended);
        }
    }
    /**
     * Read the extended information about the level from specified storage
     * @param in Storage object
     */
    protected void readExtendedInfo(ParameterValueStorageExInterface in)
    {
        String cls = in.getParameter("class");
        if(cls!=null && cls.length()>0) {
            extendedInfo = null;
            try {
                extendedInfo = (ParameterSerializable)Class.forName(cls).newInstance();
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            }
            extendedInfo.read(in);
        }
    }

    /**
     * Read all the blocks in the level from the specified storage
     * @param in Storage object
     */
    protected void readBlocks(ParameterValueStorageExInterface in)
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
                b.init(environment);
                b.setContainer(cont);
                b.read(oneBlock);
                int x = b.getPosX();
                int y = b.getPosY();
                if(x>=0 && x<sizeX && y>=0 && y<sizeY) {
                    blocks[x][y] = prepareNewBlock(blocks[x][y],b);
                }
            }else {
                bQuit = true;
            }
        }
    }

    /**
     * Prepare new block that should be inserted instead of an old block
     * @param oldBlock Old block
     * @param newBlock New block that should be prepared
     * @return The prepared new blocks that should be inserted instead of the old block, observe that this might be
     *         a completely different object than the block specified in the newBlock parameter
     */
    protected MapEditorBlockInterface prepareNewBlock(MapEditorBlockInterface oldBlock,MapEditorBlockInterface newBlock)
    {
        return newBlock;
    }

    /**
     * Clone all blocks in the specified matrix
     * @param blocks Matrix with blocks to clone
     * @return A cloned copy of the original blocks
     */
    protected MapEditorBlockInterface[][] cloneBlocks(MapEditorBlockInterface[][] blocks)
    {
        MapEditorBlockInterface[][] clonedBlocks = new MapEditorBlockInterface[blocks.length][blocks[0].length];
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[0].length;y++) {
                if(blocks[x][y]!=null) {
                    clonedBlocks[x][y] = blockCloneManager.cloneBlock(blocks[x][y]);
                    if(clonedBlocks[x][y]!=null) {
                        clonedBlocks[x][y].init(environment);
                        clonedBlocks[x][y].setContainer(cont);
                        clonedBlocks[x][y].setPos(x,y);
                    }
                }else {
                    clonedBlocks[x][y] = null;
                }
            }
        }
        return clonedBlocks;
    }

    public MapEditorBlockInterface[][] getBlocks()
    {
        return cloneBlocks(blocks);
    }

    public void setBlocks(MapEditorBlockInterface[][] blocks)
    {
        this.blocks = cloneBlocks(blocks);
    }

    public String getName()
    {
        return name;
    }

    public void setExtendedInfo(ParameterSerializable extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    public ParameterSerializable getExtendedInfo() {
        return extendedInfo;
    }
}
