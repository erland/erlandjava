package erland.webapp.gallery.loader;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.picture.ViewPictureInterface;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.gallery.GalleryHelper;
import erland.webapp.gallery.DescriptionTagHelper;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Arrays;
import java.io.*;
import java.net.URL;

import com.drew.metadata.Metadata;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.MetadataException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;

public class LoadMetadataCommand implements CommandInterface, ViewMetadataInterface, ViewPictureInterface{
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private Metadata metaData;
    private Picture picture;
    private Map metaDataMap = new HashMap();
    private Boolean showAllMetadata;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String imageString = request.getParameter("image");
        Integer image = null;
        if(imageString!=null && imageString.length()>0) {
            image = Integer.valueOf(imageString);
        }
        String showAllMetadataString = request.getParameter("showall");
        showAllMetadata = Boolean.FALSE;
        if(showAllMetadataString!=null && showAllMetadataString.equalsIgnoreCase("true")) {
            showAllMetadata = Boolean.TRUE;
        }
        Integer gallery = getGalleryId(request);
        if(image!=null && gallery!=null) {
            Picture template = (Picture) environment.getEntityFactory().create("picture");
            template.setGallery(gallery);
            template.setId(image);
            picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(template);
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
                metaData = getMetaData(imageFile);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1,picture.getLink().length()-1);
    }

    protected String getImageFile() {
        return imageFile;
    }

    private Metadata getMetaData(String filename) {
        try {
            BufferedInputStream input = null;
            try {
                input = new BufferedInputStream(new FileInputStream(filename));
            } catch (FileNotFoundException e) {
                input = new BufferedInputStream(new URL(filename).openConnection().getInputStream());
            }
            if(input!=null) {
                return JpegMetadataReader.readMetadata(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getMetadataNames() {
        if(metaData!=null) {
            for(Iterator it =  metaData.getDirectoryIterator();it.hasNext();) {
                Directory directory = (Directory) it.next();
                for(Iterator tagsIt = directory.getTagIterator();tagsIt.hasNext();) {
                    Tag tag = (Tag) tagsIt.next();
                    try {
                        if(showAllMetadata.booleanValue() || DescriptionTagHelper.getInstance().getDescription("metadatafielddescription",tag.getDirectoryName()+" "+tag.getTagName())!=null) {
                            metaDataMap.put(tag.getDirectoryName()+" "+tag.getTagName(),tag.getDescription());
                        }
                    } catch (MetadataException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        String[] result = (String[]) metaDataMap.keySet().toArray(new String[0]);
        Arrays.sort(result);
        return result;
    }

    public String getMetadataValue(String name) {
        return (String) metaDataMap.get(name);
    }

    public String getMetadataDescription(String name) {
        String description = DescriptionTagHelper.getInstance().getDescription("metadatafielddescription",name);
        if(showAllMetadata.booleanValue()) {
            return name;
        }
        return description;
    }

    public Picture getPicture() {
        return picture;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}