package erland.util;

public interface ParameterValueStorageInterface
{
	String getParameter(String name);
	void setParameter(String name, String value);
	void delParameter(String name);
}
