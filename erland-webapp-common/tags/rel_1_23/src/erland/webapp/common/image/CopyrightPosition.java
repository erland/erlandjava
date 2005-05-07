package erland.webapp.common.image;

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
 * Representation of the positioning of a copyright logotype
 */
public class CopyrightPosition {
    private CopyrightPosition() {};

    public static CopyrightPosition TOP_LEFT = new CopyrightPosition();
    public static CopyrightPosition TOP_RIGHT = new CopyrightPosition();
    public static CopyrightPosition BOTTOM_LEFT = new CopyrightPosition();
    public static CopyrightPosition BOTTOM_RIGHT = new CopyrightPosition();

    public static CopyrightPosition fromId(int id) {
        switch(id) {
            case 0:
                return TOP_LEFT;
            case 1:
                return TOP_RIGHT;
            case 2:
                return BOTTOM_LEFT;
            case 3:
            default:
                return BOTTOM_RIGHT;
        }
    }
}
