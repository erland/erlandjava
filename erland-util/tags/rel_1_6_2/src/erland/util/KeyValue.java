package erland.util;

import java.util.Map;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

/**
 * This class stores a key value pair which can be used in all sorts of data structures
 */
public class KeyValue implements Map.Entry {
    private Object key = null;
    private Object value = null;

    public KeyValue() {}
    public KeyValue(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public Object setValue(Object value) {
        this.value = value;
        return value;
    }

    public int hashCode() {
        return (getKey()==null ?
                0 :
                getKey().hashCode()) ^ (getValue()==null ?
                                            0 :
                                            getValue().hashCode());
    }

    public boolean equals(Object obj) {
        if(obj instanceof Map.Entry) {
            Map.Entry o = (Map.Entry)obj;
            return (o.getKey()==null ?
                        getKey()==null :
                        o.getKey().equals(getKey()))  &&
                   (o.getValue()==null ?
                        getValue()==null :
                        o.getValue().equals(getValue()));
        }else {
            return super.equals(obj);
        }
    }
}
