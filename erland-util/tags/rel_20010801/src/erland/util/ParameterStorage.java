package erland.util;
import java.io.*;
import java.util.*;

public class ParameterStorage
	implements ParameterValueStorageInterface
{
	protected String file;
	protected String documentName;
	protected XMLNode data;
	
	public ParameterStorage(String file)
	{
		init(file,"parameters");
	}

	public ParameterStorage(String file, String documentName)
	{
		init(file,documentName);
	}
	
	void init(String file, String documentName)
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

	protected boolean isSpecialHandled(String name)
	{
		return false;
	}	
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
