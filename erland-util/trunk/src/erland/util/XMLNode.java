package erland.util;

import java.util.*;

/**
 * Makes it easy to parse and work with XML documents
 * Note that this is not a fully compliant XML parser, just a simple parser
 * that only supports ordinary XML nodes. It is currently not possilble to
 * access attributes defined in the XML nodes.
 * An object of this class represents a node and all its child nodes
 * @author Erland Isaksson
 */
public class XMLNode {
	/**
	 * XML processing instruction related to this XML node
	 */
	protected String procInstr;

	/**
	 * The name of this XML node
	 */
	protected String name;

	/**
	 * The value of this XML node, will be empty if child nodes exists
	 */
	protected String value;

	/**
	 * The attributes of this XML node
	 */
	protected String attributes;

	/**
	 * The parent XML node, null if this it the top of the XML tree
	 */
	protected XMLNode parent;

	/**
	 * A list of all child nodes
	 */
	protected LinkedList childs;

	/**
	 * Current child node accessed
	 */
	protected int pos =0;

	/**
	 * Temporary storage for node name during parsing
	 */
	protected String tmpName;
	/**
	 * Temporary storage for node value during parsing
	 */
	protected String tmpValue;
	/**
	 * Temporary storage for XML processing instruction during parsing
	 */
	protected String tmpProcInstr;
	/**
	 * Temporary storage for node attributes during parsing
	 */
	protected String tmpAttr;
	
	/**
	 * Creates a new empty XML node
	 */
	public XMLNode() 
	{
		this.name = null;
		this.value = null;
		this.procInstr = null;
		this.attributes = null;
		childs = null;
		childs = new LinkedList();
		this.parent = null;
	}

	/**
	 * Creates a new empty XML node
	 * @param name The name of the node
	 * @param value The value of the node, set to null if childs is going to be added
	 * @param attibutes The value of the attribute strings for the node, set to null if no attributes exist
	 * @param procInstr The value of the XML processing instruction for the node, set to null if no processing instruction exist
	 * @param parent The parent node, set to null if this is the top of the XML tree
	 */
	public XMLNode(String name, String value, String attributes, String procInstr, XMLNode parent) 
	{
		this.name = name;
		this.value = value;
		childs = null;
		childs = new LinkedList();
		this.parent = parent;
		this.procInstr = procInstr;
		this.attributes = attributes;
		if(parent==null) {
			this.procInstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		}
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
		return this.value;
	}

	/**
	 * Set the value of this node
	 * @param value The value of the node
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * Get the first child node
	 * @return The first child node, null if no childs exist
	 * @see #getNextChild()
	 */
	public XMLNode getFirstChild()
	{
		try {
			pos=0;
			if(childs!=null) {
				return (XMLNode)(childs.get(pos));
			}
		}catch(IndexOutOfBoundsException e) {
		}
		return null;
	}
	
	/**
	 * Get the next child node
	 * @return The next child node, null if no more childs exist
	 * @see #getFirstChild()
	 */
	public XMLNode getNextChild()
	{
		try {
			pos++;
			if(childs!=null) {
				if(pos>=childs.size()) {
					return null;
				}
				return (XMLNode)(childs.get(pos));
			}
		}catch(IndexOutOfBoundsException e) {
		}
		return null;
	}

