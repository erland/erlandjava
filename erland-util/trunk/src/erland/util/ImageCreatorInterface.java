package erland.util;

import java.awt.*;
import java.awt.image.VolatileImage;

public interface ImageCreatorInterface {
    /**
     * Creates a new Image with the size of the screen
     * @return An Image object
     */
    Image createImage();

    /**
     * Creates a new VolatieImage with the size of the screen
     * @return An Image object
     */
    VolatileImage createVolatileImage();

    /**
     * Creates a new Image with the specified size
     * @param width The width of the image
     * @param height The height of the image
     * @return An Image object
     */
    Image createImage(int width, int height);

    /**
     * Creates a new VolatileImage with the specified size
     * @param width The width of the image
     * @param height The height of the image
     * @return An Image object
     */
    VolatileImage createVolatileImage(int width, int height);

    /**
     * Creates a new VolatileImage and copies the specified image to it
     * @param image The image to copy
     * @return An image object
     */
    VolatileImage createVolatileImage(Image image);
}
