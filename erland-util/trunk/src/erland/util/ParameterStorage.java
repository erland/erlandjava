package erland.util;
import java.io.*;
import java.util.*;

/**
 * Get, set or delete parameters stored in a file
 * @author Erland Isaksson
 */
public class ParameterStorage
	implements ParameterValueStorageInterface
{
	/**
	 * The filename of the file that holds the parameters
	 */
	protected String file;
	
	/**
	 * The section in the file where the parameters are stored
	 */
	protected String documentName;
	
	/**
	 * The in-memory representation of the file where the parameters
	 * are stored
	 */
	protected XMLNode data;
	
    /**
     * @param file The name of the file which the parameters should be
     *             accessed from
     */
	public ParameterStorage(String file)
	{
		init(file,"parameters");
	}

    /**
     * @param file The name of the file which the parameters should be
     *             accessed from
     * @param documentName The name of the section in the file where parameters
     *                     are stored
     */
	public ParameterStorage(String file, String documentName)
	{
		init(file,documentName);
	}
	
	/**
	 * Initialize this object
     * @param file The name of the file which the parameters should be
     *             accessed from
     * @param documentName The name of the section in the file where parameters
     *                     are stored
	 */
	protected void init(String file, String documentName)
	{
		this.file = file;
		this.documentName = documentName;
		//BufferedWriter w;
		BufferedReader r=null;
		try {
			//w = new BufferedWriter(new FileWriter(file));
			r = new BufferedReader(new FileReader(file));

			String str = new String();
			String line;
			line = r.readLine();
			while(line!=null) {
				str+=line;
				line = r.readLine();
			}
		
			data = new XMLNode();
			if(!data.parse(str)) {
				data = null;
			}else {
				System.out.println("*******************");
				System.out.println(data);
				System.out.println("*******************");
			}
		}catch(IOException e) {
			e.printStackTrace();
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
			try {
				BufferedWriter w = new BufferedWriter(new FileWriter(file));
				w.write(data.toString());
				w.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
