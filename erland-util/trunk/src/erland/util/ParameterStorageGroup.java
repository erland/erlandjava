package erland.util;

/**
 * Handles storing of groupdata
 */
public class ParameterStorageGroup
	extends ParameterStorageString
{
    /** Prefix for all group attributes */
    protected String groupPrefix;

	/**
	 * Creates a new object
	 * @param storage The storage where the data should be saved to
     * @param documentName Name of the document
	 */
    public ParameterStorageGroup(StorageInterface storage, String documentName, String groupPrefix)
	{
		super(storage,documentName);
        this.groupPrefix = groupPrefix;
	}

    /**
	 * Creates a new object
	 * @param storage The storage where the data should be saved to
     * @param documentName Name of the document
     * @param part Indicates that this is just a part of a larger storage
	 */
    public ParameterStorageGroup(StorageInterface storage, String documentName, String groupPrefix,boolean part)
	{
		super(storage,documentName,part);
        this.groupPrefix = groupPrefix;
	}

	protected String getSpecialParameter(String name)
	{
        Log.println(this,"getSpecialParameter("+name+")");
		XMLNode node = findGroupNode(name);
		if(node!=null) {
            Log.println(this,"getSpecialParameter...");
			XMLNode nodeval = node.getFirstChild();
			while(nodeval!=null) {
                Log.println(this,"getSpecialParameter..."+nodeval.getName()+" "+nodeval.getValue());
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
		if(name.substring(0,groupPrefix.length()).equalsIgnoreCase(groupPrefix)) {
			return true;
		}else {
			return false;
		}
	}

	protected void setSpecialParameter(String name, String value)
	{
		XMLNode node = findGroupNode(name);
		if(node!=null) {
			XMLNode groupdatanode = node.getFirstChild();
			while(groupdatanode!=null) {
				if(groupdatanode.getName().equalsIgnoreCase("data")) {
					groupdatanode.setValue(value);
					save();
					return;
				}
				groupdatanode = node.getNextChild();
			}
		}else {
			XMLNode child = data.addChild(groupPrefix,null);
			child.addChild("name",name);
			child.addChild("data",value);
			save();
		}
	}

	protected void delSpecialParameter(String name)
	{
		XMLNode node = findGroupNode(name);
		if(node!=null) {
			data.delChild(node);
			save();
			return;
		}
	}

	/**
	 * Find a specific group node in the XMLNode tree
	 * @param name The group to find
	 * @return The XMLNode object if the group node was found or null if it was not found
	 */
	protected XMLNode findGroupNode(String name)
	{
		XMLNode node = data.getFirstChild();
		while(node!=null) {
            Log.println(this,"findGroupNode node.getName()="+node.getName());
			if(node.getName().equalsIgnoreCase(groupPrefix)) {
				XMLNode groupnamenode = node.getFirstChild();
				boolean bFound = false;
				while(groupnamenode!=null) {
                    Log.println(this,"groupnamenode.getName()="+groupnamenode.getName());
					if(groupnamenode.getName().equalsIgnoreCase("name")) {
                        Log.println(this,"grounamenode.getValue()="+groupnamenode.getValue());
						if(groupnamenode.getValue().equalsIgnoreCase(name)) {
							bFound = true;
						}
					}
					groupnamenode = node.getNextChild();
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