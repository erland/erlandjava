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

import java.util.Vector;

/**
 * An object representing a highscore list
 * @author Erland Isaksson
 */
public class HighScoreList implements HighScoreListInterface {
    /** The storage to load/save the highscore from */
    private ParameterValueStorageInterface storage;
    /** The current highscore list */
    private Vector highScoreList;
    /** Maximum number of entries in the highscore list */
    private int maxEntries;

    /**
     * A object representing an entry in the highscore list
     */
    private class Entry {
        /** The name of this entry */
        private String name;
        /** The score of this entry */
        private long score;
        /**
         * Creates a new instance with the specified name and score
         * @param name The name of the new entry
         * @param score The score of the new entry
         */
        Entry(String name,long score) {
            this.name = name;
            this.score = score;
        }
        /**
         * Get the name of this entry
         * @return The name of this entry
         */
        public String getName() {
            return name;
        }
        /**
         * Get the score of this entry
         * @return The score of this entry
         */
        public long getScore() {
            return score;
        }
    }

    /**
     * Creates a new highscore list object
     * @param storage The storage to load/save the highscore list from
     * @param maxEntries The maximum number of entries in the highscore list
     */
    public HighScoreList(ParameterValueStorageInterface storage,int maxEntries) {
        this.storage = storage;
        highScoreList = new Vector();
        this.maxEntries = maxEntries;
    }

    public void load() {
        for (int i = 0; i < maxEntries; i++) {
            String name = storage.getParameter("highScoreName"+i);
            String scoreStr = storage.getParameter("highScorePoint"+i);
            if(name!=null && name.length()>0) {
                if(scoreStr!=null && scoreStr.length()>0) {
                    long score = Integer.valueOf(scoreStr).longValue();
                    Entry entry = new Entry(name,score);
                    highScoreList.add(entry);
                }
            }
        }
    }

    public void save() {
        for (int i = 0; i < highScoreList.size(); i++) {
            Entry entry = (Entry) highScoreList.elementAt(i);
            storage.setParameter("highScoreName"+i,entry.getName());
            storage.setParameter("highScorePoint"+i,String.valueOf(entry.getScore()));
        }
    }

    public void update(String name,long score) {
        Entry entry = (Entry) highScoreList.lastElement();
        if(entry.score<score || highScoreList.size()<maxEntries) {
            for (int i = 0; i < highScoreList.size(); i++) {
                entry = (Entry) highScoreList.elementAt(i);
                if(entry.getScore()<score) {
                    highScoreList.insertElementAt(new Entry(name,score),i);
                    break;
                }
            }
            while(highScoreList.size()>maxEntries) {
                highScoreList.removeElement(highScoreList.lastElement());
            }
            save();
        }
    }

    public long getScore(int position) {
        Entry entry = (Entry) highScoreList.elementAt(position);
        if(entry!=null) {
            return entry.getScore();
        }else {
            return 0;
        }
    }

    public String getName(int position) {
        Entry entry = (Entry) highScoreList.elementAt(position);
        if(entry!=null) {
            return entry.getName();
        }else {
            return null;
        }
    }
}
