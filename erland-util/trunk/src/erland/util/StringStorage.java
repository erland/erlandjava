package erland.util;

import erland.util.StorageInterface;

/**
 * Implements a storage that stores the data in a String in memory
 * @author Erland Isaksson
 */
public class StringStorage implements StorageInterface {
    /** String object that contains the data */
    private String str;

    /**
     * Creates a new empty storage
     */
    public StringStorage()
    {
        this.str = "";
    }
    /**
     * Creates a new storage based on a String
     * @param str String with data to store in the newly created storage
     */
    public StringStorage(String str)
    {
        if(str!=null) {
            this.str = str;
        }else {
            this.str = "";
        }
    }
    public void save(String str) {
        this.str = str;
    }

    public String load() {
        return str;
    }
}
