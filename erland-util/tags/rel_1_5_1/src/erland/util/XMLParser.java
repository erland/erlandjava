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

/**
 * This is a singleton object that makes it possible to set
 * and get the currently active XML parser
 * @author Erland Isaksson
 */
public class XMLParser {
    /** The currently active XML parser object */
    private static XMLParserInterface me;

    /**
     * Change the currently active XML parser
     * @param parser The new XML parser to user
     */
    public static void setInstance(XMLParserInterface parser) {
        me = parser;
    }
    /**
     * Get the currently active XML parser, if no parser has been
     * set previously it will be created automatically when this
     * method is called
     * @return The currently active XML parser
     */
    public static XMLParserInterface getInstance() {
        if(me==null) {
            me = new SimpleXMLParser();
        }
        return me;
    }
}
