package erland.game;

import erland.util.ParameterValueStorageInterface;

/**
 * An object representing the highscore
 * @author Erland Isaksson
 */
public class HighScore implements HighScoreInterface {
    /** The storage to load/save highscore to */
    private ParameterValueStorageInterface storage;
    /** The current highscore */
    private long highscore;

    /**
     * Creates a new instance
     * @param storage The storage to load/save highscore from
     */
    public HighScore(ParameterValueStorageInterface storage) {
        this.storage = storage;
    }

    public void load() {
        String scoreStr = storage.getParameter("highscore");
        if(scoreStr!=null && scoreStr.length()>0) {
            highscore = Integer.valueOf(scoreStr).longValue();
        }
    }

    public void save() {
        storage.setParameter("highscore",String.valueOf(highscore));
    }

    public void update(long score) {
        if(highscore<score) {
                highscore = score;
        }
    }
    public long get() {
        return highscore;
    }
}
