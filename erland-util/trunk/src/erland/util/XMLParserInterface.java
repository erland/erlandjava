package erland.util;

import java.io.InputStream;

/**
 * Defines methods that an XML parser needs to implement
 * @author Erland Isaksson
 */
public interface XMLParserInterface {
    /**
     * Parse an XML document in an InputStream using the specified
     * XML parser handler implementation
     * @param in The InputStream with the XML document
     * @param handler The XML parser handler implementation to use when parsing
     * @return true/false (success/failure)
     */
    boolean parse(InputStream in, XMLParserHandlerInterface handler);

    /**
     * Parse an XML document in String using the specified
     * XML parser handler implementation
     * @param str The String object with the XML document
     * @param handler The XML parser handler implementation to use when parsing
     * @return true/false (success/failure)
     */
    boolean parse(String str, XMLParserHandlerInterface handler);
}