	/**
	 * Add a child node
	 * @param name The name of the child node
	 * @param value The value of the child node
	 * @return The created child node
	 */
	public XMLNode addChild(String name, String value) {
		XMLNode tmp = new XMLNode(name,value,null,null,this);
		childs.add(tmp);
		return tmp;
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
	 * @see #delChild(XMLNode)
	 */
	public void delChild(String name) {
		ListIterator it = childs.listIterator();
		while(it.hasNext()) {
			XMLNode node = (XMLNode)(it.next());
			if(node.getName().equalsIgnoreCase(name)) {
				it.remove();
				return;
			}
		}
	}

	/**
	 * Initiate the object with new XML data from a String
	 * @param data The XML data string
	 * @return <pre>true - XML string parsed successfully</pre>
	 *         <pre>false - XML string parsing failed</pre>
	 */
	public boolean parse(String data)
	{
		return parse(data,null);
	}
	

	/**
	 * Initiate the object with new XML data from a String
	 * @param data The XML data string
	 * @param parent The parent XML node
	 * @return <pre>true - XML string parsed successfully</pre>
	 *         <pre>false - XML string parsing failed</pre>
	 */
	public boolean parse(String data, XMLNode parent)
	{
		Log.println(this,"parse:" + this.hashCode()+ ":"+ data);
		int pos =0;
		while(pos!=-1) {
			int oldpos = pos;
			pos = parseNextSibling(data,oldpos);
			if(pos!=-1) {
				XMLNode tmp;
				tmp = new XMLNode(tmpName, null,tmpAttr,tmpProcInstr,parent);
				if(parent!=null) {
					parent.childs.add(tmp);
					Log.println(this,"parse(store):" + this.hashCode()+ ":name="+ tmpName);
				}else {
					this.name = tmpName;
					if(tmpProcInstr!=null && tmpProcInstr.length()>0) {
						this.procInstr = tmpProcInstr;
					}else {
						this.procInstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
					}
					this.attributes = tmpAttr;
					Log.println(this,"parse(store):" + this.hashCode()+ ":name="+ tmpName);
				}
				
				if(parent!=null) {
					if(!tmp.parse(tmpValue,tmp)) {
						if(parent!=null) {
							parent.childs.remove(tmp);
						}else {
							this.name = null;
						}
					}
				}else {
					if(!this.parse(tmpValue,this)) {
						this.name = null;
					}
				}
			}else {
				if(data.substring(oldpos).length()<=0) {
					return true;
				}
				this.value = data.substring(oldpos);
				Log.println(this,"parse(store):" + this.hashCode()+ ":"+ this.value);
				return true;
			}
		}
		return false;
	}

	/**
	 * Initiate the object with new XML data from a String
	 * @param value The XML data string
	 * @param pos The position in the string where parsing should start
	 * @return The position in the string where the next node starts, or -1 if
	 *         the parsing failed. If the position is equal to the string length
	 *         no more nodes exist
	 */
	protected int parseNextSibling(String value, int pos)
	{
		Log.println(this,"parseNextSibling: "+pos + ":"+ value);
		Log.println(this,"parseNextSibling2: "+value.substring(pos));
		int startpos = pos;
		tmpProcInstr = "";
		tmpAttr="";
		try {
			boolean processingInstruction=false;
			do {
				Log.println(this,"parseNext: "+ value.substring(pos));
				pos=skipSpaces(value,pos);
				if(value.charAt(pos)!='<') {
					return -1;
				}
				startpos = pos;
				Log.println(this,"parseNext: " + value.substring(pos,pos+2));
				if(value.substring(pos,pos+2).equals("<?")) {
					processingInstruction=true;
					String endtag = "?>";
					int endpos = value.indexOf(endtag,pos);
					if(endpos==-1) {
						return -1;
					}
					tmpProcInstr += value.substring(pos,endpos+endtag.length());
					Log.println(this,"parseNext: " + tmpProcInstr);
					pos = endpos+endtag.length();
				}else {
					processingInstruction=false;
				}
			}while(processingInstruction);
			Log.println(this,"parseNext: "+value.substring(pos));
			while(value.charAt(pos)!=' ' && value.charAt(pos)!='>') {
				pos++;
			}
			tmpName = value.substring(startpos+1,pos);
			int attrStartPos = pos;
			while(value.charAt(pos)!='>') {
				pos++;
			}
			tmpAttr = value.substring(attrStartPos,pos);
			
			String endtag = "</"+tmpName+">";
			int endpos = value.indexOf(endtag,pos);
			
			if(endpos==-1) {
				name=null;
				return -1;
			}
			tmpValue = value.substring(pos+1,endpos);
			return endpos + endtag.length();
		}catch (IndexOutOfBoundsException e) {
			return -1;
		} 
	}
	
	/**
	 * Calculate the position of the first real character after the specified position, skipping
	 * space and tab characters.
	 * @param data The text string
	 * @param pos The start position in the string
	 * @return The position of the next real character, 
	 *         equal to string length if no more real character exist
	 */
	protected int skipSpaces(String data, int pos)
	{
		char ch;
		try {
			ch=data.charAt(pos);
			while(ch==' ' && ch=='\t') {
				pos++;
				ch=data.charAt(pos);
			}
		}catch(IndexOutOfBoundsException e) {
			return pos = data.length(); 
		}
		return pos;
	}
	
	/**
	 * Get a string representation of the XML node and all its child nodes
	 * @return A string representatino of the XML node and its child nodes
	 */
	public String toString()
	{
		String str = "";//"[" + this.hashCode()+"]{";
		if(procInstr!=null && procInstr.length()>0) {
			str += procInstr + "\n\n";
		}
		if(name!=null && name.length()>0) {
			str += "<" + name;
			if(attributes!=null && attributes.length()>0) {
				str+=attributes;
			}
			str+=">";
			if(childs!=null && childs.size()>0) {
				str += "\n";
				ListIterator it = childs.listIterator();
				while(it.hasNext()) {
					XMLNode node =(XMLNode)(it.next());
					str += node.toString();
				}
			}else if(value!=null && value.length()>0){
				str += value;
			}
			str += "</" + name + ">\n";
		}else if(value!=null){
			str += value +"\n";
		}else {
			str += "ERROR!";
		}
		str += "";//"}";
		return str;
	}
}
