package erland.util;

/**
 * Get, set or delete parameters from some storage.
 * This interface adds the functionality to get a parameter value
 * as a {@link StorageInterface} object to the {@link ParameterValueStorageInterface} interface
 * @author Erland Isaksson
 */
public interface ParameterValueStorageExInterface extends ParameterValueStorageInterface {
    /**
     * Retreives the parameter as a StorageInterface object instead of a value
     * Observe that this is a new StorageInterface object and will not be related
     * to any of your other StorageInterface objects
     * @param name The name of the parameter to retreive
     * @return A StorageInterface object containing the value of the parameter
     */
    public StorageInterface getParameterAsStorage(String name);
}
