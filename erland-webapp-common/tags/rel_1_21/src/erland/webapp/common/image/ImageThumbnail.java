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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ImageThumbnail.class);
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
            LOG.debug("Opening "+url.getFile());
            BufferedImage image = ImageIO.read(new BufferedInputStream(url.openStream()));
            thumbnail = createThumbnail(image,requestedWidth,requestedHeight,filters);
        }
        if(LOG.isTraceEnabled()) {
            LOG.trace("Returning thumbnail "+thumbnail);
        }
        return thumbnail;
    }

    protected BufferedImage createThumbnail(BufferedImage image, int requestedWidth, int requestedHeight, ImageFilterContainerInterface filters) throws IOException {
        BufferedImage thumbnail = null;
        if (image != null) {
            if(LOG.isTraceEnabled()) {
                LOG.trace("Opened image "+image);
            }
            ImageFilter[] filterList = filters!=null?filters.getFilters():null;
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            if(filterList!=null && filterList.length>0) {
                LOG.trace("Applying filters");
                ImageProducer prod = image.getSource();
                for (int i = 0; i < filterList.length; i++) {
                    ImageFilter postFilter = filterList[i];
                    prod = new FilteredImageSource(prod,postFilter);
                }
                Image img = toolkit.createImage(prod);
                image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                LOG.trace("Filters applied");
                Graphics g = image.createGraphics();
                LOG.trace("Draw image");
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
            LOG.trace("Creating BufferedImage for thumbnail");
            // The next three rows are quite stupid, but it was the only way to get decent performance
            // on images editied in PhotoShop and saved with a ICC profile.
            BufferedImage tempImage = new BufferedImage(origWidth, origHeight, image.getType());
            //tempImage.setData(image.getRaster());
            tempImage.getGraphics().drawImage(image,0,0,null);
            image = tempImage;

            thumbnail = new BufferedImage(width, height, image.getType());
            LOG.trace("Create graphics");
            Graphics2D g2 = thumbnail.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            if(antialias.booleanValue()) {
                LOG.trace("Creating scaled instance");
                Image scaled = image.getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING);
                LOG.trace("Drawing thumbnail");
                g2.drawImage(scaled, 0, 0, null);
                LOG.trace("Thumbnail drawed");
            }else {
                LOG.trace("Drawing thumbnail");
                g2.drawImage(image, 0, 0, width, height, 0, 0, origWidth, origHeight, null);
                LOG.trace("Thumbnail drawed");
            }
            g2.dispose();
            LOG.trace("Thumbnail successfully generated");
        }
        return thumbnail;
    }
}