package erland.util;

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
    /** Heigth of sub images */
    private int height;
    /** Number of sub images on each row */
    private int noOfX;
    /** Number of rows with sub images */
    private int noOfY;

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
        this.image = image;
        this.width = width;
        this.height = height;
        this.noOfX = noOfX;
        this.noOfY = noOfY;
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
        int subImagePosX = (subimage%noOfX)*width;
        int subImagePosY = (subimage/noOfX)*height;
        g.drawImage(image,x,y,x+width,y+height,subImagePosX,subImagePosY,subImagePosX+width,subImagePosY+height,null);
    }
}
