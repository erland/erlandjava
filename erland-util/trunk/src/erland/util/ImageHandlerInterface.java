package erland.util;

import java.awt.*;

/**
 * Specifies and interface for image loading
 * @author Erland Isaksson
 */
public interface ImageHandlerInterface
{
	/**
	 * Get a specific image
	 * @param image name of the image to get
	 * @return Image object for the specified image name
	 */
	public Image getImage(String image);
}
