package erland.webapp.dirgallery.loader;

import erland.util.Log;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.gallery.GalleryHelper;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.dirgallery.gallery.picture.Picture;
import erland.webapp.usermgmt.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

public class LoadImageCommand implements CommandInterface, CommandResponseInterface {
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private String username;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String image = request.getParameter("image");
        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            GalleryInterface templateGallery = (GalleryInterface) environment.getEntityFactory().create("gallery");
            templateGallery.setId(gallery);
            GalleryInterface entity = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(templateGallery);
            if (entity == null || (getClass().equals(LoadImageCommand.class) && !entity.getOriginalDownloadable().booleanValue())) {
                return null;
            }
            username = entity.getUsername();
            Picture template = (Picture) environment.getEntityFactory().create("picture");
            template.setGallery(gallery);
            template.setDirectory(entity.getDirectory());
            template.setId(image);
            Picture picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(template);
            if (picture != null) {
                if (!entity.getOfficial().booleanValue()) {
                    User realUser = (User) request.getSession().getAttribute("user");
                    if (realUser != null && !username.equals(entity.getUsername())) {
                        return null;
                    } else if (realUser == null) {
                        return null;
                    }
                }

                imageFile = getImageFileName(picture);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (getImageFile() != null) {
                BufferedInputStream input = null;
                Log.println(this, "Loading image" + getImageFile());
                try {
                    input = new BufferedInputStream(new FileInputStream(getImageFile()));
                } catch (FileNotFoundException e) {
                    input = new BufferedInputStream(new URL(getImageFile()).openConnection().getInputStream());
                }
                write(input, response.getOutputStream());
                input.close();
            } else {
                request.getRequestDispatcher("thumbnailna.gif").forward(request, response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
    }

    protected String getImageFile() {
        return imageFile;
    }

    protected void write(InputStream input, OutputStream output) throws IOException {
        byte[] data = new byte[100000];
        while (true) {
            int length = input.read(data);
            if (length < 0) {
                return;
            }
            output.write(data, 0, length);
        }
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public String getUsername() {
        return username;
    }
}