package erland.util;
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
