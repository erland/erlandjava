package erland.webapp.stocks.bl.logic.stock;
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

import java.io.InputStream;
import java.text.*;
import java.util.Map;

import erland.webapp.diagram.DateValue;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueSerie;
import erland.util.XMLParserHandlerInterface;
import erland.util.XMLParser;

public class XMLStock implements XMLParserHandlerInterface, StockInterface {
    private DateValueSerie rates;

    private DateFormat dateFormat;
    private NumberFormat kursFormat;

    public XMLStock(InputStream xmlData) {
        rates = new DateValueSerie("");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        kursFormat = new DecimalFormat("#,#",symbols);
        XMLParser.getInstance().parse(xmlData,this);
    }

    public void startElement(String name, Map attributes) {
        if(name.equals("rate")) {
            try {
                rates.addDateValue(new DateValue(dateFormat.parse(((String) attributes.get("date"))),kursFormat.parse((String) attributes.get("value")).doubleValue()));
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (NumberFormatException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }

        }else if(name.equals("stock")) {
            rates.setName((String)attributes.get("name"));
        }

    }

    public void startDocument() {
    }

    public void endDocument() {
    }

    public void endElement(String name) {
    }

    public void characters(String chars, int start, int length) {
    }

    public DateValueSerieInterface getRates() {
        return rates;
    }
}
