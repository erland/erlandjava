package erland.util;


/**
 * Defines an interface for objects that can serialize their parameters
 * to a {@link ParameterValueStorageExInterface}
 * @author Erland Isaksson
 */
public interface ParameterSerializable {
    /**
     * Write all parameters to a storage
     * @param out ParameterValueStorageExInterface object to write to
     */
    public void write(ParameterValueStorageExInterface out);

    /**
     * Read all parameters from a storage
     * @param in ParameterValueStorageExInterface to read from
     */
    public void read(ParameterValueStorageExInterface in);
}
