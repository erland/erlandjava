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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

/**
 * Handles storing of groupdata
 * @author Erland Isaksson
 */
public class ParameterStorageGroupWithId
	extends ParameterStorageStringEx
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ParameterStorageGroupWithId.class);
    /** Prefix for all group attributes */
    private String groupPrefix;

	/**
	 * Creates a new object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param documentName Name of the document
	 */
    public ParameterStorageGroupWithId(StorageInterface primaryStorage, StorageInterface secondaryStorage, String documentName, String groupPrefix)
	{
		super(primaryStorage,secondaryStorage,documentName);
        this.groupPrefix = groupPrefix;
	}

	protected String getSpecialParameterInData(XMLNode data, String name)
	{
        LOG.debug("getSpecialParameter("+name+")");
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
            LOG.debug("getSpecialParameter...");
            return node.getValue();
		}
		return "";
	}

    protected StorageInterface getSpecialParameterAsStorageInData(XMLNode data,String name)
	{
        LOG.debug("getSpecialParameterAsStorage("+name+")");
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
            return new XMLStorage(node);
		}
		return null;
	}

	protected boolean isSpecialHandled(String name)
	{
		if(name.length()>=groupPrefix.length() && name.substring(0,groupPrefix.length()).equalsIgnoreCase(groupPrefix)) {
			return true;
		}else {
			return false;
		}
	}

	protected void setSpecialParameterInData(XMLNode data, String name, String value)
	{
		XMLNode node = findGroupNode(data,name);
        name = getId(name);
		if(node!=null) {
            setValue(node,value);
		}else {
			XMLNode child = addChild(data,groupPrefix,value);
            child.setAttributeValue("id",name);
		}
	}

    protected void setSpecialParameterAsStorageInData(XMLNode data, String name, StorageInterface value) {
        XMLNode node = findGroupNode(data,name);
        name = getId(name);
        if(node!=null) {
            setNodeAsStorage(node,name,value);
        }else {
            data.delChild(node);
            XMLNode child = addChildAsStorage(data,groupPrefix,value);
            child.setAttributeValue("id",name);
        }
    }

	protected void delSpecialParameterInData(XMLNode data, String name)
	{
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
			data.delChild(node);
			return;
		}
	}

	/**
	 * Find a specific group node in the XMLNode tree
     * @param data Main object for the XMLNode tree
	 * @param name The group to find
	 * @return The XMLNode object if the group node was found or null if it was not found
	 */
	protected XMLNode findGroupNode(XMLNode data, String name)
	{
        name = getId(name);
		Iterator it = data.getChilds();
		while(it.hasNext()) {
            XMLNode node = (XMLNode)it.next();
            LOG.debug("findGroupNode node.getName()="+node.getName());
			if(node.getName().equalsIgnoreCase(groupPrefix)) {
                if(node.getAttributeValue("id")!=null && node.getAttributeValue("id").equalsIgnoreCase(name)) {
					return node;
				}
			}
		}
		return null;
	}

    protected String getId(String name) {
        name = name.substring(groupPrefix.length());
        if(name.startsWith("[") && name.endsWith("]")) {
            name = name.substring(1,name.length()-1);
        }else if(name.startsWith(".")) {
            name = name.substring(1);
        }
        return name;
    }
}