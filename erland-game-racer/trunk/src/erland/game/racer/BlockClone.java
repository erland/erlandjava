package erland.game.racer;

import erland.game.MapEditorBlockInterface;

/**
 * A class that makes clone of a block by calling the clone method on the block
 * @author Erland Isaksson
 */
public class BlockClone implements BlockCloneInterface {
    public MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block) {
        MapEditorBlockInterface b = null;
        if(block!=null) {
            try {
                b = (MapEditorBlockInterface)((Block)block).clone();
            } catch (CloneNotSupportedException e) {
            }
        }
        return b;
    }
}
