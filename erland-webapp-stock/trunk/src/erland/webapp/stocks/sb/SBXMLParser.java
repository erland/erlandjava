package erland.webapp.stocks.sb;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

import erland.util.StringUtil;
import erland.util.XMLNode;
import erland.util.XMLParserHandlerInterface;

public class SBXMLParser implements XMLParserHandlerInterface {
    private StringBuffer text;
    private Vector vector = new Vector();
    private XMLNode current = null;
    private boolean bNameFound;
    private boolean bCatchCharacters;
    private boolean bNameFinished;
    private boolean bStockRateCaptionsFound;
    private boolean bFirstStockRateFound;
    private int fieldCounter;
    private StringBuffer date;
    private boolean bStockRateCaptionsFinished;

    public XMLNode getData() {
        if(vector.size()>0) {
            return (XMLNode) vector.elementAt(0);
        }else {
            return null;
        }
    }
    public void startDocument() {
        text = new StringBuffer();
        date = new StringBuffer();
    }

    public void endDocument() {
    }

    public void startElement(String name, Map attributes) {
        if(!bNameFound && name.equals("nobr")) {
            bNameFound = true;
            bCatchCharacters = true;
        }else if(bNameFound && !bStockRateCaptionsFound && name.equals("TR")) {
            bStockRateCaptionsFound = true;
        }else if(bStockRateCaptionsFinished && !bFirstStockRateFound && name.equals("TR")) {
            bFirstStockRateFound = true;
            fieldCounter = 0;
        }else if(bFirstStockRateFound && name.equals("TR")) {
            fieldCounter = 0;
        }else if(bFirstStockRateFound && name.equals("TD")) {
            fieldCounter++;
            if(fieldCounter==1 || fieldCounter==7) {
                bCatchCharacters = true;
            }
        }
        text.setLength(0);
    }

    public void endElement(String name) {
        if(bNameFound && !bNameFinished && name.equals("nobr")) {
            bNameFinished = true;
            bCatchCharacters = false;
            Map stockAttrs = new HashMap();
            int lastNameChar = text.lastIndexOf(",");
            if(lastNameChar>0) {
                int lastNameChar2 = text.lastIndexOf(",",lastNameChar-1);
                if(lastNameChar2>0) {
                    lastNameChar=lastNameChar2;
                }
                text.setLength(lastNameChar);
            }
            stockAttrs.put("name",text.toString());
            current = new XMLNode("stock",stockAttrs);
            vector.addElement(current);
        }else if(bStockRateCaptionsFound && name.equals("TR")) {
            bStockRateCaptionsFinished = true;
        }else if(bFirstStockRateFound && name.equals("TD")) {
            if(fieldCounter==1) {
                date.append(text);
                bCatchCharacters = false;
            }else if(fieldCounter==7) {
                if(text!=null && text.length()>0 && Character.isDigit(text.charAt(0))) {
                    Map rateAttrs = new HashMap();
                    rateAttrs.put("date",date.toString());
                    rateAttrs.put("value",StringUtil.replaceChar(text,'.',',').toString());
                    XMLNode rate = new XMLNode("rate",rateAttrs);
                    current.insertChild(rate);
                }
                bCatchCharacters = false;
                date.setLength(0);
                text.setLength(0);
            }
        }
    }

    public void characters(String chars, int start, int length) {
        if(bCatchCharacters) {
            if(text != null) {
                text.append(chars.substring(start,start+length));
            }
        }
    }
}
