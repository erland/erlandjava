package erland.webapp.gallery.act.loader;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.act.BaseTaskPlugin;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.image.*;
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.GalleryCategoryAssociation;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.entity.gallery.filter.GalleryFilter;
import erland.webapp.gallery.entity.gallery.filter.Filter;
import erland.webapp.gallery.fb.loader.ThumbnailImageFB;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.awt.image.ImageFilter;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;


public class GenerateThumbnailPlugin extends BaseTaskPlugin {
    private static final int THUMBNAIL_WIDTH = 150;
    private static GenerateThumbnailPlugin me = null;

    private WebAppEnvironmentInterface environment;
    private WebAppEnvironmentInterface getEnvironment() {
        if(environment == null) {
            environment = WebAppEnvironmentPlugin.getEnvironment();
        }
        return environment;
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        super.init(actionServlet, moduleConfig);
        me = this;
        addTask("manager",new ManagerTask());
    }

    public static GenerateThumbnailPlugin getInstance() {
        return me;
    }
    public Gallery[] getActiveThumbnailGenerationTasks() {
        Object[] tasks = getActiveTasks();
        ArrayList galleryList = new ArrayList();
        for (int i = 0; i < tasks.length; i++) {
            if(!tasks[i].equals("manager")) {
                Integer galleryId = (Integer) tasks[i];
                Gallery gallery = GalleryHelper.getGallery(getEnvironment(),galleryId);
                if(gallery!=null) {
                    galleryList.add(gallery);
                }
            }
        }
        return (Gallery[]) galleryList.toArray(new Gallery[0]);
    }

