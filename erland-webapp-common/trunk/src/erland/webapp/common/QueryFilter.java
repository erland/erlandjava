package erland.webapp.common;

import erland.webapp.common.FilterInterface;

import java.util.HashMap;
import java.util.Map;

public class QueryFilter implements FilterInterface {
    private String queryName;
    private Map map = new HashMap();

    public QueryFilter(String queryName) {
        this.queryName = queryName;
    }
    public String getQueryName() {
        return queryName;
    }
    public Object getAttribute(String attr) {
        return map.get(attr);
    }
    public void setAttribute(String attr, Object value) {
        map.put(attr,value);
    }
}
