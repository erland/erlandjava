package erland.game;

/**
 * Defines the methods that a highscore list object needs to implement
 * @author Erland Isaksson
 */
public interface HighScoreListInterface {
    /**
     * Load the highscore list from storage
     */
    void load();
    /**
     * Save the highscore list to storage
     */
    void save();
    /**
     * Update the highscore list with the specified name and score. If the
     * score is large enough the highscore list will be updated with the new
     * name
     * @param name The name of the score
     * @param score The score to update the highscore list with
     */
    void update(String name,long score);
    /**
     * Get the score of a specified position in the highscore list
     * @param position The position of the highscore list to get the score for
     * @return The score from the highscore list
     */
    long getScore(int position);
    /**
     * Get the name of a specified position in the highscore list
     * @param position The position of the highscore list to get the name for
     * @return The name from the highscore list
     */
    String getName(int position);
}
