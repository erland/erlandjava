package erland.util;

/**
 * A storage object that stores its data as XML in memory
 * @author Erland Isaksson
 */
public class XMLStorage implements StorageInterface {
    /** XMLNode that contains the stored data */
    private XMLNode node;

    /** XMLParser implementation */
    private SimpleXMLParserHandler parser = new SimpleXMLParserHandler();

    /**
     * Creates a new object based on the data in an XMLNode object
     * @param node The XMLNode object containing the data
     */
    public XMLStorage(XMLNode node)
    {
        this.node = node;
    }

    /**
     * Get the storage data as an XMLNode object
     * @return The XMLNode representing the stored data
     */
    public XMLNode getXMLNode()
    {
        return node;
    }

    public void save(String str) {
        if(XMLParser.getInstance().parse(str,parser)) {
            node = parser.getData();
        }else {
            node = null;
        }
    }

    public String load() {
        return node.toString(false);
    }

}
