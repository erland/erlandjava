package erland.game.racer;

import erland.game.MapEditorBlockInterface;

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
