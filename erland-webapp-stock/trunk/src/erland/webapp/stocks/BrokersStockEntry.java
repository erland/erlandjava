package erland.webapp.stocks;

public class BrokersStockEntry {
    private String name;
    private String code;
    public BrokersStockEntry(String code,String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
