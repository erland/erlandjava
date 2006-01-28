package erland.webapp.stocks.bl.logic.broker.robur;
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

import java.io.BufferedReader;
import java.io.IOException;

public class RoburXMLEncoder {
    static String encodeStockData(BufferedReader data) {
        StringBuffer out = new StringBuffer(2000);

        out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        try {
            String line = data.readLine();
            int pos = line.indexOf(":");
            if(pos>=0 && line.length()>pos+1) {
                line = line.substring(pos+1).trim();
                out.append("<stock name=\""+line+"\">");

                data.readLine(); // Skip line with column headers

                line = data.readLine();
                while(line!=null) {
                    String[] fields = line.split("\t");
                    out.append("\n<rate date=\""+fields[0]+"\" value=\""+fields[1]+"\"/>");
                    line = data.readLine();
                }

                out.append("\n</stock>");
            }else {
                out.setLength(0);
                out.append("Error!");
            }
        } catch (IOException e) {
            out.setLength(0);
            out.append("Error!");
        }
        return out.toString();
    }
}
