package erland.webapp.gallery.loader;

import erland.webapp.common.*;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.*;
import java.net.URL;

public class LoadImageCommand implements CommandInterface, CommandResponseInterface{
    private WebAppEnvironmentInterface environment;
    private String imageFile;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String imageString = request.getParameter("image");
        String galleryString = request.getParameter("gallery");
        Integer image = null;
        Integer gallery = null;
        if(imageString!=null && imageString.length()>0) {
            image = Integer.valueOf(imageString);
        }
        if(galleryString!=null && galleryString.length()>0) {
            gallery = Integer.valueOf(galleryString);
        }
        if(image!=null && gallery!=null) {
            Picture template = (Picture) environment.getEntityFactory().create("picture");
            template.setGallery(gallery);
            template.setId(image);
            Picture picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(template);
            if(picture!=null) {
                String username = request.getParameter("user");
                if(username==null || username.length()==0) {
                    User user = (User) request.getSession().getAttribute("user");
                    username = user.getUsername();
                }
                QueryFilter filter = new QueryFilter("allforuser");
                filter.setAttribute("username",username);
                EntityInterface[] storageEntities = environment.getEntityStorageFactory().getStorage("picturestorage").search(filter);

                imageFile = getImageFileName(picture);
                for (int j = 0; j < storageEntities.length; j++) {
                    PictureStorage storage = (PictureStorage) storageEntities[j];
                    if(imageFile.startsWith(storage.getName())) {
                        imageFile = storage.getPath()+imageFile.substring(storage.getName().length());
                        break;
                    }
                }
            }
        }
        return null;
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        try {
            if(getImageFile()!=null) {
                BufferedInputStream input = null;
                try {
                    input = new BufferedInputStream(new FileInputStream(getImageFile()));
                } catch (FileNotFoundException e) {
                    input = new BufferedInputStream(new URL(getImageFile()).openConnection().getInputStream());
                }
                write(input,response.getOutputStream());
            }else {
                request.getRequestDispatcher("thumbnailna.gif").forward(request,response);
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1,picture.getLink().length()-1);
    }

    protected String getImageFile() {
        return imageFile;
    }

    protected void write(InputStream input,OutputStream output) throws IOException {
        byte[] data = new byte[100000];
        while(true){
            int length= input.read(data);
            if(length<0) {
                return;
            }
            output.write(data,0,length);
        }
    }
}