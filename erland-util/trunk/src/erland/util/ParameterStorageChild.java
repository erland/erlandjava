package erland.util;

public class ParameterStorageChild implements ParameterValueStorageExInterface {
    private String prefix;
    private ParameterValueStorageExInterface values;
    public ParameterStorageChild(String prefix, ParameterValueStorageExInterface values) {
        this.prefix = prefix;
        this.values = values;
    }

    public StorageInterface getParameterAsStorage(String name) {
        return values.getParameterAsStorage(prefix+name);
    }

    public String getParameter(String name) {
        return values.getParameter(prefix+name);
    }

    public void setParameter(String name, String value) {
        values.setParameter(prefix+name,value);
    }

    public void delParameter(String name) {
        values.delParameter(prefix+name);
    }
}
