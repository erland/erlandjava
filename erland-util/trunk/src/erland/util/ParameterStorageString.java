package erland.util;
import java.io.*;
import java.util.*;

/**
 * Get, set or delete parameters stored in a file
 * @author Erland Isaksson
 */
public class ParameterStorageString
	implements ParameterValueStorageInterface
{
	/**
	 * The section in the file where the parameters are stored
	 */
	protected String documentName;
	
	/**
	 * The in-memory representation of the file where the parameters
	 * are stored
	 */
	protected XMLNode data;

	/** String which parameters are accessed in */
	protected String dataStr;
	
	
	/**
	 * Creates an empty unusable instance, you have to call the init method
	 * before you start using this
	 */
	protected ParameterStorageString()
	{
		data = null;
		dataStr = null;
	}
	
    /**
     * @param data The String object which the parameters should be accessed from
     */
	public ParameterStorageString(String data)
	{
		init(data,"parameters");
	}

    /**
     * @param data The String object which the parameters should be
     *             accessed from
     * @param documentName The name of the section in the file where parameters
     *                     are stored
     */
	public ParameterStorageString(String data, String documentName)
	{
		this.dataStr = data;
		init(data,documentName);
	}
	
	/**
	 * Initialize this object
     * @param data The String object which the parameters should be
     *             accessed from
     * @param documentName The name of the section in the file where parameters
     *                     are stored
	 */
	protected void init(String dataStr, String documentName)
	{
		this.documentName = documentName;
		data = null;
		if(dataStr!=null && dataStr.length()>0) {
			data = new XMLNode();
			if(!data.parse(dataStr)) {
				data = null;
			}else {
				Log.println(this,"*******************");
				Log.println(this,data);
				Log.println(this,"*******************");
			}
		}
	}

	
	/**
	 * Check if this parameter should be special handled, will be autmatically called
	 * when a parameter is accessed. Implement this in your sub class if you want to handle some
	 * parameters in a special way.
	 * @param name The parameter name
	 * @return Always false
	 *         <pre>true - This parameter should be special handled,then one of 
	 *         {@link #getSpecialParameter(String)}, {@link #setSpecialParameter(String,String)}, {@link #delSpecialParameter(String)}, 
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
	 * Get parameter value. Implement this in your sub class if you want to handle some
	 * parameters in a special way
	 * @param name The parameter name
	 * @return Always an empty string
	 * @see #isSpecialHandled(String)
	 */
	protected String getSpecialParameter(String name)
	{
		return "";
		// Not needed, implemented in sub classes if required
	}
	
	public String getParameter(String name)
	{
		if(data!=null) {
			String mainname = data.getName();
			if(mainname != null && mainname.equalsIgnoreCase(documentName)) {
				if(isSpecialHandled(name)) {
					return getSpecialParameter(name);
				}else {
					XMLNode node = data.getFirstChild();
					while(node!=null) {
						if(node.getName().equalsIgnoreCase(name)) {
							Log.println(this,"get: " +name + " = "+ node.getValue());
							return node.getValue();
						}
						
						node = data.getNextChild();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Set parameter value. Implement this in your sub class if you want to handle some
	 * parameters in a special way
	 * @param name The parameter name
	 * @param value The parameter value
	 * @see #isSpecialHandled(String)
	 */
	protected void setSpecialParameter(String name, String value)
	{
		// Not needed, implemented in sub classes if required
	}
	
	public void setParameter(String name, String value)
	{
		Log.println(this,"set: " +name + " = "+ value);
		if(data==null) {
			data = new XMLNode(documentName,null,null,null,null);
		}
		String mainname = data.getName();
		if(mainname.equalsIgnoreCase(documentName)) {
			if(isSpecialHandled(name)) {
				setSpecialParameter(name,value);
			}else {
				XMLNode node = data.getFirstChild();
				while(node!=null) {
					if(node.getName().equalsIgnoreCase(name)) {
						node.setValue(value);
						save();
						return;
					}
					node = data.getNextChild();
				}
				data.addChild(name,value);
				save();
			}
		}
	}
	
	/**
	 * Delete parameter. Implement this in your sub class if you want to handle some
	 * parameters in a special way
	 * @param name The parameter name
	 * @see #isSpecialHandled(String)
	 */
	protected void delSpecialParameter(String name)
	{
		// Not needed, implemented in sub classes if required
	}
	
	public void delParameter(String name)
	{
		if(data!=null) {
			String mainname = data.getName();
			if(mainname.equalsIgnoreCase(documentName)) {
				if(isSpecialHandled(name)) {
					delSpecialParameter(name);
					return;
				}else {
					XMLNode node = data.getFirstChild();
					while(node!=null) {
						if(node.getName().equalsIgnoreCase(name)) {
							data.delChild(node);
							save();
							return;
						}
						node = data.getNextChild();
					}
				}
			}
		}
	}

	/**
	 * Save the parameters in {@link #data data} to file.
	 * This will be automatically called in all the parameter access methods
	 */
	protected void save() 
	{
		if(data!=null) {
			dataStr = data.toString();
		}
	}
}
