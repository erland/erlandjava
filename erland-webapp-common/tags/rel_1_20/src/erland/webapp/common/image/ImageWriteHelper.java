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

import erland.webapp.common.WebAppEnvironmentInterface;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageWriteHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ImageWriteHelper.class);
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int COPYRIGHT_WIDTH = 250;
    private static final int COPYRIGHT_HEIGHT = COPYRIGHT_WIDTH*3/4;
    private static final float COMPRESSION = 0.9f;
    private static final float SMALL_THUMBNAIL_COMPRESSION = 0.5f;
    private static final int COPYRIGHT_OFFSET = 5;
    private static ImageWriteHelper me;

    private ImageWriteHelper() {};

    public static boolean writeImage(WebAppEnvironmentInterface environment,String file, OutputStream output) {
        try {
            if (file != null) {
                BufferedInputStream input = null;
                LOG.debug( "Loading image: " + file);
                 try {
                    input = new BufferedInputStream(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    input = new BufferedInputStream(new URL(file).openConnection().getInputStream());
                }
                write(input, output);
                input.close();
                return true;
            } else {

            }
        } catch (IOException e) {
            LOG.error("Unable to write image "+file,e);
        }
        return false;
    }


    public static boolean writeThumbnail(WebAppEnvironmentInterface environment,Integer width, Boolean useCache, Float compression, String username, String imageFile, String copyrightText, ThumbnailCreatorInterface thumbnailCreator, OutputStream output) {
        return writeThumbnail(environment,width,null,useCache,compression,username,imageFile,copyrightText,thumbnailCreator,null,null,null,null,output);
    }

    public static boolean writeThumbnail(WebAppEnvironmentInterface environment,Integer width, Integer height, Boolean useCache, Float compression, String username, String imageFile, String copyrightText, ThumbnailCreatorInterface thumbnailCreator, OutputStream output) {
        return writeThumbnail(environment,width,height,useCache,compression,username,imageFile,copyrightText,thumbnailCreator,null,null,null,null,output);
    }
    public static boolean writeThumbnail(WebAppEnvironmentInterface environment,Integer width, Boolean useCache, Float compression, String username, String imageFile, String copyrightText, ThumbnailCreatorInterface thumbnailCreator, String cachePrefix, ImageFilterContainerInterface preFilters, ImageFilterContainerInterface postFilters, Date cacheDate, OutputStream output) {
        return writeThumbnail(environment,width,null,useCache,compression,username,imageFile,copyrightText,thumbnailCreator,null,null,null,null,output);
    }
    public static boolean writeThumbnail(WebAppEnvironmentInterface environment,Integer width, Integer height, Boolean useCache, Float compression, String username, String imageFile, String copyrightText, ThumbnailCreatorInterface thumbnailCreator, String cachePrefix, ImageFilterContainerInterface preFilters, ImageFilterContainerInterface postFilters, Date cacheDate, OutputStream output) {
        LOG.debug( "Loading thumbnail image: " + imageFile);
        String cacheDir = environment.getConfigurableResources().getParameter("thumbnail.cache");
        int requestedWidth = width!=null?width.intValue():THUMBNAIL_WIDTH;
        int requestedHeight = height!=null?height.intValue():0;
        float requestedCompression = compression!=null?compression.floatValue():0f;
        if (requestedCompression == 0) {
            requestedCompression = requestedWidth <= THUMBNAIL_WIDTH ? SMALL_THUMBNAIL_COMPRESSION : COMPRESSION;
        }
        if (requestedCompression > 1.0f) {
            requestedCompression = 1.0f;
        } else if (requestedCompression < 0.0f) {
            requestedCompression = 0.0f;
        }
        BufferedImage thumbnail = null;
        try {
            if (imageFile != null) {
                URL url = null;
                long lastModified = 0;
                File file = new File(imageFile);
                if (file.exists()) {
                    lastModified = file.lastModified();
                    url = file.toURL();
                } else {
                    url = new URL(imageFile);
                    URLConnection connection = url.openConnection();
                    lastModified = connection.getLastModified();
                }

                File cachedFile = null;
                if (useCache.booleanValue()) {
                    cachedFile = getFromCache(cacheDir, username, cachePrefix, requestedWidth, imageFile, lastModified);
                }
                if (cachedFile != null && (cacheDate==null||cacheDate.getTime()<cachedFile.lastModified())) {
                    InputStream inputCache = new FileInputStream(cachedFile);
                    if(output!=null) {
                        write(inputCache, output);
                    }
                    inputCache.close();
                    return true;
                } else {
                    thumbnail = thumbnailCreator.create(url, requestedWidth, requestedHeight, preFilters);
                    if (thumbnail != null) {
                        ImageFilter[] filters = postFilters!=null?postFilters.getFilters():null;
                        if(filters!=null && filters.length>0) {
                            LOG.trace("Applying filters");
                            ImageProducer prod = thumbnail.getSource();
                            for (int i = 0; i < filters.length; i++) {
                                ImageFilter postFilter = filters[i];
                                prod = new FilteredImageSource(prod,postFilter);
                            }
                            Image img = Toolkit.getDefaultToolkit().createImage(prod);
                            thumbnail = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                            Graphics g = thumbnail.createGraphics();
                            g.drawImage(img, 0, 0, null);
                            g.dispose();
                            LOG.trace("Filters applied");
                        }
                        Graphics2D g2= thumbnail.createGraphics();
                        int finalWidth = thumbnail.getWidth();
                        int finalHeight = thumbnail.getHeight();
                        if (finalWidth >= COPYRIGHT_WIDTH || finalHeight>= COPYRIGHT_HEIGHT) {
                            if (copyrightText != null && copyrightText.length() > 0) {
                                LOG.trace("Drawing copyright");
                                FontMetrics metrics = g2.getFontMetrics();
                                Rectangle2D rc = metrics.getStringBounds(copyrightText, g2);
                                g2.setColor(new Color(1f, 1f, 1f, 0.4f));
                                g2.fill3DRect(finalWidth - (int) rc.getWidth() - COPYRIGHT_OFFSET * 3, finalHeight - (int) rc.getHeight() - COPYRIGHT_OFFSET * 3, (int) rc.getWidth() + COPYRIGHT_OFFSET * 2, (int) rc.getHeight() + COPYRIGHT_OFFSET * 2, true);
                                g2.setColor(Color.black);
                                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                                g2.drawString(copyrightText, finalWidth - (int) rc.getWidth() - COPYRIGHT_OFFSET * 2, finalHeight - COPYRIGHT_OFFSET * 2 - metrics.getDescent());
                                LOG.trace("Copyright drawed");
                            }
                        }
                    }

                    if (thumbnail != null) {
                        if(output!=null) {
                            LOG.trace("Create thumbnail jpeg");
                            ImageOutputStream imageOutput = ImageIO.createImageOutputStream(output);
                            LOG.trace("Write thumbnail to response");
                            writeImageToOutput(thumbnail, requestedCompression, imageOutput);
                            LOG.trace("Thumbnail written to response");
                            imageOutput.close();
                        }

                        if (useCache.booleanValue()) {
                            setInCache(cacheDir,username, cachePrefix, requestedWidth, imageFile, thumbnail, requestedCompression);
                        }
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Unable to create thumbnail for image: "+imageFile,e);
        }
        return false;
    }


    private static void write(InputStream input, OutputStream output) throws IOException {
        byte[] data = new byte[100000];
        while (true) {
            int length = input.read(data);
            if (length < 0) {
                return;
            }
            output.write(data, 0, length);
        }
    }
    public static void writeImageToOutput(BufferedImage image, OutputStream output) throws IOException {
        float compression = image.getWidth() <= THUMBNAIL_WIDTH ? SMALL_THUMBNAIL_COMPRESSION : COMPRESSION;
        writeImageToOutput(image,compression,output);
    }

    public static void writeImageToOutput(BufferedImage image, float compression, OutputStream output) throws IOException {
        ImageOutputStream imageOutput = ImageIO.createImageOutputStream(output);
        writeImageToOutput(image,compression,imageOutput);
    }
    private static void writeImageToOutput(BufferedImage image, float requestedCompression, ImageOutputStream output) throws IOException {
        Iterator it = ImageIO.getImageWritersByFormatName("JPEG");
        ImageWriter writer = (ImageWriter) it.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(requestedCompression);
        writer.setOutput(output);
        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
    }

    private static String getCacheFileName(String username, String cachePrefix, int width, String file) {
        return username + "_" + (cachePrefix!=null?cachePrefix + "_":"") + width + "_" + file.replaceAll("[\\\\:/]", "_");
    }

    private static File getFromCache(String cacheDir,String username, String cachePrefix, int width, String originalFile, long lastModified) {
        String cacheFileName = getCacheFileName(username, cachePrefix, width, originalFile);
        LOG.debug( "Loading thumbnail from cache: " + cacheDir+"/"+cacheFileName);
        File cachedfile = new File(cacheDir + "/" + cacheFileName);
        if (cachedfile.exists()) {
            if (cachedfile.lastModified() > lastModified) {
                return cachedfile;
            }
        }
        return null;
    }

    private static void setInCache(String cacheDir,String username, String cachePrefix, int width, String originalFile, BufferedImage image, float compression) {
        String cacheFileName = getCacheFileName(username, cachePrefix, width, originalFile);
        try {
            ImageOutputStream output = ImageIO.createImageOutputStream(new File(cacheDir + "/" + cacheFileName));
            writeImageToOutput(image, compression, output);
            output.close();
        } catch (IOException e) {
            LOG.error("Unable to write cache image: "+cacheFileName,e);
        }
    }
    private static ImageWriteHelper getLogInstance() {
        if(me==null) {
            me = new ImageWriteHelper();
        }
        return me;
    }
}