package erland.webapp.stocks;

import java.io.InputStream;
import java.text.*;
import java.util.Map;

import erland.webapp.diagram.DateValue;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueSerie;
import erland.util.XMLParserHandlerInterface;
import erland.util.XMLParser;

public class Stock implements XMLParserHandlerInterface, StockInterface {
    private DateValueSerie rates;

    private DateFormat dateFormat;
    private NumberFormat kursFormat;

    public Stock(InputStream xmlData) {
        rates = new DateValueSerie("");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        kursFormat = new DecimalFormat("#,#");
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
