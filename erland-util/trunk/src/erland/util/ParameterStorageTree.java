package erland.util;

import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Vector;

/**
 * Get parameters from an XML document
 * @author Erland Isaksson
 */
public class ParameterStorageTree implements ParameterValueStorageExInterface {
    /** Main XMLNode for the parsed XML document */
    private XMLNode resources = null;
    /** XML Parser implementation */
    private SimpleXMLParserHandler handler = new SimpleXMLParserHandler();

    /**
     * Create an instance which accesses data in the specified storage
     * @param storage The storage object
     */
    public ParameterStorageTree(StorageInterface storage) {
        if(XMLParser.getInstance().parse(storage.load(),handler)) {
            resources = handler.getData();
        }else {
            resources = null;
        }
    }

    /**
     * Get XMLNode for a specific parameter
     * @param tokenizer The parameter to get, e.g. "resources.commands.login.class"
     * @param elements The elements to start searching in
     * @return The XMLNode for the requested parameter
     */
    private XMLNode getParameter(StringTokenizer tokenizer, Iterator elements) {
        String token=null;
        if(tokenizer.hasMoreElements()) {
            token = (String)tokenizer.nextElement();
        }else if(elements.hasNext()) {
            XMLNode element = (XMLNode)elements.next();
            return element;
        }
        while(elements.hasNext()) {
            XMLNode element = (XMLNode)elements.next();
            Iterator childs = element.getChilds();
            if(token.equals(element.getName())) {
                if(tokenizer.hasMoreElements()) {
                    if(childs!=null && childs.hasNext()) {
                        return getParameter(tokenizer,childs);
                    }
                }
                return element;
            }else {
                if(childs != null) {
                    while(childs.hasNext()) {
                        XMLNode child = (XMLNode)childs.next();
                        if(child.getName().equals("id")) {
                            if(child.getValue().equals(token)) {
                                if(tokenizer.hasMoreElements()) {
                                    return getParameter(tokenizer,childs);
                                }else {
                                    return getParameter(tokenizer,childs);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * Get the XMLNode for a specific parameter
     * @param str The parameter name
     * @return The XMLNode for the specified parameter
     */
    private XMLNode getParameterNode(String str) {
        StringTokenizer tokenizer = new StringTokenizer("resources."+str,".");
        if(tokenizer.hasMoreElements()) {
            if(resources!=null) {
                Vector v = new Vector();
                v.addElement(resources);
                return getParameter(tokenizer,v.iterator());
            }
        }
        return null;
    }
    public String getParameter(String str) {
        XMLNode node = getParameterNode(str);
        if(node!=null) {
            return node.getValue();
        }else {
            return null;
        }
    }

    public void setParameter(String name, String value) {
        // Do nothing, this is not supported
    }

    public void delParameter(String name) {
        // Do nothing, this is not supported
    }

    public StorageInterface getParameterAsStorage(String name) {
        XMLNode node = getParameterNode(name);
        if(node!=null) {
            return new XMLStorage(node);
        }else {
            return null;
        }
    }
}
