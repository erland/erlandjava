package erland.game.crackout;
import erland.util.*;
import java.io.*;
import java.util.*;

/**
 * Handles storing of leveldata and highscore
 */
class CrackOutParameterStorage
	extends ParameterStorage
{
	/**
	 * Creates a new object
	 * @param file Filename of the file where the data should be saved to
	 */
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

	/**
	 * Find a specific level node in the XMLNode tree
	 * @param name The level to find
	 * @return The XMLNode object if the level node was found or null if it was not found
	 */
	protected XMLNode findLevelNode(String name)
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
}