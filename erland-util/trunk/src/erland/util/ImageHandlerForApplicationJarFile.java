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
import java.util.LinkedList;
import java.util.ListIterator;
import java.net.URL;

/**
 * An implementation of {@link ImageHandlerInterface} that loads images from
 * a jar file
 * @author Erland Isaksson
 */
public class ImageHandlerForApplicationJarFile
    implements ImageHandlerInterface
{
    /** Component object that should be used for the image loading */
    private Component component;
    /** Directory where all images are stored */
    private String imageDir;
    /** Vector with all loaded images, see {@link erland.util.ImageHandlerForApplicationJarFile.ImageData} */
    private LinkedList images;
    /** The class loader object to use when loading images */
    private ClassLoader clsLoader;

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
    public ImageHandlerForApplicationJarFile(Component component, String imageDir)
    {
        this.clsLoader = this.getClass().getClassLoader();
        this.component = component;
        this.imageDir = imageDir;
        this.images = new LinkedList();
    }

    public Image getImage(String imagename)
    {
        System.out.println("getImage \""+imagename+"\"");
        Image image = null;
        ListIterator it = images.listIterator();
        while(it.hasNext()) {
            ImageHandlerForApplicationJarFile.ImageData data = (ImageHandlerForApplicationJarFile.ImageData)(it.next());
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
            URL url = clsLoader.getResource(file);
            if(url!=null) {
                image = tk.getImage(url);

                images.add(new ImageHandlerForApplicationJarFile.ImageData(imagename,image));
                MediaTracker mt = new MediaTracker(component);
                mt.addImage(image,0);
                try {
                    mt.waitForAll();
                }catch(InterruptedException e) {
                    // Do nothing
                }
            }
        }
        return image;
    }
}
