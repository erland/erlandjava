package erland.webapp.gallery.loader;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.net.URL;
import java.net.URLConnection;

public class LoadThumbnailCommand extends LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private static final int THUMBNAIL_WIDTH=150;
    private String cacheDir;

    public void init(WebAppEnvironmentInterface environment) {
        super.init(environment);
        cacheDir = environment.getResources().getParameter("thumbnail.cache");
    }

    protected String getImageFileName(Picture picture) {
        return picture.getImage().substring(1,picture.getImage().length()-1);
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        int width = THUMBNAIL_WIDTH;
        String widthString = request.getParameter("width");
        if(widthString!=null && widthString.length()>0) {
            width = Integer.valueOf(widthString).intValue();
        }
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        BufferedImage thumbnail = null;
        try {
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
            File cachedFile = getFromCache(username,width,getImageFile(),lastModified);
            if(cachedFile!=null) {
                write(new FileInputStream(cachedFile),response.getOutputStream());
            }else {
                BufferedImage image = ImageIO.read(input);

                if(image!=null) {
                    int height = width*image.getHeight()/image.getWidth();
                    thumbnail = new BufferedImage(width,height,image.getType());
                    Graphics2D g2 = thumbnail.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage( image, 0, 0, width,height,0,0,image.getWidth(),image.getHeight(),null);
                    g2.dispose();
                }

                if(thumbnail!=null) {
                    response.setContentType("image/jpeg");
                    ImageIO.write(thumbnail,"JPEG",response.getOutputStream());
                    setInCache(username,width,getImageFile(),thumbnail);
                }else {
                    request.getRequestDispatcher("thumbnailna.gif").forward(request,response);
                }
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
}