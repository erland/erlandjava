package erland.game.racer;

import erland.game.MapEditorBlockInterface;

/**
 * Interface that defines block clone
 * @author Erland Isaksson
 */
public interface BlockCloneInterface {
    /**
     * Make a clone of the specified block
     * @param block Block to make a clone of
     * @return The clone of the original block
     */
    public MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block);
}
