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

import erland.util.XMLNode;
import erland.util.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SBXMLEncoder {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(SBXMLEncoder.class);
    private static Object logObject;
    private static Object getLogObject() {
        if(logObject==null) {
            logObject = new SBXMLEncoder();
        }
        return logObject;
    }
    static String encodeStockData(BufferedReader data, String stockName, int rateColumn) {
        String out="";
        try {
            LOG.debug("getXMLData start "+System.currentTimeMillis());
            String xmlData = getXMLData(data);
            if(LOG.isTraceEnabled()) {
                LOG.trace(xmlData);
            }
            LOG.debug("getXMLData stop "+System.currentTimeMillis());
            if(xmlData!=null) {
                SBXMLParser parser = new SBXMLParser(stockName,rateColumn);
                if(XMLParser.getInstance().parse(xmlData,parser)) {
                    XMLNode element = parser.getData();
                    out = element.toString(true);
                }
            }
        } catch (IOException e) {
            out = "Error!";
        } catch (Exception e) {
            out = "Error!";
        }
        return out.toString();
    }
    private static String getXMLData(BufferedReader data) throws IOException {
        StringBuffer out = new StringBuffer(200000);
        String line = data.readLine();
        boolean bFound1 = false;
        boolean bFound2 = false;
        while(line!=null && !bFound2) {
            if(line.indexOf("table")>=0) {
                if(bFound1) {
                    bFound2 = true;
                    break;
                }else {
                    bFound1 = true;
                }
            }
            line = data.readLine();
        }
        if(!bFound2) {
            return null;
        }
        //out.append(line);
        out.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        //line = data.readLine();
        boolean bFound = false;
        while(line!=null && !bFound) {
            out.append(line);
            if(line.startsWith("</table")) {
                bFound = true;
            }
            line = data.readLine();
        }
        if(bFound) {
            return replaceEntities(out.toString());
        }else {
            return null;
        }
    }
    private static String replaceEntities(String data) {
        data = data.replace("&auml;","a");
        data = data.replace("&ouml;","o");
        return data;
    }
}
