package erland.game.crackout;
import erland.util.*;
import java.io.*;
import java.util.*;

class CrackOutParameterStorage
	extends ParameterStorage
{
	CrackOutParameterStorage(String file)
	{
		super(file,"crackout");
	}
	
	protected String getSpecialParameter(String name)
	{
		XMLNode node = findLevelNode(name);
		if(node!=null) {
			XMLNode nodeval = node.getFirstChild();
			while(nodeval!=null) {
				if(nodeval.getName().equalsIgnoreCase("data")) {
					return nodeval.getValue();
				}
				nodeval = node.getNextChild();
			}
		}
		return "";
	}
	
	protected boolean isSpecialHandled(String name) 
	{
		if(name.substring(0,"level".length()).equalsIgnoreCase("level")) {
			return true;
		}else {
			return false;
		}
	}

	protected void setSpecialParameter(String name, String value) 
	{
		XMLNode node = findLevelNode(name);
		if(node!=null) {
			XMLNode leveldatanode = node.getFirstChild();
			while(leveldatanode!=null) {
				if(leveldatanode.getName().equalsIgnoreCase("data")) {
					leveldatanode.setValue(value);
					save();
					return;
				}
				leveldatanode = node.getNextChild();
			}
		}else {
			XMLNode child = data.addChild("level",null);
			child.addChild("name",name);
			child.addChild("data",value);
			save();
		}
	}

	protected void delSpecialParameter(String name)
	{
		XMLNode node = findLevelNode(name);
		if(node!=null) {
			data.delChild(node);
			save();
			return;
		}
	}
/*
	boolean isCorrectLevelNode(XMLNode node, String name)
	{
		XMLNode levelnamenode = node.getFirstChild();
		boolean bFound = false;
		while(levelnamenode!=null) {
			if(levelnamenode.getName().equalsIgnoreCase("name")) {
				if(levelnamenode.getValue().equalsIgnoreCase(name)) {
					bFound = true;
				}
			}
			levelnamenode = node.getNextChild();
		}
		if(bFound) {
			return true;
		}
		return false;
	}
*/
	XMLNode findLevelNode(String name)
	{
		XMLNode node = data.getFirstChild();
		while(node!=null) {
			if(node.getName().equalsIgnoreCase("level")) {
				XMLNode levelnamenode = node.getFirstChild();
				boolean bFound = false;
				while(levelnamenode!=null) {
					if(levelnamenode.getName().equalsIgnoreCase("name")) {
						if(levelnamenode.getValue().equalsIgnoreCase(name)) {
							bFound = true;
						}
					}
					levelnamenode = node.getNextChild();
				}
				if(bFound) {
					return node;
				}
			}
			node = data.getNextChild();
		}
		return null;
	}
/************************************************
	CrackOutParameterStorage(String file)
	{
		super(file);
	}
	
	public String getParameter(String name)
	{
		//System.out.println("get: name = " + name);
		if(data!=null) {
			String mainname = data.getName();
			if(mainname != null && mainname.equalsIgnoreCase("crackout")) {
				//System.out.println("get: crackout found");
				if(name.substring(0,"level".length()).equalsIgnoreCase("level")) {
					XMLNode node = findLevelNode(name);
					if(node!=null) {
						XMLNode nodeval = node.getFirstChild();
						while(nodeval!=null) {
							if(nodeval.getName().equalsIgnoreCase("data")) {
								//System.out.println("get: " + name + "=" + nodeval.getValue());
								return nodeval.getValue();
							}
							nodeval = node.getNextChild();
						}
					}
				}else {
					XMLNode node = data.getFirstChild();
					while(node!=null) {
						//System.out.println("get: " + node.getName() + " found");
						if(node.getName().equalsIgnoreCase(name)) {
							//System.out.println("get: " + name + "=" + node.getValue());
							return node.getValue();
						}
						
						node = data.getNextChild();
					}
				}
			}
		}
		//System.out.println("<none>");
		return "";
	}
	public void setParameter(String name, String value)
	{
		System.out.println("set: " + name + "=" + value);
		if(data==null) {
			data = new XMLNode("crackout",null,null,null,null);
		}
		String mainname = data.getName();
		if(mainname.equalsIgnoreCase("crackout")) {
			if(name.substring(0,"level".length()).equalsIgnoreCase("level")) {
				XMLNode node = findLevelNode(name);
				if(node!=null) {
					XMLNode leveldatanode = node.getFirstChild();
					while(leveldatanode!=null) {
						if(leveldatanode.getName().equalsIgnoreCase("data")) {
							leveldatanode.setValue(value);
							save();
							return;
						}
						leveldatanode = node.getNextChild();
					}
				}else {
					XMLNode child = data.addChild("level",null);
					child.addChild("name",name);
					child.addChild("data",value);
					save();
				}
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
	XMLNode findLevelNode(String name)
	{
		XMLNode node = data.getFirstChild();
		while(node!=null) {
			if(node.getName().equalsIgnoreCase("level")) {
				XMLNode levelnamenode = node.getFirstChild();
				boolean bFound = false;
				while(levelnamenode!=null) {
					if(levelnamenode.getName().equalsIgnoreCase("name")) {
						if(levelnamenode.getValue().equalsIgnoreCase(name)) {
							bFound = true;
						}
					}
					levelnamenode = node.getNextChild();
				}
				if(bFound) {
					return node;
				}
			}
			node = data.getNextChild();
		}
		return null;
	}
	public void delParameter(String name)
	{
		if(data!=null) {
			//System.out.println("del: " + name);
			String mainname = data.getName();
			if(mainname.equalsIgnoreCase("crackout")) {
				if(name.substring(0,"level".length()).equalsIgnoreCase("level")) {
					XMLNode node = findLevelNode(name);
					if(node!=null) {
						data.delChild(node);
						save();
						return;
					}
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
	void save() 
	{
		if(data!=null) {
			try {
				//System.out.println("save: "+data.toString());
				BufferedWriter w = new BufferedWriter(new FileWriter(file));
				w.write(data.toString());
				w.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	*******************************************/
}