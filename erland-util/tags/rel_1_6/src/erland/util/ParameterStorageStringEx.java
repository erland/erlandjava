package erland.util;
/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

import java.util.Iterator;


/**
 * Get, set or delete parameters stored in a string
 * @author Erland Isaksson
 */
public class ParameterStorageStringEx
	extends ParameterStorageString
{
    /**
     * Createa a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     */
	public ParameterStorageStringEx(StorageInterface primaryStorage, StorageInterface secondaryStorage)
	{
        super(primaryStorage,secondaryStorage);
	}

    /**
     * Creates a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param documentName The name of the section in the storage where parameters
     *                     are stored
     */
	public ParameterStorageStringEx(StorageInterface primaryStorage, StorageInterface secondaryStorage, String documentName)
	{
		super(primaryStorage, secondaryStorage,documentName);
	}

    /**
     * Overridden so it returns the node itself
     * @param node The node that matched the parameter name
     * @param name The parameter name that was searched for
     * @return The storage for the node
     */
    protected StorageInterface getNodeAsStorage(XMLNode node, String name)
    {
        return new XMLStorage(node);
    }
    /**
     * Overridden so it sets the node itself
     * @param node The node that matched the parameter name
     * @param name The parameter name that was searched for
     * @param value The value to set
     */
    protected void setNodeAsStorage(XMLNode node, String name, StorageInterface value) {
        if(value instanceof XMLStorage) {
            XMLNode valueNode = ((XMLStorage)value).getXMLNode();
            if(valueNode!=null) {
                node.setName(name);
                node.delChilds();
                Iterator itAttr = valueNode.getAttributes();
                while (itAttr.hasNext()) {
                    String attr = (String) itAttr.next();
                    node.setAttributeValue(attr,valueNode.getAttributeValue(attr));
                }
                Iterator childs = valueNode.getChilds();
                while (childs.hasNext()) {
                    XMLNode child = (XMLNode) childs.next();
                    node.addChild(child);
                }
            }
        }
    }
}
