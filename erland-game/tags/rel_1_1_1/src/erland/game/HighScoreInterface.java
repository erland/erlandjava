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
