package erland.game;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
