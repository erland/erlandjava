package erland.util;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

public class ObjectStorageParameterStorage implements ObjectStorageInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ObjectStorageParameterStorage.class);
    private static boolean bLogging = LOG.isDebugEnabled();
    
    private ParameterValueStorageInterface parameterStorage;
    public ObjectStorageParameterStorage(ParameterValueStorageInterface parameterStorage) {
        this.parameterStorage = parameterStorage;
    }
    public Object get(String name) {
        Object value = parameterStorage.getParameter(name);
        if(bLogging) LOG.debug("get: "+name+"="+value);
        return value;
    }

    public void set(String name, Object value) {
        parameterStorage.setParameter(name,value!=null?value.toString():"");
        if(bLogging) LOG.debug("set: "+name+"="+value);
    }

    public void delete(String name) {
        parameterStorage.delParameter(name);
        if(bLogging) LOG.debug("delete: "+name);
    }
}