package erland.util;

/**
 * Get, set or delete parameters from some storage
 * @author Erland Isaksson
 */
public interface ParameterValueStorageInterface
{
	/**
	 * Get a parameter value
	 * @param name The name of the parameter
	 * @return The value of the parameter, will be an
	 *         empty string if the parameter does not exist
	 */
	public String getParameter(String name);
	
	/**
	 * Set a parameter value
	 * @param name The name of the parameter
	 * @param value The value of the parameter
	 */
	public void setParameter(String name, String value);
	
	/**
	 * Delete a parameter
	 * @param name The name of the parameter
	 */
	public void delParameter(String name);
}
