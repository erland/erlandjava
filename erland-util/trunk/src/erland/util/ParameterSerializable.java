package erland.util;

import erland.util.ParameterValueStorageInterface;

public interface ParameterSerializable {
    public void write(ParameterValueStorageInterface out);

    public void read(ParameterValueStorageInterface in);
}
