package erland.webapp.gallery.act.gallery.importers;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.EntityStorageInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.entity.gallery.category.CategoryMembership;
import erland.webapp.gallery.entity.gallery.category.CategoryPictureAssociation;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.fb.gallery.importers.ImportFB;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IMatchImportAction extends BaseAction {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private class CategoryCache {
        private Map childs = new HashMap();
        private Integer id;
        private Boolean official;
        private Boolean officialAlways;
        private Boolean officialNever;
        private Boolean officialVisible;

        public CategoryCache(Integer id, Boolean official, Boolean officialAlways, Boolean officialNever, Boolean officialVisible) {
            this.id = id;
            this.official = official;
            this.officialAlways = officialAlways;
            this.officialNever = officialNever;
            this.officialVisible = officialVisible;
        }

        public Integer getId() {
            return id;
        }

        public Boolean getOfficial() {
            return official;
        }

        public Boolean getOfficialAlways() {
            return officialAlways;
        }

        public Boolean getOfficialNever() {
            return officialNever;
        }

        public Boolean getOfficialVisible() {
            return officialVisible;
        }

        public Map getChilds() {
            return childs;
        }
    }

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImportFB fb = (ImportFB) form;
        try {
            long orderNoStart = 0;
            if (fb.getClearCategories().booleanValue()) {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                getEnvironment().getEntityStorageFactory().getStorage("gallery-category").delete(filter);
                getEnvironment().getEntityStorageFactory().getStorage("gallery-categorymembership").delete(filter);
            }
            if (fb.getClearPictures().booleanValue()) {
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").delete(filter);
                getEnvironment().getEntityStorageFactory().getStorage("gallery-categorypictureassociation").delete(filter);
            }else {
                QueryFilter filter = new QueryFilter("allforgallerywithlimit","byordernodesc");
                filter.setAttribute("gallery",fb.getGallery());
                filter.setAttribute("start",new Integer(0));
                filter.setAttribute("max",new Integer(1));
                EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
                if(entities.length>0) {
                    Long orderNo = ((Picture)entities[0]).getOrderNo();
                    orderNoStart=orderNo!=null?orderNo.longValue()+1:0;
                }
            }
            Map categories = new HashMap();
            loadCategories(categories, fb.getGallery());
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(fb.getFile()));
            } catch (FileNotFoundException e) {
                reader = new BufferedReader(new InputStreamReader(new URL(fb.getFile()).openConnection().getInputStream()));
            }
            if (reader != null) {
                //Ignore first line
                reader.readLine();
                String line = reader.readLine();
                while (line != null) {
                    doImport(categories, fb.getGallery(), line, fb.getLocalLinks(), fb.getFilenameAsPictureTitle(), fb.getFilenameAsPictureDescription(), orderNoStart++);
                    line = reader.readLine();
                }

                QueryFilter filter = new QueryFilter("calculateofficialforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                updatePictures(filter, fb.getGallery(), Boolean.TRUE);

                filter = new QueryFilter("calculateunofficialforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                updatePictures(filter, fb.getGallery(), Boolean.FALSE);

                Gallery galleryEntity = GalleryHelper.getGallery(getEnvironment(),fb.getGallery());
                Collection categoryList = new ArrayList();
                if(galleryEntity.getOfficialCategory()!=null && galleryEntity.getOfficialCategory().intValue()!=0) {
                    categoryList.add(galleryEntity.getOfficialCategory());
                    filter = new QueryFilter("calculateallwithoutcategory");
                    filter.setAttribute("gallery",fb.getGallery());
                    filter.setAttribute("category", galleryEntity.getOfficialCategory());
                    updatePictures(filter, fb.getGallery(), Boolean.FALSE);
                }
                if(galleryEntity.getOfficialGuestCategory()!=null && galleryEntity.getOfficialGuestCategory().intValue()!=0) {
                    categoryList.add(galleryEntity.getOfficialGuestCategory());
                    filter = new QueryFilter("calculateallwithcategory");
                    filter.setAttribute("gallery",fb.getGallery());
                    filter.setAttribute("category", galleryEntity.getOfficialGuestCategory());
                    updatePictures(filter, fb.getGallery(), Boolean.FALSE);
                }

                filter = new QueryFilter("calculateofficialguestforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                updatePicturesGuest(filter, fb.getGallery(), Boolean.TRUE);

                filter = new QueryFilter("calculateunofficialguestforgallery");
                filter.setAttribute("gallery", fb.getGallery());
                updatePicturesGuest(filter, fb.getGallery(), Boolean.FALSE);

                if(galleryEntity.getOfficialGuestCategory()!=null && galleryEntity.getOfficialGuestCategory().intValue()!=0) {
                    filter = new QueryFilter("calculateallunofficialorwithoutcategory");
                    filter.setAttribute("gallery",fb.getGallery());
                    filter.setAttribute("category", galleryEntity.getOfficialGuestCategory());
                    updatePicturesGuest(filter, fb.getGallery(), Boolean.FALSE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doImport(Map previousCategories, Integer gallery, String line, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, long orderNoStart) {
        StringTokenizer tokenizer = new StringTokenizer(line, "\t", true);
        if (tokenizer.countTokens() >= 8) {
            String picture = tokenizer.nextToken();
            //Discard delimiter
            tokenizer.nextToken();
            String dateString = tokenizer.nextToken();
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Discard delimiter
            tokenizer.nextToken();
            String oidString = tokenizer.nextToken();
            Integer oid = null;
            if (oidString != null && oidString.length() > 0) {
                oid = Integer.valueOf(oidString);
            }
            String categories = "\"\"";
            String title = null;
            String description = null;
            try {
                //Discard delimiter
                tokenizer.nextToken();
                categories = tokenizer.nextToken();
                if (categories.equalsIgnoreCase("\t")) {
                    categories = "\"\"";
                } else {
                    //Discard delimiter
                    tokenizer.nextToken();
                }
                title = tokenizer.nextToken();
                if (title.equalsIgnoreCase("\t")) {
                    title = null;
                } else {
                    //Discard delimiter
                    tokenizer.nextToken();
                }
                if (title != null && title.length() > 0) {
                    title = title.substring(1, title.length() - 1);
                }
                description = tokenizer.nextToken();
                if (description.equalsIgnoreCase("\t")) {
                    description = null;
                }
                if (description != null && description.length() > 0) {
                    description = description.substring(1, description.length() - 1);
                }
            } catch (NoSuchElementException e) {
                // Do nothing
            }
            Integer pictureId = createPicture(gallery, picture.substring(1, picture.length() - 1), date, oid, title, description, localLinks, filenameAsPictureTitle, filenameAsPictureDescription, new Long(orderNoStart));
            for (StringTokenizer it = new StringTokenizer(categories.substring(1, categories.length() - 1), ","); it.hasMoreTokens();) {
                String category = it.nextToken();
                Integer categoryId = createCategory(previousCategories, gallery, category);
                createPictureAssociation(gallery, pictureId, categoryId);
            }
        }
    }

    private Integer createPicture(Integer gallery, String picture, Date modificationDate, Integer id, String title, String description, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Long orderNo) {
        picture = picture.replace('\\',File.separatorChar);
        QueryFilter filter = new QueryFilter("ingallerywithname");
        filter.setAttribute("gallery", gallery);
        filter.setAttribute("link", "{" + picture + "}");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        if (entities.length > 0) {
            return ((Picture) entities[0]).getId();
        } else {
            Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
            if ((title == null || title.length() == 0) && filenameAsPictureTitle.booleanValue()) {
                title = picture;
            }
            entity.setTitle(title);
            if ((description == null || description.length() == 0) && filenameAsPictureDescription != null && filenameAsPictureDescription.booleanValue()) {
                description = picture;
            }
            entity.setDescription(description);
            if (localLinks.booleanValue()) {
                entity.setLink(picture);
                entity.setImage(picture);
            } else {
                entity.setLink("{" + picture + "}");
                entity.setImage("{" + picture + "}");
            }
            entity.setGallery(gallery);
            entity.setDate(modificationDate);
            entity.setId(id);
            entity.setOrderNo(orderNo);
            getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").store(entity);
            return entity.getId();
        }
    }


    private Integer createCategory(Map previousCategories, Integer gallery, String categoryPath) {
        StringTokenizer it = new StringTokenizer(categoryPath, ".");
        EntityStorageInterface storage = getEnvironment().getEntityStorageFactory().getStorage("gallery-category");
        CategoryCache parent = null;
        Map current = previousCategories;
        while (it.hasMoreTokens()) {
            String category = it.nextToken();
            CategoryCache obj = (CategoryCache) current.get(category);
            if (obj == null) {
                Category entity = (Category) getEnvironment().getEntityFactory().create("gallery-category");
                entity.setGallery(gallery);
                entity.setName(category);
                entity.setParentCategory(parent!=null?parent.getId():null);
                entity.setOfficial(parent!=null?parent.getOfficial():Boolean.FALSE);
                entity.setOfficialVisible(parent!=null?parent.getOfficialVisible():Boolean.TRUE);
                entity.setOfficialAlways(parent!=null?parent.getOfficialAlways():Boolean.FALSE);
                entity.setOfficialNever(parent!=null?parent.getOfficialNever():Boolean.FALSE);
                storage.store(entity);
                obj = new CategoryCache(entity.getCategory(),entity.getOfficial(),entity.getOfficialAlways(),entity.getOfficialNever(),entity.getOfficialVisible());
                current.put(category, obj);
                if (parent != null) {
                    updateMembership(gallery, parent.getId(), entity.getCategory());
                }
            }
            parent = obj;
            current = obj.getChilds();
        }
        return parent!=null?parent.getId():null;
    }

    private void loadCategories(Map categories, Integer gallery) {
        QueryFilter filter = new QueryFilter("allforgallery");
        filter.setAttribute("gallery", gallery);
        categories.clear();
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-category").search(filter);
        for (int i = 0; i < entities.length; i++) {
            Category category = (Category) entities[i];
            addCategory(categories, category);
        }
    }

    private boolean addCategory(Map categories, Category category) {
        if (category.getParentCategory().equals(new Integer(0))) {
            categories.put(category.getName(), new CategoryCache(category.getCategory(),category.getOfficial(),category.getOfficialAlways(),category.getOfficialNever(), category.getOfficialVisible()));
            return true;
        } else {
            for (Iterator it = categories.values().iterator(); it.hasNext();) {
                CategoryCache categoryCache = (CategoryCache) it.next();
                if (categoryCache.getId().equals(category.getParentCategory())) {
                    categoryCache.getChilds().put(category.getName(), new CategoryCache(category.getCategory(),category.getOfficial(),category.getOfficialAlways(),category.getOfficialNever(),category.getOfficialVisible()));
                    return true;
                } else {
                    if (addCategory(categoryCache.getChilds(), category)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createPictureAssociation(Integer gallery, Integer picture, Integer category) {
        CategoryPictureAssociation entity = (CategoryPictureAssociation) getEnvironment().getEntityFactory().create("gallery-categorypictureassociation");
        entity.setGallery(gallery);
        entity.setPicture(picture);
        entity.setCategory(category);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-categorypictureassociation").store(entity);
    }

    private void updateMembership(Integer gallery, Integer parent, Integer category) {
        CategoryMembership template = (CategoryMembership) getEnvironment().getEntityFactory().create("gallery-categorymembership");
        template.setGallery(gallery);
        template.setCategory(parent);
        template.setMemberCategory(category);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-categorymembership").store(template);

        if (!parent.equals(new Integer(0))) {
            Category templateCategory = (Category) getEnvironment().getEntityFactory().create("gallery-category");
            templateCategory.setGallery(gallery);
            templateCategory.setCategory(parent);
            Category entity = (Category) getEnvironment().getEntityStorageFactory().getStorage("gallery-category").load(templateCategory);
            if (entity != null && !entity.getParentCategory().equals(new Integer(0))) {
                updateMembership(gallery, entity.getParentCategory(), category);
            }
        }
    }

    private void updatePictures(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        entity.setOfficial(official);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(entities, gallery, entity);
    }

    private void updatePicturesGuest(QueryFilter filter, Integer gallery, Boolean official) {
        Picture entity = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        entity.setOfficialGuest(official);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        updatePictures(entities, gallery, entity);
    }

    private void updatePictures(EntityInterface[] entities, Integer gallery, Picture picture) {
        Collection pictures = new ArrayList(entities.length);
        for (int i = 0; i < entities.length; i++) {
            pictures.add(((Picture) entities[i]).getId());
        }
        if (pictures.size() > 0) {
            QueryFilter pictureFilter = new QueryFilter("allforgalleryandpicturelist");
            pictureFilter.setAttribute("gallery", gallery);
            pictureFilter.setAttribute("pictures", pictures);
            getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").update(pictureFilter, picture);
        }
    }
}