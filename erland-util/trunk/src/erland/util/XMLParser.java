package erland.util;

/**
 * This is a singleton object that makes it possible to set
 * and get the currently active XML parser
 * @author Erland Isaksson
 */
public class XMLParser {
    /** The currently active XML parser object */
    private static XMLParserInterface me;

    /**
     * Change the currently active XML parser
     * @param parser The new XML parser to user
     */
    public static void setInstance(XMLParserInterface parser) {
        me = parser;
    }
    /**
     * Get the currently active XML parser, if no parser has been
     * set previously it will be created automatically when this
     * method is called
     * @return The currently active XML parser
     */
    public static XMLParserInterface getInstance() {
        if(me==null) {
            me = new SimpleXMLParser();
        }
        return me;
    }
}
