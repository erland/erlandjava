package erland.webapp.dirgallery.loader;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.dirgallery.DescriptionTagHelper;
import erland.webapp.dirgallery.gallery.GalleryHelper;
import erland.webapp.dirgallery.gallery.GalleryInterface;
import erland.webapp.dirgallery.gallery.picture.Picture;
import erland.webapp.dirgallery.gallery.picture.PictureComment;
import erland.webapp.dirgallery.gallery.picture.ViewPictureInterface;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoadMetadataCommand implements CommandInterface, ViewMetadataInterface, ViewPictureInterface {
    private WebAppEnvironmentInterface environment;
    private String imageFile;
    private Metadata metaData;
    private Picture picture;
    private Map metaDataMap = new HashMap();
    private Boolean showAllMetadata;
    private String comment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String image = request.getParameter("image");
        String showAllMetadataString = request.getParameter("showall");
        showAllMetadata = Boolean.FALSE;
        if (showAllMetadataString != null && showAllMetadataString.equalsIgnoreCase("true")) {
            showAllMetadata = Boolean.TRUE;
        }
        Integer gallery = getGalleryId(request);
        if (image != null && gallery != null) {
            GalleryInterface templateGallery = (GalleryInterface) environment.getEntityFactory().create("gallery");
            templateGallery.setId(gallery);
            GalleryInterface entityGallery = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(templateGallery);
            if (entityGallery != null) {
                Picture template = (Picture) environment.getEntityFactory().create("picture");
                template.setGallery(gallery);
                template.setDirectory(entityGallery.getDirectory());
                template.setId(image);
                picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(template);
                if (picture != null) {
                    imageFile = getImageFileName(picture);
                    metaData = getMetaData(imageFile);
                }
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment, request);
    }

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
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
            if (input != null) {
                return JpegMetadataReader.readMetadata(input);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getMetadataNames() {
        if (metaData != null) {
            for (Iterator it = metaData.getDirectoryIterator(); it.hasNext();) {
                Directory directory = (Directory) it.next();
                for (Iterator tagsIt = directory.getTagIterator(); tagsIt.hasNext();) {
                    Tag tag = (Tag) tagsIt.next();
                    try {
                        if (showAllMetadata.booleanValue() || DescriptionTagHelper.getInstance().getDescription("metadatafielddescription", tag.getDirectoryName() + " " + tag.getTagName()) != null) {
                            metaDataMap.put(tag.getDirectoryName() + " " + tag.getTagName(), tag.getDescription());
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
        String description = DescriptionTagHelper.getInstance().getDescription("metadatafielddescription", name);
        if (showAllMetadata.booleanValue() && description == null) {
            return name;
        }
        return description;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getComment() {
        if (picture != null && comment == null) {
            PictureComment template = (PictureComment) environment.getEntityFactory().create("picturecomment");
            template.setId(picture.getFullPath());
            PictureComment pictureComment = (PictureComment) environment.getEntityStorageFactory().getStorage("picturecomment").load(template);
            if (pictureComment != null) {
                comment = pictureComment.getComment();
            }
        }
        return comment;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}