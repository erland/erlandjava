package erland.util;

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
