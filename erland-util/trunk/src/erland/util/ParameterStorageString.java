package erland.util;

import java.util.Iterator;


/**
 * Get, set or delete parameters stored in a file
 * @author Erland Isaksson
 */
public class ParameterStorageString
	implements ParameterValueStorageExInterface
{
	/**
	 * The section in the file where the parameters are stored
	 */
	private String documentName;
	
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
		init(primaryStorage,secondaryStorage,"parameters");
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
        bLogging = Log.isEnabled(this);
		this.documentName = documentName;
        this.primaryStorage = primaryStorage;
        if(primaryStorage!=null) {
            if(bLogging) Log.println(this,"PrimaryStorage = "+primaryStorage);
            this.primaryData = getData(primaryStorage);
            if(primaryData!=null) {
                if(bLogging) Log.println(this,"PrimaryData = "+primaryData.getClass().getName()+"@"+Integer.toHexString(primaryData.hashCode()));
            }else {
                if(bLogging) Log.println(this,"PrimaryData = null");
            }
        }
        if(secondaryStorage!=null && secondaryStorage!=primaryStorage) {
            if(bLogging) Log.println(this,"SecondaryStorage = "+secondaryStorage);
            this.secondaryData = getData(secondaryStorage);
            if(secondaryData!=null) {
                if(bLogging) Log.println(this,"SecondaryData = "+secondaryData.getClass().getName()+"@"+Integer.toHexString(secondaryData.hashCode()));
            }else {
                if(bLogging) Log.println(this,"SecondaryData = null");
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
                if(XMLParser.getInstance().parse(s,parser)) {
                    data = parser.getData();
                }else {
                    if(bLogging) Log.println(this,"getData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode()));
                    //Log.println(this,"*******************");
                    //Log.println(this,data);
                    //Log.println(this,"*******************");
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
            if(bLogging) Log.println(this,"getParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            String mainname = data.getName();
            if(mainname != null && mainname.equalsIgnoreCase(documentName)) {
                if(isSpecialHandled(name)) {
                    if(bLogging) Log.println(this,"getSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                    return getSpecialParameterInData(data,name);
                }else {
                    Iterator it = data.getChilds();
                    while(it.hasNext()) {
                        XMLNode node = (XMLNode)it.next();
                        if(node.getName().equalsIgnoreCase(name)) {
                            //Log.println(this,"get: " +name + " = "+ node.getValue());
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
        if(bLogging) Log.println(this,"setParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
        String mainname = data.getName();
        if(mainname.equalsIgnoreCase(documentName)) {
            if(isSpecialHandled(name)) {
                if(bLogging) Log.println(this,"setSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name+"="+value+" "+this.getClass());
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
     * Sets the value of a XMLNode object, the value will be parsed to a
     * child tree if it contains XML
     * @param node The XMLNode object to set value on
     * @param value The value to set
     */
    protected void setValue(XMLNode node, String value) {
        if(XMLParser.getInstance().parse(value,parser)) {
            if(bLogging) Log.println(this,"ParameterStorageString::setValue2 name="+node.getName()+"="+value);
            node.setValue(null);
            node.addChild(parser.getData());
        }else {
            if(bLogging) Log.println(this,"ParameterStorageString::setValue3 name="+node.getName()+"="+value);
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
            if(bLogging) Log.println(this,"delParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            String mainname = data.getName();
            if(mainname.equalsIgnoreCase(documentName)) {
                if(isSpecialHandled(name)) {
                    if(bLogging) Log.println(this,"delSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
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
            if(bLogging) Log.println(this,"getParameterAsStorage "+name+" -> "+storage.getClass().getName()+"@"+Integer.toHexString(storage.hashCode()));
        }else {
            if(bLogging) Log.println(this,"getParameterAsStorage "+name+" -> null");
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
            if(bLogging) Log.println(this,"getParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
			String mainname = data.getName();
			if(mainname != null && mainname.equalsIgnoreCase(documentName)) {
				if(isSpecialHandled(name)) {
                    if(bLogging) Log.println(this,"getSpecialParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
					return getSpecialParameterAsStorageInData(data,name);
				}else {
					Iterator it = data.getChilds();
					while(it.hasNext()) {
                        XMLNode node = (XMLNode)it.next();
						if(node.getName().equalsIgnoreCase(name)) {
							//Log.println(this,"get: " +name + " = "+ node.getValue());
                            Iterator it2 = node.getChilds();
							if(it2.hasNext()) {
                                return new XMLStorage((XMLNode)it2.next());
                            }else {
                                return new StringStorage(node.getValue());
                            }
						}
					}
				}
			}
		}
        return null;
    }
}
