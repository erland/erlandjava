package erland.util;
import java.awt.*;
import java.util.*;

/**
 * Loads images using an Component object for the loading mechanism
 * @author Erland Isaksson
 */
public class ImageHandlerForApplicationFileSystem
	implements ImageHandlerInterface
{
	/** Component object that should be used for the image loading */
	private Component component;
	/** Directory where all images are stored */
	private String imageDir;
	/** Vector with all loaded images, see {@link ImageData} */
	private LinkedList images;

	/**
	 * Contains the name of the image and the Image object
	 */
	private class ImageData {
		/** name of the image */
		public String name;
		/** Image object for the loaded image */
		public Image image;
		/** 
		 * Creates a new instance 
		 * @param name Name of the image
		 * @param image Image object of the loaded image
		 */
		public ImageData(String name, Image image)
		{
			this.name = name;
			this.image = image;
		}
	}	
	/**
     * Creates a new instance
     * @param component Applet object to use for the loading
     * @param imageDir Directory where all images are stored
     */
	public ImageHandlerForApplicationFileSystem(Component component, String imageDir)
	{
		this.component = component;
		this.imageDir = imageDir;
		this.images = new LinkedList();
	}

	public Image getImage(String imagename)
	{
		Image image = null;
		ListIterator it = images.listIterator();
		while(it.hasNext()) {
			ImageData data = (ImageData)(it.next());
			if(data != null) {
				if(data.name.equals(imagename)) {
					image = data.image;
					break;
				}
			}else {
				break;
			}
		}
		
		if(image == null) {
			String file = imageDir + "/" + imagename;

			Toolkit tk = Toolkit.getDefaultToolkit();
			image = tk.getImage(file);

			images.add(new ImageData(imagename,image));
            MediaTracker mt = new MediaTracker(component);
			mt.addImage(image,0);
			try {
				mt.waitForAll();
			}catch(InterruptedException e) {
				// Do nothing
			}

		}
		return image;
	}
}
