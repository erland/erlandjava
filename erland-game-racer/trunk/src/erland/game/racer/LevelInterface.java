package erland.game.racer;

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;

/**
 * Defines all methods that a level object must implement
 */
public interface LevelInterface extends ParameterSerializable, LevelInfoInterface {
    /**
     * Initialize the level
     * @param environment The game enviornment
     */
    void init(GameEnvironmentInterface environment);

    /**
     * Set the block container the level exists in
     * @param cont The block container
     */
    void setContainer(BlockContainerInterface cont);

    /**
     * Write the level to specified storage object
     * @param out Storage object
     */
    void write(ParameterValueStorageExInterface out);

    /**
     * Read the level from the specified storage object
     * @param in Storage object
     */
    void read(ParameterValueStorageExInterface in);

    /**
     * Set the blocks in the level
     * @param blocks The blocks
     */
    void setBlocks(MapEditorBlockInterface[][] blocks);

    /**
     * Set the extended information about the level besides the blocks
     * @param extendedInfo Extended info about the level
     */
    void setExtendedInfo(ParameterSerializable extendedInfo);

    /**
     * Get the name of the level
     * @return The name of the level
     */
    String getName();

    /**
     * Set the name of the level
     * @param name The name of the level
     */
    void setName(String name);

}