    private class ManagerTask implements Runnable {
        public void run() {
            while (true) {
                Boolean active = ServletParameterHelper.asBoolean(getEnvironment().getConfigurableResources().getParameter("backgroundthumnailgeneration"),Boolean.FALSE);
                Boolean parallell = ServletParameterHelper.asBoolean(getEnvironment().getConfigurableResources().getParameter("backgroundthumnailgenerationparallell"),Boolean.FALSE);
                if(active.booleanValue()) {
                    EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").search(new QueryFilter("allofficial"));
                    for (int i = 0; i < entities.length; i++) {
                        Gallery gallery = (Gallery) entities[i];
                        addTask(gallery.getId(),new GalleryTask(gallery),!parallell.booleanValue());
                    }
                }
                for(int i=0;i<15;i++) {
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                    }
                    Boolean activeCurrent = ServletParameterHelper.asBoolean(getEnvironment().getConfigurableResources().getParameter("backgroundthumnailgeneration"),Boolean.FALSE);
                    if(!activeCurrent.equals(active)) {
                        break;
                    }
                }
            }
        }
    }

    private class GalleryTask implements Runnable {
        private Gallery gallery;
        public GalleryTask(Gallery gallery) {
            this.gallery = gallery;
        }
        public void run() {
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username", gallery.getUsername());
            EntityInterface[] storageEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);
            Integer galleryId = gallery.getId();
            if(gallery.getReferencedGallery()!=null && gallery.getReferencedGallery().intValue()>0) {
                galleryId = gallery.getReferencedGallery();
                Collection pictures = getPictures(gallery);
                if(pictures!=null) {
                    filter = new QueryFilter("allofficialforgalleryandpicturelist");
                    filter.setAttribute("pictures",pictures);
                }else {
                    filter = new QueryFilter("allofficialforgallery");
                }
            }else {
                filter = new QueryFilter("allofficialforgallery");
            }
            filter.setAttribute("gallery",galleryId);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
            for (int i = 0; i < entities.length; i++) {
                Picture picture = (Picture) entities[i];

                Boolean active = ServletParameterHelper.asBoolean(getEnvironment().getConfigurableResources().getParameter("backgroundthumnailgeneration"),Boolean.FALSE);
                if(!active.booleanValue()) {
                    break;
                }

                String imageFile = getImageFileName(picture);
                for (int j = 0; j < storageEntities.length; j++) {
                    PictureStorage storage = (PictureStorage) storageEntities[j];
                    if (imageFile.startsWith(storage.getName())) {
                        imageFile = storage.getPath() + imageFile.substring(storage.getName().length());
                        break;
                    }
                }
                createThumbnail(gallery,picture,imageFile);
            }
        }
        protected String getImageFileName(Picture picture) {
            return picture.getImage().substring(1, picture.getImage().length() - 1);
        }
        private String getPictureInfo(String parameter, Picture picture) {
            return ServletParameterHelper.replaceDynamicParameters(parameter, new PictureParameterStorage(picture.getFile(),picture,null,new JPEGMetadataHandler(false),null));
        }
        private UserAccount getUserAccount(String username) {
            UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
            template.setUsername(username);
            UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").load(template);
            return account;
        }

        protected String getCopyrightText(String username, Gallery gallery, Picture picture) {
            String copyright = gallery.getCopyrightText();
            if(StringUtil.asNull(gallery.getCopyrightText())==null) {
                UserAccount account = getUserAccount(username);
                if (account != null) {
                    copyright = account.getCopyrightText();
                }
            }
            if(StringUtil.asNull(copyright)!=null) {
                return getPictureInfo(copyright,picture);
            }
            return null;
        }

        protected void createThumbnail(Gallery gallery, Picture picture, String imageFile) {
            Integer width = null;
            Integer height = null;
            Float compression = gallery.getThumbnailCompression();
            Boolean antialias = gallery.getThumbnailAntialias();
            ImageThumbnail thumbnailCreator = null;
            if((gallery.getUseExifThumbnails()!=null && gallery.getUseExifThumbnails().booleanValue())&& (width==null || width.floatValue()<=THUMBNAIL_WIDTH)) {
                thumbnailCreator = new MetadataImageThumbnail(antialias, gallery.getScaleExifThumbnails());
            }else {
                thumbnailCreator = new ImageThumbnail(antialias);
            }
            ImageWriteHelper.writeThumbnail(getEnvironment(), width, height,Boolean.TRUE, compression , gallery.getUsername(), imageFile, getCopyrightText(gallery.getUsername(),gallery,picture), thumbnailCreator, gallery.getId().toString(),new FilterContainer(gallery.getId(),GalleryFilter.TYPE_PREFILTER),new FilterContainer(gallery.getId(),GalleryFilter.TYPE_POSTFILTER),gallery.getCacheDate(),null);
        }
        protected Collection getPictures(Gallery gallery) {
            if (gallery.getReferencedGallery()!=null && gallery.getReferencedGallery().intValue()>0) {
                QueryFilter categoryFilter = new QueryFilter("allforgallery");
                categoryFilter.setAttribute("gallery", gallery.getId());
                EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-gallerycategoryassociation").search(categoryFilter);
                if (entities.length == 0) {
                    return null;
                }
                Collection categories = new ArrayList();
                for (int i = 0; i < entities.length; i++) {
                    GalleryCategoryAssociation entity = (GalleryCategoryAssociation) entities[i];
                    categories.add(entity.getCategory());
                }
                QueryFilter pictureFilter = new QueryFilter("allforgalleryandcategorylistallrequired");
                pictureFilter.setAttribute("gallery", gallery.getReferencedGallery());
                pictureFilter.setAttribute("categories", categories);
                pictureFilter.setAttribute("numberofcategories", new Integer(categories.size()));
                entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(pictureFilter);
                Collection pictures = new ArrayList();
                for (int i = 0; i < entities.length; i++) {
                    Picture entity = (Picture) entities[i];
                    pictures.add(entity.getId());
                }
                return pictures;
            }
            return null;
        }
    }



    private class FilterContainer implements ImageFilterContainerInterface {
        private Integer gallery;
        private Integer type;
        private ImageFilter[] filters;
        public FilterContainer(Integer gallery, Integer type) {
            this.gallery = gallery;
            this.type = type;
            this.filters = null;
        }

        public ImageFilter[] getFilters() {
            if(filters==null) {
                QueryFilter query = null;
                if(GalleryFilter.TYPE_PREFILTER.equals(type)) {
                    query = new QueryFilter("allpreforgallery");
                }else {
                    query = new QueryFilter("allpostforgallery");
                }
                query.setAttribute("gallery",gallery);
                List filterList = new ArrayList();
                EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-galleryfilter").search(query);
                for (int i = 0; i < entities.length; i++) {
                    GalleryFilter entity = (GalleryFilter) entities[i];
                    Filter filter = (Filter) getEnvironment().getEntityFactory().create("gallery-filter");
                    filter.setId(entity.getFilter());
                    filter = (Filter) getEnvironment().getEntityStorageFactory().getStorage("gallery-filter").load(filter);
                    if(filter!=null) {
                        try {
                            Class cls = Class.forName(filter.getCls());
                            Object obj = cls.newInstance();
                            if(obj instanceof ImageFilter) {
                                setAttributes((ImageFilter)obj,StringUtil.asNull(entity.getParameters())!=null?entity.getParameters():filter.getParameters());
                                filterList.add(obj);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } catch (InstantiationException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
                if(filterList.size()>0) {
                    filters =  (ImageFilter[]) filterList.toArray(new ImageFilter[0]);
                }
            }
            return filters;
        }
        private void setAttributes(ImageFilter filter, String attributeString) {
            if(attributeString!=null) {
                StringTokenizer attributes = new StringTokenizer(attributeString,",",true);
                while(attributes.hasMoreElements()) {
                    String attribute = (String) attributes.nextElement();
                    int pos = attribute.indexOf("=");
                    if(pos>0) {
                        String name = attribute.substring(0,pos);
                        String value = attribute.substring(pos+1);
                        try {
                            BeanUtils.setProperty(filter,name,value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}