package erland.game.crackout;
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
 * Refers to a rectangular object that collisions can occur with
 */
interface CollisionRect
{
	/**
	 * Left side position of the object
	 * @return Left side position (Pixel coordinate)
	 */
	int left();

	/**
	 * Right side position of the object
	 * @return Right side position (Pixel coordinate)
	 */
	int right();

	/**
	 * Top side position of the object
	 * @return Top side position (Pixel coordinate)
	 */
	int top();

	/**
	 * Bottom side position of the object
	 * @return Bottom side position (Pixel coordinate)
	 */
	int bottom();

	/**
	 * Performs the action that should occur when something hits the object
	 * @param a Reference to action object that implements all collision actions
	 */
	void handleCollision(ActionInterface a);
}
