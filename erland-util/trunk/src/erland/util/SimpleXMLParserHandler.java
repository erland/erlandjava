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

import java.util.Vector;
import java.util.Map;


/**
 * An implementation of a XMLParserHandlerInterface. This will parse
 * the XML document to a full XMLNode tree.
 * @author Erland Isaksson
 */
public class SimpleXMLParserHandler implements XMLParserHandlerInterface {
    /** The currently parsed text value */
    private String text;

    /** The currently parsed XMLNode object of the tree */
    private XMLNode current;

    /** The parent tree of the currently parsed XMLNode object of the tree */
    private Vector parents;

    /** The main XMLNode object of the parsed tree */
    private XMLNode main;

    /**
     * Get the XMLNode tree that has been created during the parsing
     * @return The XMLNode tree
     */
    public XMLNode getData() {
        return main;
    }

    public void startDocument() {
        main = null;
        current = null;
        parents = new Vector();
        text = null;
    }
    public void endDocument() {
        // Do nothing
    }

    public void startElement(String name, Map attributes) {
        XMLNode newElement = new XMLNode(name,attributes);
        if(current == null) {
            if(parents.size()>0) {
                current =(XMLNode)parents.lastElement();
            }
        }else {
            parents.addElement(current);
        }
        if(current != null) {
            current.addChild(newElement);
        }else {
            main = newElement;
        }
        current = newElement;
        text = new String();
    }

    public void endElement(String name) {
        if(current != null && text != null) {
            current.setValue(text.trim());
        }else {
            if(parents.size()>0) {
                XMLNode parent = (XMLNode)parents.lastElement();
                if(parent.getName().equals(name)) {
                    parents.removeElement(parent);
                }
            }
        }
        current = null;
    }

    public void characters(String str, int start, int length) {
        if(current != null && text != null) {
            String value = str.substring(start,start+length);
            text += value;
        }
    }
}
