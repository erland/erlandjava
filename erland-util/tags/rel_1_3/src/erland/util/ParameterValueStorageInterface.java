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
 * Get, set or delete parameters from some storage
 * @author Erland Isaksson
 */
public interface ParameterValueStorageInterface
{
	/**
	 * Get a parameter value
	 * @param name The name of the parameter
	 * @return The value of the parameter, will be an
	 *         empty string if the parameter does not exist
	 */
	public String getParameter(String name);
	
	/**
	 * Set a parameter value
	 * @param name The name of the parameter
	 * @param value The value of the parameter
	 */
	public void setParameter(String name, String value);
	
	/**
	 * Delete a parameter
	 * @param name The name of the parameter
	 */
	public void delParameter(String name);
}
