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

import java.awt.*;

/**
 * Defines an interface for objects implementing image creation
 * @author Erland Isaksson
 */
public interface ImageCreatorInterface {
    /**
     * Creates a new Image with the size of the screen
     * @return An Image object
     */
    public Image createImage();

    /**
     * Creates a new VolatieImage with the size of the screen
     * @return An Image object
     */
    public Image createVolatileImage();

    /**
     * Creates a new Image with the specified size
     * @param width The width of the image
     * @param height The height of the image
     * @return An Image object
     */
    public Image createImage(int width, int height);

    /**
     * Creates a new Image with the specified size and transparency
     * @param width The width of the image
     * @param height The height of the image
     * @param transparency Indicates the type of transparency
     * @return An Image object
     */
    public Image createCompatibleImage(int width, int height,int transparency);

    /**
     * Creates a new VolatileImage with the specified size
     * @param width The width of the image
     * @param height The height of the image
     * @return An Image object
     */
    public Image createVolatileImage(int width, int height);

    /**
     * Creates a new VolatileImage and copies the specified image to it
     * @param image The image to copy
     * @return An image object
     */
    public Image createVolatileImage(Image image);
}
