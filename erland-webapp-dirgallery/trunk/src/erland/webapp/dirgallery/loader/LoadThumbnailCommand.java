package erland.webapp.dirgallery.loader;

import erland.util.Log;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.account.UserAccount;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;


public class LoadThumbnailCommand extends LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int COPYRIGHT_WIDTH = 640;
    private static final int COPYRIGHT_HEIGHT = 480;
    private static final float COMPRESSION = 0.9f;
    private static final float SMALL_THUMBNAIL_COMPRESSION = 0.5f;
    private String cacheDir;
    private UserAccount account;
    private final int COPYRIGHT_OFFSET = 5;

    public void init(WebAppEnvironmentInterface environment) {
        super.init(environment);
        cacheDir = environment.getResources().getParameter("thumbnail.cache");
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        int requestedWidth = THUMBNAIL_WIDTH;
        String widthString = request.getParameter("width");
        if (widthString != null && widthString.length() > 0) {
            requestedWidth = Integer.valueOf(widthString).intValue();
        }
        Boolean useCache = Boolean.TRUE;
        String useCacheString = request.getParameter("usecache");
        if (useCacheString != null && useCacheString.equalsIgnoreCase("false")) {
            useCache = Boolean.FALSE;
        }
        float requestedCompression = requestedWidth <= THUMBNAIL_WIDTH ? SMALL_THUMBNAIL_COMPRESSION : COMPRESSION;
        String compressionString = request.getParameter("compression");
        if (compressionString != null && compressionString.length() > 0) {
            requestedCompression = Float.valueOf(compressionString).floatValue();
        }
        if (requestedCompression > 1.0f) {
            requestedCompression = 1.0f;
        } else if (requestedCompression < 0.0f) {
            requestedCompression = 0.0f;
        }
        BufferedImage thumbnail = null;
        try {
            if (getImageFile() != null) {
                URL url = null;
                Log.println(this, "Loading image" + getImageFile());
                long lastModified = 0;
                File file = new File(getImageFile());
                if (file.exists()) {
                    lastModified = file.lastModified();
                    url = file.toURL();
                } else {
                    url = new URL(getImageFile());
                    URLConnection connection = url.openConnection();
                    lastModified = connection.getLastModified();
                }

                File cachedFile = null;
                if (useCache.booleanValue()) {
                    cachedFile = getFromCache(getUsername(), requestedWidth, getImageFile(), lastModified);
                }
                if (cachedFile != null) {
                    InputStream inputCache = new FileInputStream(cachedFile);
                    write(inputCache, response.getOutputStream());
                    inputCache.close();
                } else {
                    thumbnail = createThumbnail(request, url, requestedWidth, thumbnail);

                    if (thumbnail != null) {
                        response.setContentType("image/jpeg");
                        ImageOutputStream output = ImageIO.createImageOutputStream(response.getOutputStream());
                        writeImageToOutput(requestedCompression, output, thumbnail);
                        output.close();

                        if (useCache.booleanValue()) {
                            setInCache(getUsername(), requestedWidth, getImageFile(), thumbnail, requestedCompression);
                        }
                    } else {
                        request.getRequestDispatcher("thumbnailna.gif").forward(request, response);
                    }
                }
            } else {
                request.getRequestDispatcher("thumbnailna.gif").forward(request, response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BufferedImage createThumbnail(HttpServletRequest request, URL url, int requestedWidth, BufferedImage thumbnail) throws IOException {
        BufferedImage image = ImageIO.read(new BufferedInputStream(url.openStream()));
        if (image != null) {
            int width = requestedWidth;
            int height = width * image.getHeight() / image.getWidth();
            if (((double) image.getWidth() / image.getHeight()) < ((double) 1600 / 1200)) {
                height = width * 1200 / 1600;
                width = height * image.getWidth() / image.getHeight();
            }
            if (width > image.getWidth() || height > image.getHeight()) {
                width = image.getWidth();
                height = image.getHeight();
            }
            thumbnail = new BufferedImage(width, height, image.getType());
            Graphics2D g2 = thumbnail.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
            if (width >= COPYRIGHT_WIDTH || height >= COPYRIGHT_HEIGHT) {
                String copyrightText = getCopyrightText();
                if (copyrightText != null && copyrightText.length() > 0) {
                    FontMetrics metrics = g2.getFontMetrics();
                    Rectangle2D rc = metrics.getStringBounds(copyrightText, g2);
                    g2.setColor(new Color(1f, 1f, 1f, 0.4f));
                    g2.fill3DRect(width - (int) rc.getWidth() - COPYRIGHT_OFFSET * 3, height - (int) rc.getHeight() - COPYRIGHT_OFFSET * 3, (int) rc.getWidth() + COPYRIGHT_OFFSET * 2, (int) rc.getHeight() + COPYRIGHT_OFFSET * 2, true);
                    g2.setColor(Color.black);
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.drawString(copyrightText, width - (int) rc.getWidth() - COPYRIGHT_OFFSET * 2, height - COPYRIGHT_OFFSET * 2 - metrics.getDescent());
                }
            }
            g2.dispose();
        }
        return thumbnail;
    }

    private void writeImageToOutput(float requestedCompression, ImageOutputStream output, BufferedImage thumbnail) throws IOException {
        Iterator it = ImageIO.getImageWritersByFormatName("JPEG");
        ImageWriter writer = (ImageWriter) it.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(requestedCompression);
        writer.setOutput(output);
        writer.write(null, new IIOImage(thumbnail, null, null), param);
        writer.dispose();
    }

    private String getCacheFileName(String username, int width, String file) {
        return username + "_" + width + "_" + file.replaceAll("[\\\\:/]", "_");
    }

    private File getFromCache(String username, int width, String originalFile, long lastModified) {
        String cacheFileName = getCacheFileName(username, width, originalFile);
        File cachedfile = new File(cacheDir + "/" + cacheFileName);
        if (cachedfile.exists()) {
            if (cachedfile.lastModified() > lastModified) {
                return cachedfile;
            }
        }
        return null;
    }

    private void setInCache(String username, int width, String originalFile, BufferedImage image, float compression) {
        try {
            String cacheFileName = getCacheFileName(username, width, originalFile);
            ImageOutputStream output = ImageIO.createImageOutputStream(new File(cacheDir + "/" + cacheFileName));
            writeImageToOutput(compression, output, image);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserAccount getUserAccount() {
        if (account == null) {
            UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("dirgalleryuseraccount");
            template.setUsername(getUsername());
            account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("dirgalleryuseraccount").load(template);
        }
        return account;
    }

    private String getCopyrightText() {
        UserAccount account = getUserAccount();
        if (account != null) {
            return account.getCopyrightText();
        }
        return null;
    }
}