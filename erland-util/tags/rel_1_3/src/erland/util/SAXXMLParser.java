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

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * A wrapper for the {@link SAXParser} XML parser to make
 * it possible to use it with the XMLParserInterface
 * @author Erland Isaksson
 */
public class SAXXMLParser implements XMLParserInterface {
    /**
     * An adapter that forwards all relevant callbacks to
     * the {@link DefaultHandler} to the specified XMLParserHandlerInterface object
     */
    class SAXConverter extends DefaultHandler {
        /** The XMLParserHandler object to forward all callbacks to */
        private XMLParserHandlerInterface parser;

        /**
         * Creates a new instance that forwards all calls to the specified
         * XMLParserHandlerInterface object
         * @param parser The XMLParserHandlerInterface object to forward calls to
         */
        public SAXConverter(XMLParserHandlerInterface parser) {
            this.parser = parser;
        }
        public void startDocument() {
            parser.startDocument();
        }
        public void endDocument() {
            parser.endDocument();
        }
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes)
                throws SAXException {
            Map attr=null;
            if(attributes!=null&& attributes.getLength()>0) {
                attr = new HashMap();
                for(int i=0;i<attributes.getLength();i++) {
                    attr.put(attributes.getQName(i),attributes.getValue(i));
                }
            }
            parser.startElement(qName,attr);
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            parser.endElement(qName);
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            parser.characters(new String(ch,start,length),0,length);
        }
    }
    public boolean parse(InputStream in, XMLParserHandlerInterface parser) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader reader = saxParser.getXMLReader();
            reader.setContentHandler(new SAXConverter(parser));
            reader.parse(new InputSource(in));
            return true;
        } catch (FactoryConfigurationError factoryConfigurationError) {
            factoryConfigurationError.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }
    public boolean parse(String str, XMLParserHandlerInterface parser) {
        InputStream input = new ByteArrayInputStream(str.getBytes());
        return parse(input,parser);
    }
}
