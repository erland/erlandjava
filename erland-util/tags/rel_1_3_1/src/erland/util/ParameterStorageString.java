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
 * Get, set or delete parameters stored in a file
 * @author Erland Isaksson
 */
public class ParameterStorageString
	implements ParameterValueStorageExInterface
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ParameterStorageString.class);
	/**
	 * The section in the file where the parameters are stored
	 */
	private String documentName;

    /** The defualt document name to use if none is specified */
    private static final String DEFAULT_DOCUMENT_NAME = "parameters";

	/**
	 * The in-memory representation of the file where the parameters
	 * are stored
	 */
    private XMLNode primaryData;
    private XMLNode secondaryData;

	/** Storage which parameters are accessed in */
	private StorageInterface primaryStorage;

    /** Indicates that this ParameterStorageString is just a part of a bigger ParameterValueStorageInterface object */
    private boolean bLogging;

    private SimpleXMLParserHandler parser = new SimpleXMLParserHandler();
    /**
     * Createa a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     */
	public ParameterStorageString(StorageInterface primaryStorage, StorageInterface secondaryStorage)
	{
		init(primaryStorage,secondaryStorage,DEFAULT_DOCUMENT_NAME);
	}

    /**
     * Creates a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param documentName The name of the section in the storage where parameters
     *                     are stored
     */
	public ParameterStorageString(StorageInterface primaryStorage, StorageInterface secondaryStorage, String documentName)
	{
		init(primaryStorage, secondaryStorage,documentName);
	}

	/**
	 * Initialize this object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param documentName The name of the section in the file where parameters
     *                     are stored
	 */
	protected void init(StorageInterface primaryStorage, StorageInterface secondaryStorage, String documentName)
	{
        bLogging = LOG.isDebugEnabled();
		this.documentName = documentName;
        this.primaryStorage = primaryStorage;
        if(primaryStorage!=null) {
            if(bLogging) LOG.debug("PrimaryStorage = "+primaryStorage);
            this.primaryData = getData(primaryStorage);
            if(primaryData!=null) {
                if(bLogging) LOG.debug("PrimaryData = "+primaryData.getClass().getName()+"@"+Integer.toHexString(primaryData.hashCode()));
            }else {
                if(bLogging) LOG.debug("PrimaryData = null");
            }
        }
        if(secondaryStorage!=null && secondaryStorage!=primaryStorage) {
            if(bLogging) LOG.debug("SecondaryStorage = "+secondaryStorage);
            this.secondaryData = getData(secondaryStorage);
            if(secondaryData!=null) {
                if(bLogging) LOG.debug("SecondaryData = "+secondaryData.getClass().getName()+"@"+Integer.toHexString(secondaryData.hashCode()));
            }else {
                if(bLogging) LOG.debug("SecondaryData = null");
            }
        }
	}

    /**
     * Get a XMLNode object for a storage
     * @param storage The storage object to get XMLNode object for
     * @return The XMLNode object representing the storage object
     */
    private XMLNode getData(StorageInterface storage) {
        XMLNode data = null;
        if(storage instanceof XMLStorage) {
            data = ((XMLStorage)storage).getXMLNode();
        }else {
            String s = storage.load();
            if(s!=null && s.length()>0) {
                if(documentName==null) {
                    s = "<"+DEFAULT_DOCUMENT_NAME+">"+s+"</"+DEFAULT_DOCUMENT_NAME+">";
                }
                if(XMLParser.getInstance().parse(s,parser)) {
                    data = parser.getData();
                    if(documentName==null) {
                        data.setName(null);
                    }
                }else {
                    if(bLogging) LOG.debug("getData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode()));
                    //LOG.debug("*******************");
                    //LOG.debug(data);
                    //LOG.debug("*******************");
                }
            }
        }
        return data;
    }


	/**
	 * Check if this parameter should be special handled, will be autmatically called
	 * when a parameter is accessed. Implement this in your sub class if you want to handle some
	 * parameters in a special way.
	 * @param name The parameter name
	 * @return Always false
	 *         <pre>true - This parameter should be special handled,then one of 
	 *         {@link #getSpecialParameterInData(erland.util.XMLNode,String)}, {@link #setSpecialParameterInData(erland.util.XMLNode,String,String)}, {@link #delSpecialParameterInData(erland.util.XMLNode,String)},
	 *         will be called to access the parameter</pre>
	 *         <pre>false - This parameter is handled in the normal way, one of
	 *         {@link #getParameter(String)}, {@link #setParameter(String,String)}, {@link #delParameter(String)}, 
	 *         will be called to access the parameter</pre>
	 */
	protected boolean isSpecialHandled(String name)
	{
		return false;
	}

    /**
     * Gets the parameter value for a parameter from the XMLNode tree specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The XMLNode tree to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The value of the parameter
     */
    protected String getSpecialParameterInData(XMLNode data, String name) {
        return "";
        // Not needed, implemented in sub classes if required
    }

    /**
     * Gets a storage object for a parameter from the XMLNode tree specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The XMLNode tree to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The storage object for the parameter
     */
    protected StorageInterface getSpecialParameterAsStorageInData(XMLNode data, String name) {
        return null;
        // Not needed, implemented in sub classes if required
    }

	public String getParameter(String name)
	{
        String value = getParameterInData(primaryData, name);
        if(value==null || value.length()==0) {
            value = getParameterInData(secondaryData,name);
        }
        if(value==null) {
            return "";
        }else {
            return value;
        }
	}

    /**
     * Gets the parameter value for a parameter from the XMLNode tree specified
     * @param data The XMLNode tree to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The value of the parameter
     */
    private String getParameterInData(XMLNode data, String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("getParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            String mainname = data.getName();
            if(documentName==null || (mainname != null && mainname.equalsIgnoreCase(documentName))) {
                if(isSpecialHandled(name)) {
                    if(bLogging) LOG.debug("getSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                    return getSpecialParameterInData(data,name);
                }else {
                    Iterator it = data.getChilds();
                    while(it.hasNext()) {
                        XMLNode node = (XMLNode)it.next();
                        if(node.getName().equalsIgnoreCase(name)) {
                            //LOG.debug("get: " +name + " = "+ node.getValue());
                            return node.getValue();
                        }

                    }
                }
            }
        }
        return "";

    }


    /**
     * Sets the parameter value for a parameter in the XMLNode tree specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    protected void setSpecialParameterInData(XMLNode data,String name, String value) {
        // Not needed, implemented in sub classes if required
    }

    /**
     * Sets the parameter value for a parameter in the XMLNode tree specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * The value is specified as a StorageInterface object
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    protected void setSpecialParameterAsStorageInData(XMLNode data,String name, StorageInterface value) {
        // Not needed, implemented in sub classes if required
    }

	public void setParameter(String name, String value)
	{
        primaryData = setParameterInData(primaryData,name,value);
        secondaryData = setParameterInData(secondaryData,name,value);
        save();
	}

    /**
     * Sets the parameter value for a parameter in the XMLNode tree specified
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    private XMLNode setParameterInData(XMLNode data,String name,String value) {
        if(data==null) {
            data = new XMLNode(documentName,null);
        }
        if(bLogging) LOG.debug("setParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
        String mainname = data.getName();
        if(documentName==null|| (mainname!=null && mainname.equalsIgnoreCase(documentName))) {
            if(isSpecialHandled(name)) {
                if(bLogging) LOG.debug("setSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name+"="+value+" "+this.getClass());
                setSpecialParameterInData(data,name,value);
            }else {
                Iterator it = data.getChilds();
                while(it.hasNext()) {
                    XMLNode node = (XMLNode)it.next();
                    if(node.getName().equalsIgnoreCase(name)) {
                        setValue(node,value);
                        return data;
                    }
                }
                addChild(data,name,value);
            }
        }
        return data;
    }

    /**
     * Add a child XMLNode object to the specified parent, the value of the
     * child object will be parsed to a child tree if it contains XML
     * @param parent The parent XMLNode object
     * @param name The name of the child object
     * @param value The value of the child object
     * @return The added child XMLNode object
     */
    protected XMLNode addChild(XMLNode parent, String name, String value) {
        XMLNode child = new XMLNode(name,null);
        setValue(child,value);
        parent.addChild(child);
        return child;
    }

    /**
     * Add a child XMLNode object to the specified parent, the value of the
     * child object is a StorageInterface object and will be parsed to a
     * child tree if it contains XML
     * @param parent The parent XMLNode object
     * @param name The name of the child object
     * @param value The value of the child object
     * @return The added child XMLNode object
     */
    protected XMLNode addChildAsStorage(XMLNode parent, String name, StorageInterface value) {
        XMLNode child = new XMLNode(name,null);
        setNodeAsStorage(child,name,value);
        parent.addChild(child);
        return child;
    }
    /**
     * Sets the value of a XMLNode object, the value will be parsed to a
     * child tree if it contains XML
     * @param node The XMLNode object to set value on
     * @param value The value to set
     */
    protected void setValue(XMLNode node, String value) {
        if(XMLParser.getInstance().parse(value,parser)) {
            if(bLogging) LOG.debug("ParameterStorageString::setValue2 name="+node.getName()+"="+value);
            node.setValue(null);
            node.delChilds();
            node.addChild(parser.getData());
        }else if(XMLParser.getInstance().parse("<data>"+value+"</data>",parser) &&
                parser.getData()!=null && parser.getData().getChilds().hasNext()) {
            if(bLogging) LOG.debug("ParameterStorageString::setValue4 name="+node.getName()+"="+value);
            node.setValue(null);
            node.delChilds();
            Iterator childs = parser.getData().getChilds();
            while (childs.hasNext()) {
                XMLNode child = (XMLNode) childs.next();
                node.addChild(child);
            }
            if(bLogging) LOG.debug("ParameterStorageString::setValue5 name="+node.getName()+"="+node.getValue());
        }else{
            if(bLogging) LOG.debug("ParameterStorageString::setValue3 name="+node.getName()+"="+value);
            node.delChilds();
            node.setValue(value);
        }
    }

    /**
     * Delets a parameter in the XMLNode tree specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     */
    protected void delSpecialParameterInData(XMLNode data, String name) {
        // Not needed, implemented in sub classes if required
    }

	public void delParameter(String name)
	{
        delParameterInData(primaryData,name);
        delParameterInData(secondaryData,name);
        save();
	}
    /**
     * Delets a parameter in the XMLNode tree specified
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     */
    private void delParameterInData(XMLNode data,String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("delParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            String mainname = data.getName();
            if(documentName==null || (mainname!=null && mainname.equalsIgnoreCase(documentName))) {
                if(isSpecialHandled(name)) {
                    if(bLogging) LOG.debug("delSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                    delSpecialParameterInData(data,name);
                    return;
                }else {
                    Iterator it = data.getChilds();
                    while(it.hasNext()) {
                        XMLNode node = (XMLNode)it.next();
                        if(node.getName().equalsIgnoreCase(name)) {
                            data.delChild(node);
                            return;
                        }
                    }
                }
            }
        }
    }

	/**
	 * Save the parameters in {@link #primaryData data} to a storage.
	 * This will be automatically called in all the parameter access methods
	 */
	private void save()
	{
		primaryStorage.save(primaryData.toString(true));
	}

    public String toString()
    {
        if(primaryData!=null) {
            return primaryData.toString();
        }else {
            return secondaryData.toString();
        }
    }

    public StorageInterface getParameterAsStorage(String name) {
        String value = getParameterInData(primaryData, name);
        StorageInterface storage=null;
        if(value!=null && value.length()>0) {
            storage = getParameterAsStorageInData(primaryData,name);
        }else {
            storage = getParameterAsStorageInData(secondaryData,name);
        }
        if(storage!=null) {
            if(bLogging) LOG.debug("getParameterAsStorage "+name+" -> "+storage.getClass().getName()+"@"+Integer.toHexString(storage.hashCode()));
        }else {
            if(bLogging) LOG.debug("getParameterAsStorage "+name+" -> null");
        }
        return storage;
    }

    /**
     * Gets a storage object for a parameter from the XMLNode tree specified
     * @param data The XMLNode tree to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The storage object for the parameter
     */
    private StorageInterface getParameterAsStorageInData(XMLNode data, String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("getParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
			String mainname = data.getName();
			if(documentName==null || (mainname != null && mainname.equalsIgnoreCase(documentName))) {
				if(isSpecialHandled(name)) {
                    if(bLogging) LOG.debug("getSpecialParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
					return getSpecialParameterAsStorageInData(data,name);
				}else {
					Iterator it = data.getChilds();
					while(it.hasNext()) {
                        XMLNode node = (XMLNode)it.next();
						if(node.getName().equalsIgnoreCase(name)) {
                            return getNodeAsStorage(node,name);
						}
					}
				}
			}
		}
        return null;
    }

    /**
     * Return the specified node as a storage. This method can be overridden
     * by sub classes in case you want to control which node that is returned
     * or which type of storage that is returned
     * @param node The node that matched the parameter name
     * @param name The parameter name that was searched for
     * @return The storage for the node
     */
    protected StorageInterface getNodeAsStorage(XMLNode node, String name)
    {
        //LOG.debug("get: " +name + " = "+ node.getValue());
        Iterator it2 = node.getChilds();
        if(it2.hasNext()) {
            return new XMLStorage((XMLNode)it2.next());
        }else {
            return new StringStorage(node.getValue());
        }
    }

    /**
     * Set the specified node as a storage. This method can be overridden
     * by sub classes in case you want to control which node that is modified
     * or which type of storage that is set/allowed
     * @param node The node that matched the parameter name
     * @param name The parameter name that was searched for
     * @param value The value as a StorageInterface object
     */
    protected void setNodeAsStorage(XMLNode node, String name, StorageInterface value)
    {
        node.delChilds();
        if(value instanceof XMLStorage) {
            node.setValue(null);
            node.addChild(((XMLStorage)value).getXMLNode());
        }else {
            setValue(node,value.load());
        }
    }

    /**
     * Sets the parameter value from a StorageInterface object for a parameter
     * in the XMLNode tree specified
     * @param data The XMLNode tree to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    private XMLNode setParameterAsStorageInData(XMLNode data,String name,StorageInterface value) {
        if(data==null) {
            data = new XMLNode(documentName,null);
        }
        if(bLogging) LOG.debug("setParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
        String mainname = data.getName();
        if(documentName==null|| (mainname!=null && mainname.equalsIgnoreCase(documentName))) {
            if(isSpecialHandled(name)) {
                if(bLogging) LOG.debug("setSpecialParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name+"="+value+" "+this.getClass());
                setSpecialParameterAsStorageInData(data,name,value);
            }else {
                Iterator it = data.getChilds();
                while(it.hasNext()) {
                    XMLNode node = (XMLNode)it.next();
                    if(node.getName().equalsIgnoreCase(name)) {
                        setNodeAsStorage(node,name,value);
                        return data;
                    }
                }
                addChildAsStorage(data,name,value);
            }
        }
        return data;
    }

    public void setParameterAsStorage(String name, StorageInterface value) {
        primaryData = setParameterAsStorageInData(primaryData,name,value);
        secondaryData = setParameterAsStorageInData(secondaryData,name,value);
        save();
    }
}
