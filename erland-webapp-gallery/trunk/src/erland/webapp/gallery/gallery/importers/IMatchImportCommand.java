package erland.webapp.gallery.gallery.importers;

import erland.webapp.common.*;
import erland.webapp.gallery.gallery.picture.Picture;
import erland.webapp.gallery.gallery.category.Category;
import erland.webapp.gallery.gallery.category.CategoryPictureAssociation;
import erland.webapp.gallery.gallery.category.CategoryMembership;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.net.URL;

public class IMatchImportCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;
    private Map categories = new HashMap();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private class CategoryCache {
        private Map childs = new HashMap();
        private Integer id;
        public CategoryCache(Integer id) {
            this.id = id;
        }
        public Integer getId() {
            return id;
        }
        public Map getChilds() {
            return childs;
        }
    }
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        String file = request.getParameter("file");
        Boolean clearCategories = asBoolean(request.getParameter("clearcategories"));
        Boolean clearPictures = asBoolean(request.getParameter("clearpictures"));
        Boolean localLinks = asBoolean(request.getParameter("locallinks"));
        Boolean filenameAsPictureTitle = asBoolean(request.getParameter("filenameaspicturetitle"));
        Boolean filenameAsPictureDescription = asBoolean(request.getParameter("filenameaspicturedescription"));
        Boolean cutLongPictureTitles = asBoolean(request.getParameter("cutlongpicturetitles"));
        Integer gallery = Integer.valueOf(galleryString);
        try {
            if(gallery!=null && file!=null) {
                if(clearCategories.booleanValue()) {
                    QueryFilter filter = new QueryFilter("allforgallery");
                    filter.setAttribute("gallery",gallery);
                    environment.getEntityStorageFactory().getStorage("category").delete(filter);
                    environment.getEntityStorageFactory().getStorage("categorymembership").delete(filter);
                }
                if(clearPictures.booleanValue()) {
                    QueryFilter filter = new QueryFilter("allforgallery");
                    filter.setAttribute("gallery",gallery);
                    environment.getEntityStorageFactory().getStorage("picture").delete(filter);
                    environment.getEntityStorageFactory().getStorage("categorypictureassociation").delete(filter);
                }
                loadCategories(categories,gallery);
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    reader = new BufferedReader(new InputStreamReader(new URL(file).openConnection().getInputStream()));
                }
                if(reader!=null) {
                    //Ignore first line
                    reader.readLine();
                    String line = reader.readLine();
                    while(line!=null) {
                        doImport(gallery, line, localLinks,filenameAsPictureTitle,filenameAsPictureDescription,cutLongPictureTitles);
                        line = reader.readLine();
                    }

                    QueryFilter filter = new QueryFilter("calculateofficialforgallery");
                    filter.setAttribute("gallery",gallery);
                    updatePictures(filter,gallery, Boolean.TRUE);

                    filter = new QueryFilter("calculateunofficialforgallery");
                    filter.setAttribute("gallery",gallery);
                    updatePictures(filter,gallery, Boolean.FALSE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean asBoolean(String parameter) {
        if(parameter!=null && parameter.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }
    private void doImport(Integer gallery, String line, Boolean localLinks,Boolean filenameAsPictureTitle,Boolean filenameAsPictureDescription, Boolean cutLongPictureTitles) {
        StringTokenizer tokenizer = new StringTokenizer(line,"\t");
        if(tokenizer.countTokens()==4) {
            String picture = tokenizer.nextToken();
            String dateString = tokenizer.nextToken();
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String oidString = tokenizer.nextToken();
            Integer oid = null;
            if(oidString!=null&&oidString.length()>0) {
                oid = Integer.valueOf(oidString);
            }
            Integer pictureId = createPicture(gallery,picture.substring(1,picture.length()-1),date,oid,localLinks,filenameAsPictureTitle,filenameAsPictureDescription,cutLongPictureTitles);
            String categories = tokenizer.nextToken();
            for(StringTokenizer it = new StringTokenizer(categories.substring(1,categories.length()-1),",");it.hasMoreTokens();) {
                String category = it.nextToken();
                Integer categoryId = createCategory(gallery,category);
                createPictureAssociation(gallery,pictureId,categoryId);
            }
        }
    }
    private Integer createPicture(Integer gallery, String picture, Date modificationDate, Integer id, Boolean localLinks,Boolean filenameAsPictureTitle,Boolean filenameAsPictureDescription, Boolean cutLongPictureTitles) {
        QueryFilter filter = new QueryFilter("ingallerywithname");
        filter.setAttribute("gallery",gallery);
        filter.setAttribute("link","{"+picture+"}");
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("picture").search(filter);
        if(entities.length>0) {
            return ((Picture)entities[0]).getId();
        }else {
            Picture entity = (Picture) environment.getEntityFactory().create("picture");
            if(filenameAsPictureTitle.booleanValue()) {
                if(cutLongPictureTitles.booleanValue()) {
                    String title = null;
                    if(picture.length()>30) {
                        title = "..."+picture.substring(picture.length()-27);
                    }else {
                        title = picture;
                    }
                    entity.setTitle(title);
                }else {
                    entity.setTitle(picture);
                }
            }
            if(filenameAsPictureDescription!=null) {
                entity.setDescription(picture);
            }
            if(localLinks.booleanValue()) {
                entity.setLink(picture);
                entity.setImage(picture);
            }else {
                entity.setLink("{"+picture+"}");
                entity.setImage("{"+picture+"}");
            }
            entity.setGallery(gallery);
            entity.setDate(modificationDate);
            entity.setId(id);
            environment.getEntityStorageFactory().getStorage("picture").store(entity);
            return entity.getId();
        }
    }


    private Integer createCategory(Integer gallery, String categoryPath) {
        StringTokenizer it = new StringTokenizer(categoryPath,".");
        EntityStorageInterface storage = environment.getEntityStorageFactory().getStorage("category");
        Integer parent = null;
        Map current = categories;
        while(it.hasMoreTokens()) {
            String category = it.nextToken();
            CategoryCache obj =(CategoryCache) current.get(category);
            if(obj==null) {
                Category entity = (Category) environment.getEntityFactory().create("category");
                entity.setGallery(gallery);
                entity.setName(category);
                entity.setParentCategory(parent);
                entity.setOfficial(Boolean.TRUE);
                entity.setOfficialVisible(Boolean.TRUE);
                entity.setOfficialAlways(Boolean.FALSE);
                storage.store(entity);
                obj = new CategoryCache(entity.getCategory());
                current.put(category,obj);
                if(parent!=null) {
                    updateMembership(gallery,parent,entity.getCategory());
                }
            }
            parent = obj.getId();
            current = obj.getChilds();
        }
        return parent;
    }
    private void loadCategories(Map categories, Integer gallery) {
        QueryFilter filter = new QueryFilter("allforgallery");
        filter.setAttribute("gallery",gallery);
        categories.clear();
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("category").search(filter);
        for (int i = 0; i < entities.length; i++) {
            Category category = (Category) entities[i];
            addCategory(categories, category.getName(),category.getCategory(),category.getParentCategory());
        }
    }
    private boolean addCategory(Map categories, String name, Integer id, Integer parent) {
        if(parent.equals(new Integer(0))) {
            categories.put(name,new CategoryCache(id));
            return true;
        }else {
            for(Iterator it=categories.values().iterator();it.hasNext();) {
                CategoryCache category = (CategoryCache) it.next();
                if(category.getId().equals(parent)) {
                    category.getChilds().put(name,new CategoryCache(id));
                    return true;
                }else {
                    if(addCategory(category.getChilds(),name,id,parent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void createPictureAssociation(Integer gallery, Integer picture, Integer category) {
        CategoryPictureAssociation entity = (CategoryPictureAssociation) environment.getEntityFactory().create("categorypictureassociation");
        entity.setGallery(gallery);
        entity.setPicture(picture);
        entity.setCategory(category);
        environment.getEntityStorageFactory().getStorage("categorypictureassociation").store(entity);
    }

    private void updateMembership(Integer gallery, Integer parent, Integer category) {
        CategoryMembership template = (CategoryMembership) environment.getEntityFactory().create("categorymembership");
        template.setGallery(gallery);
        template.setCategory(parent);
        template.setMemberCategory(category);
        environment.getEntityStorageFactory().getStorage("categorymembership").store(template);

        if(!parent.equals(new Integer(0))) {
            Category templateCategory = (Category) environment.getEntityFactory().create("category");
            templateCategory.setGallery(gallery);
            templateCategory.setCategory(parent);
            Category entity = (Category) environment.getEntityStorageFactory().getStorage("category").load(templateCategory);
            if(entity!=null && !entity.getParentCategory().equals(new Integer(0))) {
                updateMembership(gallery,entity.getParentCategory(),category);
            }
        }
    }

    private void updatePictures(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) environment.getEntityFactory().create("picture");
        entity.setOfficial(official);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("picture").search(filter);
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture) entities[i]).getId());
        }
        QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
        pictureFilter.setAttribute("gallery", gallery);
        pictureFilter.setAttribute("pictures", pictures);
        environment.getEntityStorageFactory().getStorage("picture").update(pictureFilter, entity);
    }
}