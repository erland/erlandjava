package erland.util;

/**
 * Defines an inteface for different types of storages that can store a string
 * @author Erland Isaksson
 */
public interface StorageInterface {
    /**
     * Store the specified string
     * @param str String to store
     */
    public void save(String str);

    /**
     * Read data from the storage as a string
     * @return Data in the storage
     */
    public String load();
}
