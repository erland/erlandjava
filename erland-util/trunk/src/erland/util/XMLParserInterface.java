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

import java.io.InputStream;

/**
 * Defines methods that an XML parser needs to implement
 * @author Erland Isaksson
 */
public interface XMLParserInterface {
    /**
     * Parse an XML document in an InputStream using the specified
     * XML parser handler implementation
     * @param in The InputStream with the XML document
     * @param handler The XML parser handler implementation to use when parsing
     * @return true/false (success/failure)
     */
    boolean parse(InputStream in, XMLParserHandlerInterface handler);

    /**
     * Parse an XML document in String using the specified
     * XML parser handler implementation
     * @param str The String object with the XML document
     * @param handler The XML parser handler implementation to use when parsing
     * @return true/false (success/failure)
     */
    boolean parse(String str, XMLParserHandlerInterface handler);
}
