package erland.util;

import java.util.*;

/**
 * A node object for an XML document tree.
 * An object of this class represents a node and all its child nodes
 * @author Erland Isaksson
 */
public class XMLNode {
	/** The name of this XML node */
	private String name;

	/** The value of this XML node, will be empty if child nodes exists */
	private String value;

	/** The attributes of this XML node */
	private Map attributes;

	/** A list of all child nodes */
	private LinkedList childs;

    /** Indicates if this is a child node */
    private boolean childNode;


	/**
	 * Creates a new empty XML node
	 * @param name The name of the node
	 * @param attributes The value of the attribute strings for the node, set to null if no attributes exist
	 */
	public XMLNode(String name, Map attributes)
	{
		this.name = name;
        this.value = null;
		childs = null;
		childs = new LinkedList();
		this.attributes = attributes;
        this.childNode = false;
        //Log.println(this,"new XMLNode name="+name+" value="+value+" "+getClass().getName()+"@"+Integer.toHexString(hashCode()));
	}

	/**
	 * Get the name of this node
	 * @return The name of the node
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Get the value of this node
	 * @return The value of the node, will be null or and empty string if child nodes exist
	 */
	public String getValue()
	{
        StringBuffer str = new StringBuffer(1000);
        if(childs!=null && childs.size()>0) {
            ListIterator it = childs.listIterator();
            while(it.hasNext()) {
                XMLNode node =(XMLNode)(it.next());
                node.toString(str,true,0);
            }
            return str.toString();
        }else {
            return this.value;
        }
	}

	/**
	 * Set the value of this node
	 * @param value The value of the node
	 */
	public void setValue(String value)
	{
        this.value = null;
		childs = null;
		childs = new LinkedList();
        //Log.println(this,"setValue name="+name+" parent="+parent+" value="+value);
        this.value = value;
	}

    /**
     * Get an iterator with the name of all attributes for the node
     * @return An Iterator with all attribute names
     */
    public Iterator getAttributes() {
        return attributes!=null?attributes.keySet().iterator():new Vector().iterator();
    }

    /**
     * Get the value of a specified attribute for the node
     * @param attribute The name of the attribute to get
     * @return The value of the attribute
     */
    public String getAttributeValue(String attribute) {
        return attributes!=null?(String)attributes.get(attribute):null;
    }
    /**
     * Get all child nodes
     * @return An Iterator with all child nodes
     */
    public Iterator getChilds() {
        return childs!=null?childs.iterator():new Vector().iterator();
    }


    /**
     * Add a child node
     * @param node The child node to add
     */
    public void addChild(XMLNode node) {
        node.childNode = true;
        childs.add(node);
        //Log.println(this,"addChild "+getClass().getName()+"@"+Integer.toHexString(hashCode())+" -> "+node.getClass().getName()+"@"+Integer.toHexString(node.hashCode()));
    }

    /**
     * Insert a child node
     * @param node The child node to insert
     */
    public void insertChild(XMLNode node) {
        node.childNode = true;
        childs.add(0,node);
    }
	/**
	 * Delete a child node.
	 * If the child node has child nodes itself these will also be deleted.
	 * @param node The child node which should be deleted
	 * @see #delChild(String)
	 */
	public void delChild(XMLNode node) {
		childs.remove(node);
	}

	/**
	 * Delete the first child node matching the name
	 * If the child node has child nodes itself these will also be deleted.
	 * @param name The name of the child node which should be deleted
	 * @see #delChild(erland.util.XMLNode)
	 */
	public void delChild(String name) {
		Iterator it = childs.iterator();
		while(it.hasNext()) {
			XMLNode node = (XMLNode)(it.next());
			if(node.getName().equalsIgnoreCase(name)) {
				it.remove();
				return;
			}
		}
	}

	/**
	 * Get a string representation of the XML node and all its child nodes
	 * @return A string representatino of the XML node and its child nodes
	 */
	public String toString()
	{
        return toString(false);
	}

    /**
     * Get a string representation of the XML node and all its child nodes.
     * You have option to select if each node should be separated with a linefeed
     * character or not
     * @param lineFeeds Indicates that each node should be printed on its own line
     * @return A string representationi of the XML node and its child nodes
     */
    public String toString(boolean lineFeeds) {
        StringBuffer str = new StringBuffer(1000);
        toString(str,lineFeeds,0);
        return str.toString();
    }

    /**
     * Appends attributes to a string
     * @param sb StringBuffer object to append attributes to
     * @param attributes The Map with the attributes to append
     */
    private void attributesToString(StringBuffer sb,Map attributes) {
        if(attributes!=null && attributes.size()>0) {
            Set s = attributes.keySet();
            Iterator it = s.iterator();
            while(it.hasNext()) {
                String attr = (String) it.next();
                sb.append(" "+attr+"=\""+attributes.get(attr)+"\"");
            }
        }
    }

    /**
     * Appends a string representation of the XML node and its child nodes to a StringBuffer
     * @param sb The StringBuffer to append the XML node to
     * @param lineFeeds Indicates that each node should be printed on its own line
     * @param level The indentation level
     */
    private void toString(StringBuffer sb, boolean lineFeeds, int level) {
        //Log.println(this,"XMLNode.toString() "+this.getName()+" "+getClass().getName()+"@"+Integer.toHexString(hashCode()));
        if(!childNode) {
            for(int i=0;lineFeeds&&i<level;i++) {
                sb.append("  ");
            }
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            if(lineFeeds) sb.append("\n\n");
        }
        for(int i=0;lineFeeds&&i<level;i++) {
            sb.append("  ");
        }
        if(value!=null&&value.length()>0) {
            //Log.println(this,"XMLNode.toString() "+this.getName()+" "+getClass().getName()+"@"+Integer.toHexString(hashCode())+" value");
            sb.append("<");
            sb.append(getName());
            attributesToString(sb,attributes);
            sb.append(">");
            sb.append(value);
            sb.append("</");
            sb.append(getName());
            sb.append(">");
            if(lineFeeds) {
                sb.append("\n");
            }
        }else {
            //Log.println(this,"XMLNode.toString() "+this.getName()+" "+getClass().getName()+"@"+Integer.toHexString(hashCode())+" childs");
            sb.append("<");
            sb.append(getName());
            attributesToString(sb,attributes);
            Iterator it = getChilds();
            if(it.hasNext()) {
                sb.append(">");
                if(lineFeeds) {
                    sb.append("\n");
                }
                while(it.hasNext()) {
                    ((XMLNode)it.next()).toString(sb,lineFeeds,level + 2);
                }
                for(int i=0;lineFeeds&&i<level;i++) {
                    sb.append("  ");
                }
                sb.append("</");
                sb.append(getName());
                sb.append(">");
                if(lineFeeds) {
                    sb.append("\n");
                }
            }else {
                sb.append("/>");
                if(lineFeeds) {
                    sb.append("\n");
                }
            }
        }
    }
}
