package erland.util;

import org.apache.commons.logging.*;

import java.util.Map;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class ObjectStorageMap implements ObjectStorageInterface {
    /** Logging instance */
    private static org.apache.commons.logging.Log LOG = LogFactory.getLog(ObjectStorageMap.class);
    private static boolean bLogging = LOG.isDebugEnabled();
    private Map parameters;
    public ObjectStorageMap(Map parameters) {
        this.parameters = parameters;
    }
    public Object get(String name) {
        Object value = parameters.get(name);
        if(bLogging) LOG.debug("get: "+name+"="+value);
        return value;
    }
    public void set(String name, Object value) {
        parameters.put(name,value);
        if(bLogging) LOG.debug("set: "+name+"="+value);
    }
    public void delete(String name) {
        parameters.remove(name);
        if(bLogging) LOG.debug("remove: "+name);
    }
}