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

import erland.game.MapEditorBlockInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

/**
 * A factory class that is used to create BlockMap objects
 */
public class BlockMapFactory {
    /**
     * Create a {@link BlockMap} for the specified level
     * @param environment A reference to the environment
     * @param cont The block container which the {@link BlockMap} should be used in
     * @param level The level to create a {@link BlockMap} for
     * @return The newly created {@link BlockMap} object
     */
    public static BlockMap create(GameEnvironmentInterface environment, BlockContainerInterface cont, int level) {
        BlockMap map = new BlockMap();

        Block[][] blocks = new Block[cont.getSizeX()][cont.getSizeY()];

        LevelFactoryInterface levelFactoryGameIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        LevelManager iconManager = new LevelManager();
        iconManager.init(environment,levelFactoryGameIcon,"mapicons","mapicon");
        iconManager.setContainer(cont);

        LevelFactoryInterface levelFactoryBlocks = new RacerLevelFactoryGroup(iconManager,new BlockClone(),3,3);
        LevelManager blockManager = new LevelManager();
        blockManager.init(environment,levelFactoryBlocks,"mapblocks","mapblock");
        blockManager.setContainer(cont);

        LevelFactoryInterface levelFactoryGroup = new RacerLevelFactoryGroup(blockManager,new BlockClone(),cont.getScrollingSizeX(),cont.getScrollingSizeY());
        LevelManager levelManager = new LevelManager();
        levelManager.init(environment,levelFactoryGroup,"levels","level");
        levelManager.setContainer(cont);
        MapEditorBlockInterface mapBlocks[][] = null;
        LevelInfoInterface levelInfo = levelManager.getLevel(level);
        ExtendedLevelInfo extendedLevelInfo = null;
        System.out.println("levelInfo = "+levelInfo);
        if(levelInfo!=null) {
            mapBlocks = levelInfo.getBlocks();
            if(levelInfo.getExtendedInfo() instanceof ExtendedLevelInfo) {
                extendedLevelInfo = (ExtendedLevelInfo)levelInfo.getExtendedInfo();
            }else {
                extendedLevelInfo = new ExtendedLevelInfo();
                extendedLevelInfo.setStartDirection(0);
                extendedLevelInfo.setStartPosX(0);
                extendedLevelInfo.setStartPosY(0);
            }
        }
        if(mapBlocks!=null) {
            for(int x=0;x<mapBlocks.length;x++) {
                for(int y=0;y<mapBlocks[x].length;y++) {
                    if(x>=0 && x<blocks.length && y>=0 && y<blocks[0].length) {
                        blocks[x][y]=(Block)mapBlocks[x][y];
                    }
                }
            }
        }
        Block[][] mainStartBlocks = null;
        if(extendedLevelInfo!=null) {
            LevelFactoryInterface levelFactoryStartBlock = new RacerLevelFactoryGroup(iconManager,new BlockClone(),3,3);
            LevelManager levelManagerStartBlocks = new LevelManager();
            levelManagerStartBlocks.init(environment,levelFactoryStartBlock,"mapstartblocks","mapstartblock");
            levelManagerStartBlocks.setContainer(cont);
            LevelInfoInterface startBlockInfo = levelManagerStartBlocks.getLevel(extendedLevelInfo.getStartDirection()+1);
            if(startBlockInfo!=null) {
                MapEditorBlockInterface startBlocks[][] = startBlockInfo.getBlocks();
                if(startBlocks != null) {
                    mainStartBlocks = new Block[3][3];
                    for (int x = 0; x < startBlocks.length; x++) {
                        for (int y = 0; y < startBlocks[x].length; y++) {
                            mainStartBlocks[x][y] = (Block)startBlocks[x][y];
                            if(mainStartBlocks[x][y]!=null) {
                                mainStartBlocks[x][y].setPos(extendedLevelInfo.getStartPosX()*3+x,extendedLevelInfo.getStartPosY()*3+y);
                            }
                        }
                    }
                }
            }
        }

        map.setContainer(cont);
        map.setMapData(blocks,mainStartBlocks,extendedLevelInfo);
        return map;
    }
}
