package erland.game;

import erland.util.ParameterSerializable;

/**
 * This interface must be implemented by all block objects which should
 * be used together with the {@link MapEditor} class
 * @author Erland Isaksson
 */
public interface MapEditorBlockInterface extends ParameterSerializable {
    /**
     * Initialize block
     * @param environment The game environment the block exists in
     */
    public void init(GameEnvironmentInterface environment);
    /**
     * Set the container that the block exists in
     * @param cont The container that the block exists in
     */
    public void setContainer(BlockContainerInterface cont);
    /**
     * Get block X position
     * @return X position of the block
     */
    public int getPosX();
    /**
     * Get block Y position
     * @return Y position of the block
     */
    public int getPosY();
    /**
     * Set block position
     * @param x The X position of the block
     * @param y The Y position of the block
     */
    public void setPos(int x, int y);
}
