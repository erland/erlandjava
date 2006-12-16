package erland.webapp.stocks.bl.logic.broker.sb;
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

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

import erland.util.StringUtil;
import erland.util.XMLNode;
import erland.util.XMLParserHandlerInterface;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SBXMLParser implements XMLParserHandlerInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(SBXMLParser.class);
    private StringBuffer text;
    private Vector vector = new Vector();
    private XMLNode current = null;
    private boolean bCatchCharacters;
    private boolean bStockRateCaptionsFound;
    private boolean bFirstStockRateFound;
    private int fieldCounter;
    private StringBuffer date;
    private boolean bStockRateCaptionsFinished;
    private int rateColumn;
    private String stockName;

    public SBXMLParser(String stockName, int rateColumn) {
        this.rateColumn = rateColumn;
        this.stockName = stockName;
    }
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
        if(!bStockRateCaptionsFound && name.equals("tr")) {
            bStockRateCaptionsFound = true;
            Map stockAttrs = new HashMap();
            stockAttrs.put("name",stockName);
            current = new XMLNode("stock",stockAttrs);
            vector.addElement(current);
        }else if(bStockRateCaptionsFinished && !bFirstStockRateFound && name.equals("tr")) {
            bFirstStockRateFound = true;
            fieldCounter = 0;
        }else if(bFirstStockRateFound && name.equals("tr")) {
            fieldCounter = 0;
        }else if(bFirstStockRateFound && name.equals("td")) {
            fieldCounter++;
            if(fieldCounter==1 || fieldCounter==rateColumn) {
                bCatchCharacters = true;
            }
        }
        text.setLength(0);
    }

    public void endElement(String name) {
        if(bStockRateCaptionsFound && name.equals("tr")) {
            bStockRateCaptionsFinished = true;
            if(LOG.isDebugEnabled()) LOG.debug("Got captions");
        }else if(bFirstStockRateFound && name.equals("td")) {
            if(fieldCounter==1) {
                date.append(text);
                bCatchCharacters = false;
            }else if(fieldCounter==rateColumn) {
                if(LOG.isDebugEnabled()) LOG.debug("Got rate for "+date.toString()+": "+text.toString());
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
