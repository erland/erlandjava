package erland.game.racer;

/**
 * Defines the interface that a level factory needs to implement
 */
public interface LevelFactoryInterface {
    /**
     * Creates a new level
     * @return The newly created level
     */
    public LevelInterface createLevel();
}
