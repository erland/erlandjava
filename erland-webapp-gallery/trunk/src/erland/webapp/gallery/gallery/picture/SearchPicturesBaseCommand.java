package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.ViewGalleryInterface;
import erland.webapp.gallery.gallery.Gallery;
import erland.webapp.gallery.gallery.GalleryInterface;
import erland.webapp.gallery.gallery.GalleryHelper;
import erland.webapp.gallery.gallery.category.Category;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public abstract class SearchPicturesBaseCommand implements CommandInterface, ViewPicturesInterface, ViewGalleryInterface, ViewPicturePageInterface {
    private WebAppEnvironmentInterface environment;
    private Picture[] pictures;
    private Integer galleryId;
    private Integer virtualGalleryId;
    private GalleryInterface gallery;
    private Integer start;
    private Integer max;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        initCommand(request);
        galleryId = getGalleryId(request);
        start = null;
        max = null;
        if(galleryId!=null) {
            virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
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
            Collection picturesAllowed = getPictures(request);
            QueryFilter filter;
            if(start!=null && max!=null) {
                if(picturesAllowed!=null) {
                    if(categories!=null) {
                        filter = new QueryFilter(getCategoryTreeFilter()+"andpicturelist"+"withlimit");
                        filter.setAttribute("categories",categories);
                    }else {
                        filter = new QueryFilter(getAllFilter()+"andpicturelist"+"withlimit");
                    }
                    filter.setAttribute("pictures",picturesAllowed);
                }else {
                    if(categories!=null) {
                        filter = new QueryFilter(getCategoryTreeFilter()+"withlimit");
                        filter.setAttribute("categories",categories);
                    }else {
                        filter = new QueryFilter(getAllFilter()+"withlimit");
                    }
                }
                filter.setAttribute("start",start);
                filter.setAttribute("max",max);
            }else {
                if(picturesAllowed!=null) {
                    if(categories!=null) {
                        filter = new QueryFilter(getCategoryTreeFilter()+"andpicturelist");
                        filter.setAttribute("categories",categories);
                    }else {
                        filter = new QueryFilter(getAllFilter()+"andpicturelist");
                    }
                    filter.setAttribute("pictures",picturesAllowed);
                }else {
                    if(categories!=null) {
                        filter = new QueryFilter(getCategoryTreeFilter());
                        filter.setAttribute("categories",categories);
                    }else {
                        filter = new QueryFilter(getAllFilter());
                    }
                }
            }
            filter.setAttribute("gallery",galleryId);
            setFilterAttributes(request, filter);
            EntityInterface[] entities = new EntityInterface[0];
            if(picturesAllowed==null || (picturesAllowed!=null && picturesAllowed.size()>0)) {
                entities = environment.getEntityStorageFactory().getStorage("picture").search(filter);
            }

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
                pictures[i].setGallery(virtualGalleryId);
            }
            if(pictures.length>0) {
                request.setAttribute("firstimage",pictures[0].getId());
            }
        }
        return null;
    }

    protected void initCommand(HttpServletRequest request) {
        //Do nothing
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    protected void initRequestParameters(HttpServletRequest request) {
    }

    protected abstract Collection getCategories(HttpServletRequest request);

    protected abstract Collection getPictures(HttpServletRequest request);

    protected abstract String getAllFilter();

    protected abstract String getCategoryTreeFilter();

    public Picture[] getPictures() {
        return pictures;
    }

    public GalleryInterface getGallery() {
        if(galleryId!=null && gallery==null && virtualGalleryId!=null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            template.setId(virtualGalleryId);
            gallery = (GalleryInterface) environment.getEntityStorageFactory().getStorage("gallery").load(template);
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

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public Integer getGalleryId() {
        return galleryId;
    }
}