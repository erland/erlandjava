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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.FilteredImageSource;
import java.io.BufferedInputStream;
import java.io.IOException;
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
            BufferedImage image = ImageIO.read(new BufferedInputStream(url.openStream()));
            if (image != null) {
                ImageFilter[] filterList = filters!=null?filters.getFilters():null;
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                if(filterList!=null && filterList.length>0) {
                    ImageProducer prod = image.getSource();
                    for (int i = 0; i < filterList.length; i++) {
                        ImageFilter postFilter = filterList[i];
                        prod = new FilteredImageSource(prod,postFilter);
                    }
                    Image img = toolkit.createImage(prod);
                    image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    Graphics g = image.createGraphics();
                    g.drawImage(img, 0, 0, null);
                    g.dispose();
                }
                int width = requestedWidth;
                int height = requestedHeight;
                if(height==0) {
                    height = width * image.getHeight() / image.getWidth();
                    if (((double) image.getWidth() / image.getHeight()) < ((double) 1600 / 1200)) {
                        height = width * 1200 / 1600;
                        width = height * image.getWidth() / image.getHeight();
                    }
                }else {
                    if (((double) image.getWidth() / image.getHeight() < ((double) width/height))) {
                        width = height * image.getWidth() / image.getHeight();
                    }else {
                        height = width * image.getHeight() / image.getWidth();
                    }
                }
                if (width > image.getWidth() || height > image.getHeight()) {
                    width = image.getWidth();
                    height = image.getHeight();
                }
                thumbnail = new BufferedImage(width, height, image.getType());
                Graphics2D g2 = thumbnail.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                if(antialias.booleanValue()) {
                    Image scaled = image.getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING);
                    g2.drawImage(scaled, 0, 0, null);
                }else {
                    g2.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
                }
                g2.dispose();
            }
        }
        return thumbnail;
    }
}