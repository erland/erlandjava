package erland.webapp.common.image;

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

import erland.util.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.net.URL;

public class ImageThumbnail implements ThumbnailCreatorInterface {
    private static Object sync = new Object();
    private Boolean antialias;

    public ImageThumbnail() {
        antialias = Boolean.FALSE;
    }
    public ImageThumbnail(Boolean antialias) {
        this.antialias = antialias!=null?antialias:Boolean.FALSE;
    }
    public BufferedImage create(URL url, int requestedWidth, int requestedHeight, ImageFilterContainerInterface filters) throws IOException {
        BufferedImage thumbnail = null;
        synchronized (sync) {
            Log.println(this,"Opening "+url.getFile());
            BufferedImage image = ImageIO.read(new BufferedInputStream(url.openStream()));
            thumbnail = createThumbnail(image,requestedWidth,requestedHeight,filters);
        }
        if(Log.isEnabled(this,Log.DEBUG)) {
            Log.println(this,"Returning thumbnail "+thumbnail,Log.DEBUG);
        }
        return thumbnail;
    }

    protected BufferedImage createThumbnail(BufferedImage image, int requestedWidth, int requestedHeight, ImageFilterContainerInterface filters) throws IOException {
        BufferedImage thumbnail = null;
        if (image != null) {
            if(Log.isEnabled(this,Log.DEBUG)) {
                Log.println(this,"Opened image "+image,Log.DEBUG);
            }
            ImageFilter[] filterList = filters!=null?filters.getFilters():null;
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            if(filterList!=null && filterList.length>0) {
                Log.println(this,"Applying filters",Log.DEBUG);
                ImageProducer prod = image.getSource();
                for (int i = 0; i < filterList.length; i++) {
                    ImageFilter postFilter = filterList[i];
                    prod = new FilteredImageSource(prod,postFilter);
                }
                Image img = toolkit.createImage(prod);
                image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Log.println(this,"Filters applied",Log.DEBUG);
                Graphics g = image.createGraphics();
                Log.println(this,"Draw image",Log.DEBUG);
                g.drawImage(img, 0, 0, null);
                g.dispose();
            }
            int width = requestedWidth;
            int height = requestedHeight;
            int origWidth = image.getWidth();
            int origHeight = image.getHeight();
            if(height==0) {
                height = width * origHeight / origWidth;
                if (((double) origWidth / origHeight) < ((double) 1600 / 1200)) {
                    height = width * 1200 / 1600;
                    width = height * origWidth / origHeight;
                }
            }else {
                if (((double) origWidth / origHeight < ((double) width/height))) {
                    width = height * origWidth / origHeight;
                }else {
                    height = width * origHeight / origWidth;
                }
            }
            if (width > origWidth || height > origHeight) {
                width = origWidth;
                height = origHeight;
            }
            Log.println(this,"Creating BufferedImage for thumbnail",Log.DEBUG);
            // The next three rows are quite stupid, but it was the only way to get decent performance
            // on images editied in PhotoShop and saved with a ICC profile.
            BufferedImage tempImage = new BufferedImage(origWidth, origHeight, image.getType());
            //tempImage.setData(image.getRaster());
            tempImage.getGraphics().drawImage(image,0,0,null);
            image = tempImage;

            thumbnail = new BufferedImage(width, height, image.getType());
            Log.println(this,"Create graphics",Log.DEBUG);
            Graphics2D g2 = thumbnail.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            if(antialias.booleanValue()) {
                Log.println(this,"Creating scaled instance",Log.DEBUG);
                Image scaled = image.getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING);
                Log.println(this,"Drawing thumbnail",Log.DEBUG);
                g2.drawImage(scaled, 0, 0, null);
                Log.println(this,"Thumbnail drawed",Log.DEBUG);
            }else {
                Log.println(this,"Drawing thumbnail",Log.DEBUG);
                g2.drawImage(image, 0, 0, width, height, 0, 0, origWidth, origHeight, null);
                Log.println(this,"Thumbnail drawed",Log.DEBUG);
            }
            g2.dispose();
            Log.println(this,"Thumbnail successfully generated",Log.DEBUG);
        }
        return thumbnail;
    }
}