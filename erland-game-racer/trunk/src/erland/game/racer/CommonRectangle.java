package erland.game.racer;
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

import java.awt.*;

/**
 * Implements the methods of the {@link Rectangle} class that is not
 * available in MS Java 1.1, for later java versions it will just
 * call the corresponding methods in the normal {@link Rectangle} class
 */
public class CommonRectangle {
    /** Inticates if the class has been initialized */
    static boolean bInitiated = false;
    /** Indicates if system {@link Rectangle} class should be used or not */
    static boolean useSystemRectangle = true;
    /**
     * Implementation of {@link Rectangle#contains(Rectangle)}
     * Checks if the first rectangle contains the second rectangle
     * @param rc1 The first rectangle
     * @param rc2 The second rectangle
     * @return true if first rectangle contains the second rectangle
     */
    public static boolean contains(Rectangle rc1,Rectangle rc2) {
        if(!bInitiated) {
            String version = System.getProperty("java.version").substring(0,3);
            if(version.equals("1.0") || version.equals("1.1")) {
                System.out.println("Rectangle.contains not supported in version "+version+", using internal implementation");
                useSystemRectangle = false;
            }
            bInitiated = true;
        }
        if(useSystemRectangle) {
            return SunRectangle.contains(rc1,rc2);
        } else {
            return MSRectangle.contains(rc1,rc2);
        }
    }
}
