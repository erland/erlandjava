package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.ViewGalleryInterface;
import erland.webapp.gallery.gallery.Gallery;
import erland.webapp.gallery.gallery.category.Category;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public abstract class SearchPicturesBaseCommand implements CommandInterface, ViewPicturesInterface, ViewGalleryInterface, ViewPicturePageInterface {
    protected WebAppEnvironmentInterface environment;
    private Picture[] pictures;
    protected Integer galleryId;
    private Gallery gallery;
    private Integer start;
    private Integer max;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        start = null;
        max = null;
        if(galleryString!=null && galleryString.length()>0) {
            galleryId = Integer.valueOf(galleryString);
            String startString = request.getParameter("start");
            if(startString!=null && startString.length()>0) {
                start = Integer.valueOf(startString);
            }
            String maxString = request.getParameter("max");
            if(maxString!=null && maxString.length()>0) {
                max = Integer.valueOf(maxString);
            }
            initRequestParameters(request);
            Collection categories = getCategories(request);
            QueryFilter filter;
            if(start!=null && max!=null) {
                if(categories!=null) {
                    filter = new QueryFilter(getCategoryTreeFilter()+"withlimit");
                    filter.setAttribute("categories",categories);
                }else {
                    filter = new QueryFilter(getAllFilter()+"withlimit");
                }
                filter.setAttribute("start",start);
                filter.setAttribute("max",max);
            }else {
                if(categories!=null) {
                    filter = new QueryFilter(getCategoryTreeFilter());
                    filter.setAttribute("categories",categories);
                }else {
                    filter = new QueryFilter(getAllFilter());
                }
            }
            filter.setAttribute("gallery",galleryId);
            setFilterAttributes(request, filter);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("picture").search(filter);

            String username = request.getParameter("user");
            if(username==null || username.length()==0) {
                User user = (User) request.getSession().getAttribute("user");
                username = user.getUsername();
            }
            filter = new QueryFilter("allforuser");
            filter.setAttribute("username",username);
            EntityInterface[] storageEntities = environment.getEntityStorageFactory().getStorage("picturestorage").search(filter);

            pictures = new Picture[entities.length];
            for (int i = 0; i < entities.length; i++) {
                pictures[i] = (Picture) entities[i];
                if(pictures[i].getImage().startsWith("{")) {
                    pictures[i].setImage(null);
                }else {
                    pictures[i].setImage(getImagePath(storageEntities,pictures[i].getImage()));
                }
                if(pictures[i].getLink().startsWith("{")) {
                    pictures[i].setLink(null);
                }else {
                    pictures[i].setLink(getImagePath(storageEntities,pictures[i].getLink()));
                }
            }
            if(pictures.length>0) {
                request.setAttribute("firstimage",pictures[0].getId());
            }
        }
        return null;
    }

    protected void initRequestParameters(HttpServletRequest request) {
    }

    protected abstract Collection getCategories(HttpServletRequest request);

    protected abstract String getAllFilter();

    protected abstract String getCategoryTreeFilter();

    public Picture[] getPictures() {
        return pictures;
    }

    public Gallery getGallery() {
        if(galleryId!=null && gallery==null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            template.setId(galleryId);
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
        }
        return gallery;
    }

    public Integer getPreviousPage() {
        if(max!=null && start!=null) {
            if(start.intValue()>0) {
                if(start.intValue()-max.intValue()<0) {
                    return new Integer(0);
                }else {
                    return new Integer(start.intValue()-max.intValue());
                }
            }
        }
        return null;
    }

    public Integer getNextPage() {
        if(max!=null && start!=null) {
            if(max.intValue()==getPictures().length) {
                return new Integer(start.intValue()+max.intValue());
            }
        }
        return null;
    }

    private String getImagePath(EntityInterface[] storageEntities, String originalImagePath) {
        if(originalImagePath!=null) {
            for (int j = 0; j < storageEntities.length; j++) {
                PictureStorage storage = (PictureStorage) storageEntities[j];
                if(originalImagePath.startsWith(storage.getName())) {
                    originalImagePath = storage.getPath()+originalImagePath.substring(storage.getName().length());
                    break;
                }
            }
        }
        return originalImagePath;
    }

    protected void setFilterAttributes(HttpServletRequest request, QueryFilter filter) {
        //Nothing, may be overridden in sub classes
    }
    protected Category[] getCategoryTree(Integer gallery, Integer category) {
        QueryFilter filter = new QueryFilter("allforgalleryandcategorytree");
        filter.setAttribute("gallery",gallery);
        filter.setAttribute("category",category);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("category").search(filter);
        Category[] categories = new Category[entities.length];
        for (int i = 0; i < entities.length; i++) {
            categories[i] = (Category) entities[i];
        }
        return categories;
    }
}