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
 * Implements a image handler which makes it easier to extract and drag sub images from
 * a large image
 * @author Erland Isaksson
 */
public class SubImageHandler {
    /** Main images which sub images are extracted from */
    private Image image;
    /** Width of sub images */
    private int width;
    /** Height of sub images */
    private int height;
    /** Number of sub images on each row */
    private int noOfX;
    /** Number of rows with sub images */
    private int noOfY;
    /** Drawing offset for x coordinate */
    private int drawOffsetX;
    /** Drawing offset for y coordinate */
    private int drawOffsetY;

    /**
     * Creates a image handler which makes it possible to extract sub images
     * from a main image
     * @param image The main image to extract sub images from
     * @param width The width of each subimage
     * @param height The height of each subimage
     * @param noOfX Number of sub images on each row
     * @param noOfY Number of rows with sub images
     */
    public SubImageHandler(Image image,int width,int height,int noOfX,int noOfY)
    {
        this(image,width,height,noOfX,noOfY,0,0);
    }

    /**
     * Creates a image handler which makes it possible to extract sub images
     * from a main image. It is also possible to select an offset which should
     * be added before drawing the image.
     * @param image The main image to extract sub images from
     * @param width The width of each subimage
     * @param height The height of each subimage
     * @param noOfX Number of sub images on each row
     * @param noOfY Number of rows with sub images
     * @param drawOffsetX Number of pixels to add to x coordinate before drawing
     * @param drawOffsetY Number of pixels to add to y coordinate before drawing
     */
    public SubImageHandler(Image image,int width,int height,int noOfX,int noOfY, int drawOffsetX, int drawOffsetY)
    {
        this.image = image;
        this.width = width;
        this.height = height;
        this.noOfX = noOfX;
        this.noOfY = noOfY;
        this.drawOffsetX = drawOffsetX;
        this.drawOffsetY = drawOffsetY;
    }
    /**
     * Draw a specific subimage
     * @param g The Graphics object to draw on
     * @param subimage The number of the sub image to draw
     * @param x The x position to draw sub image on
     * @param y The y position to draw sub image on
     */
    public void drawImage(Graphics g, int subimage,int x, int y)
    {
        x+=drawOffsetX;
        y+=drawOffsetY;
        int subImagePosX = (subimage%noOfX)*width;
        int subImagePosY = (subimage/noOfX)*height;
        g.drawImage(image,x,y,x+width,y+height,subImagePosX,subImagePosY,subImagePosX+width,subImagePosY+height,null);
    }


    /**
     * Get the main image which contains all sub images
     * @return The main image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get a Graphics object for the specified sub image
     * @param subimage The sub image to get a Graphics object for
     * @return A Graphics object for the subimage
     */
    public Graphics getGraphics(int subimage) {
        Graphics g = image.getGraphics();
        int subImagePosX = (subimage%noOfX)*width;
        int subImagePosY = (subimage/noOfX)*height;
        g.setClip(subImagePosX,subImagePosY,width,height);
        g.translate(subImagePosX-drawOffsetX,subImagePosY-drawOffsetY);
        return g;
    }
}
