package erland.util;

import java.util.*;

public class XMLNode {
	String procInstr;
	String name;
	String value;
	String attributes;
	XMLNode parent;
	LinkedList childs;
	String tmpName;
	String tmpValue;
	String tmpProcInstr;
	String tmpAttr;
	int pos =0;
	
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

	public String getName()
	{
		return this.name;
	}
	public String getValue()
	{
		return this.value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	
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
	public XMLNode addChild(String name, String value) {
		XMLNode tmp = new XMLNode(name,value,null,null,this);
		childs.add(tmp);
		return tmp;
	}
	public void delChild(XMLNode node) {
		childs.remove(node);
	}
	
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

	public boolean parse(String data)
	{
		return parse(data,null);
	}
	
	boolean parse(String data, XMLNode parent)
	{
		//System.out.println("parse:" + this.hashCode()+ ":"+ data);
		int pos =0;
		while(pos!=-1) {
			int oldpos = pos;
			pos = parseNextSibling(data,oldpos);
			if(pos!=-1) {
				XMLNode tmp;
				tmp = new XMLNode(tmpName, null,tmpAttr,tmpProcInstr,parent);
				if(parent!=null) {
					parent.childs.add(tmp);
					System.out.println("parse(store):" + this.hashCode()+ ":name="+ tmpName);
				}else {
					this.name = tmpName;
					if(tmpProcInstr!=null && tmpProcInstr.length()>0) {
						this.procInstr = tmpProcInstr;
					}else {
						this.procInstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
					}
					this.attributes = tmpAttr;
					System.out.println("parse(store):" + this.hashCode()+ ":name="+ tmpName);
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
				System.out.println("parse(store):" + this.hashCode()+ ":"+ this.value);
				return true;
			}
		}
		return false;
	}

	int parseNextSibling(String value, int pos)
	{
		//System.out.println("parseNextSibling: "+pos + ":"+ value);
		//System.out.println("parseNextSibling2: "+value.substring(pos));
		int startpos = pos;
		tmpProcInstr = "";
		tmpAttr="";
		try {
			//System.out.println("parseNextSibling2: "+pos + ":"+ value);
			boolean processingInstruction=false;
			do {
				System.out.println("parseNext: "+ value.substring(pos));
				pos=skipSpaces(value,pos);
				//System.out.println("parseNextSibling3: "+pos + ":"+ value);
				if(value.charAt(pos)!='<') {
					return -1;
				}
				startpos = pos;
				System.out.println("parseNext: " + value.substring(pos,pos+2));
				if(value.substring(pos,pos+2).equals("<?")) {
					processingInstruction=true;
					String endtag = "?>";
					int endpos = value.indexOf(endtag,pos);
					if(endpos==-1) {
						return -1;
					}
					tmpProcInstr += value.substring(pos,endpos+endtag.length());
					System.out.println("parseNext: " + tmpProcInstr);
					pos = endpos+endtag.length();
				}else {
					processingInstruction=false;
				}
			}while(processingInstruction);
			System.out.println("parseNext: "+value.substring(pos));
			while(value.charAt(pos)!=' ' && value.charAt(pos)!='>') {
				pos++;
			}
			tmpName = value.substring(startpos+1,pos);
			int attrStartPos = pos;
			while(value.charAt(pos)!='>') {
				pos++;
			}
			tmpAttr = value.substring(attrStartPos,pos);
			//System.out.println("parseNextSibling5: "+name);
			
			String endtag = "</"+tmpName+">";
			int endpos = value.indexOf(endtag,pos);
			//System.out.println("parseNextSibling6: "+endpos);
			
			if(endpos==-1) {
				name=null;
				return -1;
			}
			tmpValue = value.substring(pos+1,endpos);
			//System.out.println("parseNextSibling7: "+this.value);
			return endpos + endtag.length();
		}catch (IndexOutOfBoundsException e) {
			return -1;
		} 
	}
	
	int skipSpaces(String data, int pos)
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
