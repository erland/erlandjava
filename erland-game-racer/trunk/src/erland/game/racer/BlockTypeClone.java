package erland.game.racer;

import erland.game.MapEditorBlockInterface;

public class BlockTypeClone implements BlockCloneInterface {
    protected MapEditorBlockInterface[][] cloneBlocks;
    public BlockTypeClone(MapEditorBlockInterface[][] cloneBlocks)
    {
        this.cloneBlocks = cloneBlocks;
    }
    protected MapEditorBlockInterface findBlock(int blockNo)
    {
        for(int x=0;x<cloneBlocks.length;x++) {
            for(int y=0;y<cloneBlocks[x].length;y++) {
                if(cloneBlocks[x][y]!=null) {
                    if(cloneBlocks[x][y] instanceof BlockGroup) {
                        if(((BlockGroup)cloneBlocks[x][y]).getBlockType()==blockNo) {
                            return cloneBlocks[x][y];
                        }
                    }
                }
            }
        }
        return null;
    }
    public MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block) {
        MapEditorBlockInterface b = null;
        if(block instanceof BlockGroup) {
            MapEditorBlockInterface b1 = findBlock(((BlockGroup)block).getBlockType());
            if(b1 != null) {
                try {
                    b = (MapEditorBlockInterface)((Block)b1).clone();
                } catch (CloneNotSupportedException e) {
                }
            }
        }
        return b;
    }
}
