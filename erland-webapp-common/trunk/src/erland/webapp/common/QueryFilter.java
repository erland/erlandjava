package erland.webapp.common;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
