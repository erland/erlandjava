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

import java.util.Map;

/**
 * Defines the methods that an XML parser handler needs to
 * implement to be able to parse an XML document using an
 * XML parser implementing {@link XMLParserInterface}
 * @author Erland Isaksson
 */
public interface XMLParserHandlerInterface {
    /**
     * Called in the beginning of the document before parsing starts
     */
    void startDocument();
    /**
     * Called in the end of the document when parsing has finished
     */
    void endDocument();
    /**
     * Called once at the start of each node in the XML document.
     * The value of the node is sent in {@link #characters(String,int,int)}
     * @param name The name of the node
     * @param attributes The attributes of the node
     */
    void startElement(String name, Map attributes);
    /**
     * Called once at the end of each node in the XML document.
     * @param name The name of the node
     */
    void endElement(String name);
    /**
     * Called one or several times for each values of a node in the
     * XML document. Observe that the whole document may be sent
     * int the chars parameter, the position of the value in the string
     * is specified with the start and length parameters.
     * @param chars The String object containing the value
     * @param start The start position of the value
     * @param length The length of the value
     */
    void characters(String chars, int start, int length);
}
