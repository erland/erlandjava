package erland.util;
import java.applet.*;
import java.awt.*;
import java.util.*;

/**
 * Loads images using an Applet object for the loading mechanism
 */
public class ImageHandlerForApplet
	implements ImageHandlerInterface
{
	/** Applet object that should be used for the image loading */
	protected Applet applet;
	/** Directory where all images are stored */
	protected String imageDir;
	/** Vector with all loaded images, see {@link ImageData} */
	protected LinkedList images;

	/**
	 * Contains the name of the image and the Image object
	 */
	class ImageData {
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
     * @param applet Applet object to use for the loading
     * @param imageDir Directory where all images are stored
     */
	public ImageHandlerForApplet(Applet applet, String imageDir)
	{
		this.applet = applet;
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
			image = applet.getImage(applet.getDocumentBase(),file);
			images.add(new ImageData(imagename,image));
			MediaTracker mt = new MediaTracker(applet);
			mt.addImage(image,1);
			try {
				mt.waitForAll();
			}catch(InterruptedException e) {
				// Do nothing
			}
		}
		return image;
	}
}
