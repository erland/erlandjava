package erland.webapp.stocks.robur;

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
