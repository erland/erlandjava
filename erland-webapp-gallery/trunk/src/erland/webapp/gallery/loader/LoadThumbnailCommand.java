package erland.webapp.gallery.loader;
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

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.account.UserAccount;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;


public class LoadThumbnailCommand extends LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private static final int THUMBNAIL_WIDTH=150;
    private static final int COPYRIGHT_WIDTH=640;
    private static final int COPYRIGHT_HEIGHT=480;
    private static final float COMPRESSION=0.9f;
    private static final float SMALL_THUMBNAIL_COMPRESSION=0.5f;
    private String cacheDir;
    private UserAccount account;
    private final int COPYRIGHT_OFFSET = 5;

    public void init(WebAppEnvironmentInterface environment) {
        super.init(environment);
        cacheDir = environment.getConfigurableResources().getParameter("thumbnail.cache");
    }

    protected String getImageFileName(Picture picture) {
        return picture.getImage().substring(1,picture.getImage().length()-1);
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        int requestedWidth = THUMBNAIL_WIDTH;
        String widthString = request.getParameter("width");
        if(widthString!=null && widthString.length()>0) {
            requestedWidth = Integer.valueOf(widthString).intValue();
        }
        Boolean useCache = Boolean.TRUE;
        String useCacheString = request.getParameter("usecache");
        if(useCacheString!=null && useCacheString.equalsIgnoreCase("false")) {
            useCache = Boolean.FALSE;
        }
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        float requestedCompression = requestedWidth<=THUMBNAIL_WIDTH?SMALL_THUMBNAIL_COMPRESSION:COMPRESSION;
        String compressionString = request.getParameter("compression");
        if(compressionString!=null && compressionString.length()>0) {
            requestedCompression = Float.valueOf(compressionString).floatValue();
        }
        if(requestedCompression>1.0f) {
            requestedCompression = 1.0f;
        }else if(requestedCompression<0.0f) {
            requestedCompression = 0.0f;
        }
        BufferedImage thumbnail = null;
        try {
            if(getImageFile()!=null) {
                BufferedInputStream input = null;
                long lastModified = 0;
                try {
                    File file = new File(getImageFile());
                    if(file.exists()) {
                        lastModified = file.lastModified();
                    }
                    input = new BufferedInputStream(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    URLConnection connection = new URL(getImageFile()).openConnection();
                    lastModified = connection.getLastModified();
                    input = new BufferedInputStream(connection.getInputStream());
                }

                File cachedFile = null;
                if(useCache.booleanValue()) {
                    cachedFile = getFromCache(username,requestedWidth,getImageFile(),lastModified);
                }
                if(cachedFile!=null) {
                    write(new FileInputStream(cachedFile),response.getOutputStream());
                }else {
                    BufferedImage image = ImageIO.read(input);
                    if(image!=null) {
                        int width = requestedWidth;
                        int height = width*image.getHeight()/image.getWidth();
                        if(((double)image.getWidth()/image.getHeight())<((double)1600/1200)) {
                            height = width*1200/1600;
                            width = height*image.getWidth()/image.getHeight();
                        }
                        thumbnail = new BufferedImage(width,height,image.getType());
                        Graphics2D g2 = thumbnail.createGraphics();
                        g2.drawImage( image, 0, 0, width,height,0,0,image.getWidth(),image.getHeight(),null);
                        if(width>=COPYRIGHT_WIDTH|| height>=COPYRIGHT_HEIGHT) {
                            String copyrightText = getCopyrightText();
                            if(copyrightText!=null && copyrightText.length()>0) {
                                FontMetrics metrics = g2.getFontMetrics();
                                Rectangle2D rc = metrics.getStringBounds(copyrightText,g2);
                                g2.setColor(new Color(1f,1f,1f,0.4f));
                                g2.fill3DRect(width-(int)rc.getWidth()-COPYRIGHT_OFFSET*3,height-(int)rc.getHeight()-COPYRIGHT_OFFSET*3,(int)rc.getWidth()+COPYRIGHT_OFFSET*2,(int)rc.getHeight()+COPYRIGHT_OFFSET*2,true);
                                g2.setColor(Color.black);
                                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                                g2.drawString(copyrightText,width-(int)rc.getWidth()-COPYRIGHT_OFFSET*2,height-COPYRIGHT_OFFSET*2-metrics.getDescent());
                            }
                        }
                        g2.dispose();
                    }

                    if(thumbnail!=null) {
                        response.setContentType("image/jpeg");
                        Iterator it = ImageIO.getImageWritersByFormatName("JPEG");
                        ImageWriter writer = (ImageWriter) it.next();
                        ImageWriteParam param = writer.getDefaultWriteParam();
                        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        param.setCompressionQuality(requestedCompression);

                        writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
                        writer.write(null,new IIOImage(thumbnail,null,null),param);
                        writer.dispose();

                        if(useCache.booleanValue()) {
                            setInCache(username,requestedWidth,getImageFile(),thumbnail);
                        }
                    }else {
                        request.getRequestDispatcher("thumbnailna.gif").forward(request,response);
                    }
                }
            }else {
                request.getRequestDispatcher("thumbnailna.gif").forward(request,response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCacheFileName(String username, int width, String file) {
        return username+"_"+width+"_"+file.replaceAll("[\\\\:/]","_");
    }
    private File getFromCache(String username, int width, String originalFile, long lastModified) {
        String cacheFileName = getCacheFileName(username,width,originalFile);
        File cachedfile = new File(cacheDir+"/"+cacheFileName);
        if(cachedfile.exists()) {
            if(cachedfile.lastModified()>lastModified) {
                return cachedfile;
            }
        }
        return null;
    }
    private void setInCache(String username, int width, String originalFile, BufferedImage image) {
        try {
            String cacheFileName = getCacheFileName(username,width, originalFile);
            ImageIO.write(image,"JPEG",new File(cacheDir+"/"+cacheFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private UserAccount getUserAccount() {
        if(account==null) {
            UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("galleryuseraccount");
            template.setUsername(getUsername());
            account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("galleryuseraccount").load(template);
        }
        return account;
    }

    private String getCopyrightText() {
        UserAccount account = getUserAccount();
        if(account!=null) {
            return account.getCopyrightText();
        }
        return null;
    }
}