package erland.webapp.common;

import erland.webapp.common.FilterInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

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
    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append(queryName);
        sb.append("(");
        for(Iterator it=map.entrySet().iterator();it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            if(it.hasNext()) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
