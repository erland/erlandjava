package erland.webapp.stocks.sb;
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

import erland.util.Log;
import erland.util.XMLNode;
import erland.util.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;

public class SBXMLEncoder {
    static String encodeStockData(BufferedReader data) {
        String out="";
        try {
            Log.println(new SBXMLEncoder(),"getXMLData start "+System.currentTimeMillis());
            String xmlData = getXMLData(data);
            Log.println(new SBXMLEncoder(),"getXMLData stop "+System.currentTimeMillis());
            if(xmlData!=null) {
                SBXMLParser parser = new SBXMLParser();
                //System.out.println(xmlData);
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
        boolean bFound = false;
        while(line!=null && !bFound) {
            if(line.startsWith("<?xml")) {
                bFound = true;
                break;
            }
            line = data.readLine();
        }
        if(!bFound) {
            return null;
        }
        //out.append(line);
        out.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        line = data.readLine();
        bFound = false;
        while(line!=null && !bFound) {
            out.append(line);
            if(line.startsWith("</table") || line.startsWith("/TABLE")) {
                bFound = true;
            }
            line = data.readLine();
        }
        if(bFound) {
            return out.toString();
        }else {
            return null;
        }
    }
}
