package erland.util;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
        if(tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
        }else if(elements.hasNext()) {
            XMLNode element = (XMLNode)elements.next();
            return element;
        }
        while(elements.hasNext()) {
            XMLNode element = (XMLNode)elements.next();
            Iterator childs = element.getChilds();
            if(token.equals(element.getName())) {
                if(tokenizer.hasMoreTokens()) {
                    if(childs!=null && childs.hasNext()) {
                        return getParameter(tokenizer,childs);
                    }else {
                        String valueName = tokenizer.nextToken();
                        String value = element.getAttributeValue(valueName);
                        if(value!=null) {
                            XMLNode node = new XMLNode(token,null);
                            node.setValue(value);
                            return node;
                        }else {
                            return null;
                        }
                    }
                }
                return element;
            }else {
                if(childs != null && childs.hasNext()) {
                    while(childs.hasNext()) {
                        XMLNode child = (XMLNode)childs.next();
                        if(child.getName().equals("id")) {
                            if(child.getValue().equals(token)) {
                                return getParameter(tokenizer,childs);
                            }
                        }
                    }
                    if(element.getAttributeValue("id")!=null && element.getAttributeValue("id").equals(token)) {
                        return getParameter(tokenizer,element.getChilds());
                    }
                }else if(element.getAttributeValue("id")!=null && element.getAttributeValue("id").equals(token)) {
                    if(tokenizer.hasMoreTokens()) {
                        String valueName = tokenizer.nextToken();
                        String value = element.getAttributeValue(valueName);
                        if(value!=null) {
                            XMLNode node = new XMLNode(token,null);
                            node.setValue(value);
                            return node;
                        }else {
                            return null;
                        }
                    }else {
                        return element;
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
        StringTokenizer tokenizer = new StringTokenizer(str,".");
        if(tokenizer.hasMoreTokens()) {
            if(resources!=null) {
                Vector v = new Vector();
                v.addElement(resources);
                XMLNode node = getParameter(tokenizer,v.iterator());
                if(node!=null && tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    String value = node.getAttributeValue(token);
                    if(value!=null && !tokenizer.hasMoreTokens()) {
                        node = new XMLNode(token,null);
                        node.setValue(value);
                    }
                }
                return node;
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
