package erland.util;
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

import erland.util.StorageInterface;

/**
 * Implements a storage that stores the data in a String in memory
 * @author Erland Isaksson
 */
public class StringStorage implements StorageInterface {
    /** String object that contains the data */
    private String str;

    /**
     * Creates a new empty storage
     */
    public StringStorage()
    {
        this.str = "";
    }
    /**
     * Creates a new storage based on a String
     * @param str String with data to store in the newly created storage
     */
    public StringStorage(String str)
    {
        if(str!=null) {
            this.str = str;
        }else {
            this.str = "";
        }
    }
    public void save(String str) {
        this.str = str;
    }

    public String load() {
        return str;
    }
}
