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

import java.util.Iterator;

/**
 * Handles storing of groupdata
 * @author Erland Isaksson
 */
public class ParameterStorageGroup
	extends ParameterStorageString
{
    /** Prefix for all group attributes */
    private String groupPrefix;

	/**
	 * Creates a new object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param documentName Name of the document
	 */
    public ParameterStorageGroup(StorageInterface primaryStorage, StorageInterface secondaryStorage, String documentName, String groupPrefix)
	{
		super(primaryStorage,secondaryStorage,documentName);
        this.groupPrefix = groupPrefix;
	}

	protected String getSpecialParameterInData(XMLNode data, String name)
	{
        Log.println(this,"getSpecialParameter("+name+")");
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
            Log.println(this,"getSpecialParameter...");
			Iterator it = node.getChilds();
			while(it.hasNext()) {
                XMLNode nodeval =(XMLNode)it.next();
                //Log.println(this,"getSpecialParameter..."+nodeval.getName()+" "+nodeval.getValue());
				if(nodeval.getName().equalsIgnoreCase("data")) {
					return nodeval.getValue();
				}
			}
		}
		return "";
	}

    protected StorageInterface getSpecialParameterAsStorageInData(XMLNode data,String name)
	{
        Log.println(this,"getSpecialParameterAsStorage("+name+")");
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
			Iterator it = node.getChilds();
			while(it.hasNext()) {
                XMLNode nodeval = (XMLNode)it.next();
                Log.println(this,"getSpecialParameterAsStorage..."+nodeval.getName()+" "+nodeval.getValue());
				if(nodeval.getName().equalsIgnoreCase("data")) {
                    Iterator it2 = nodeval.getChilds();
                    if(it2.hasNext()) {
                        return new XMLStorage((XMLNode)it2.next());
                    }else {
                        return new StringStorage(nodeval.getValue());
                    }
				}
			}
		}
		return null;
	}

	protected boolean isSpecialHandled(String name)
	{
		if(name.substring(0,groupPrefix.length()).equalsIgnoreCase(groupPrefix)) {
			return true;
		}else {
			return false;
		}
	}

	protected void setSpecialParameterInData(XMLNode data, String name, String value)
	{
		XMLNode node = findGroupNode(data,name);
		if(node!=null) {
			Iterator it = node.getChilds();
			while(it.hasNext()) {
                XMLNode groupdatanode = (XMLNode)it.next();
				if(groupdatanode.getName().equalsIgnoreCase("data")) {
					setValue(groupdatanode,value);
					return;
				}
			}
		}else {
			XMLNode child = addChild(data,groupPrefix,null);
			addChild(child,"name",name);
			addChild(child,"data",value);
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
		Iterator it = data.getChilds();
		while(it.hasNext()) {
            XMLNode node = (XMLNode)it.next();
            Log.println(this,"findGroupNode node.getName()="+node.getName());
			if(node.getName().equalsIgnoreCase(groupPrefix)) {
				Iterator it2 = node.getChilds();
				boolean bFound = false;
				while(it2.hasNext()) {
                    XMLNode groupnamenode = (XMLNode)it2.next();
                    Log.println(this,"groupnamenode.getName()="+groupnamenode.getName());
					if(groupnamenode.getName().equalsIgnoreCase("name")) {
                        Log.println(this,"grounamenode.getValue()="+groupnamenode.getValue());
						if(groupnamenode.getValue().equalsIgnoreCase(name)) {
							bFound = true;
						}
					}
				}
				if(bFound) {
					return node;
				}
			}
		}
		return null;
	}
}