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


/**
 * Defines an interface for objects that can serialize their parameters
 * to a {@link ParameterValueStorageExInterface}
 * @author Erland Isaksson
 */
public interface ParameterSerializable {
    /**
     * Write all parameters to a storage
     * @param out ParameterValueStorageExInterface object to write to
     */
    public void write(ParameterValueStorageExInterface out);

    /**
     * Read all parameters from a storage
     * @param in ParameterValueStorageExInterface to read from
     */
    public void read(ParameterValueStorageExInterface in);
}
