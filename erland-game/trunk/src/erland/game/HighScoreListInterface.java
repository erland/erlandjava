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
