package erland.game;

/**
 * Defines the methods needed for a highscore object
 * @author Erland Isaksson
 */
public interface HighScoreInterface {
    /**
     * Loads the current highscore from storage
     */
    void load();

    /**
     * Saves the current highscore to storage
     */
    void save();

    /**
     * Updates the highscore with the specified score. If the specified score
     * is higher than the current highscore the current highscore will be updated
     * @param score The score to update the highscore object with
     */
    void update(long score);

    /**
     * Get the current highscore
     * @return The current highscore
     */
    long get();
}
