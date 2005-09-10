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

        out.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");

        try {
            out.append("<stock name=\""+data.readLine()+"\">");

            String line = data.readLine();
            while(line!=null) {
                out.append("\n<rate date=\""+line.substring(0,10)+"\" value=\""+line.substring(11)+"\"/>");
                line = data.readLine();
            }

            out.append("\n</stock>");
        } catch (IOException e) {
            out.setLength(0);
            out.append("Error!");
        }
        return out.toString();
    }
}
