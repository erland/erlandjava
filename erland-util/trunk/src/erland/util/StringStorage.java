package erland.util;

import erland.util.StorageInterface;

public class StringStorage implements StorageInterface {

    protected String str;

    public StringStorage()
    {
        this.str = "";
    }
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
