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
 * A storage object that stores its data as XML in memory
 * @author Erland Isaksson
 */
public class XMLStorage implements StorageInterface {
    /** XMLNode that contains the stored data */
    private XMLNode node;

    /** XMLParser implementation */
    private SimpleXMLParserHandler parser = new SimpleXMLParserHandler();

    /**
     * Creates a new object based on the data in an XMLNode object
     * @param node The XMLNode object containing the data
     */
    public XMLStorage(XMLNode node)
    {
        this.node = node;
    }

    /**
     * Get the storage data as an XMLNode object
     * @return The XMLNode representing the stored data
     */
    public XMLNode getXMLNode()
    {
        return node;
    }

    public void save(String str) {
        if(XMLParser.getInstance().parse(str,parser)) {
            node = parser.getData();
        }else {
            node = null;
        }
    }

    public String load() {
        return node.toString(false);
    }

}
