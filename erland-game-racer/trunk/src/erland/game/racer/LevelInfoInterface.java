package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.util.ParameterSerializable;

/**
 * Defines all methods that a level information object needs to implement
 */
public interface LevelInfoInterface {
    /**
     * Get all blocks in the level
     * @return The blocks in the level
     */
    public MapEditorBlockInterface[][] getBlocks();
    /**
     * Get extended information about the level
     * @return Extended information about the level
     */
    public ParameterSerializable getExtendedInfo();
}
