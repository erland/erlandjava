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
 * Get, set or delete parameters from some storage.
 * This interface adds the functionality to get a parameter value
 * as a {@link StorageInterface} object to the {@link ParameterValueStorageInterface} interface
 * @author Erland Isaksson
 */
public interface ParameterValueStorageExInterface extends ParameterValueStorageInterface {
    /**
     * Retreives the parameter as a StorageInterface object instead of a value
     * Observe that this is a new StorageInterface object and will not be related
     * to any of your other StorageInterface objects
     * @param name The name of the parameter to retreive
     * @return A StorageInterface object containing the value of the parameter
     */
    public StorageInterface getParameterAsStorage(String name);

    /**
     * Sets the parameter as a StorageInterface object instead of a value
     * Observe that this is a new StorageInterface object must have been retreived
     * with the {@link #getParameterAsStorage(String)} method
     * @param name The name of the parameter to set
     * @param value The value as a StorageInterface object
     */
    public void setParameterAsStorage(String name, StorageInterface value);
}
