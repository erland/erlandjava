package erland.webapp.gallery.act.gallery.picture;

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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.category.Category;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.fb.gallery.picture.PictureCollectionPB;
import erland.webapp.gallery.fb.gallery.picture.PicturePB;
import erland.webapp.gallery.fb.gallery.picture.SelectPictureFB;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.util.Log;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public abstract class SearchPicturesBaseAction extends BaseAction {

    private Integer galleryId;
    private Integer virtualGalleryId;
    private Integer categoryId;

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectPictureFB fb = (SelectPictureFB) form;
        virtualGalleryId = fb.getGallery();
        GalleryInterface gallery = GalleryHelper.getGallery(getEnvironment(), virtualGalleryId);
        galleryId = GalleryHelper.getGalleryId(gallery);
        categoryId = fb.getCategory();
        Log.println(this,"Search pictures: "+fb.getGallery()+","+fb.getCategory());
        initRequestParameters(request);
        Collection categories = getCategories(form);
        Collection picturesAllowed = getPictures(form);
        QueryFilter filter;
        if (fb.getStart() != null && fb.getMax() != null) {
            if (picturesAllowed != null) {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter() + "andpicturelist" + "withlimit");
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter() + "andpicturelist" + "withlimit");
                }
                filter.setAttribute("pictures", picturesAllowed);
            } else {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter() + "withlimit");
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter() + "withlimit");
                }
            }
            filter.setAttribute("start", fb.getStart());
            filter.setAttribute("max", fb.getMax());
        } else {
            if (picturesAllowed != null) {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter() + "andpicturelist");
                   filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter() + "andpicturelist");
                }
                filter.setAttribute("pictures", picturesAllowed);
            } else {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter());
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter());
                }
            }
        }
        filter.setAttribute("gallery", galleryId);
        setFilterAttributes(form, filter);
        EntityInterface[] entities = new EntityInterface[0];
        if (picturesAllowed == null || (picturesAllowed != null && picturesAllowed.size() > 0)) {
            entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").search(filter);
        }

        String username = fb.getUser();
        if (username == null || username.length() == 0) {
            username = request.getRemoteUser();
        }
        filter = new QueryFilter("allforuser");
        filter.setAttribute("username", username);
        EntityInterface[] storageEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);

        Map pictureParameters = new HashMap();
        PicturePB[] picturesPB = new PicturePB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            picturesPB[i] = new PicturePB();
            PropertyUtils.copyProperties(picturesPB[i], entities[i]);
            pictureParameters.put("gallery",picturesPB[i].getGallery());
            pictureParameters.put("picture",picturesPB[i].getId());
            if (picturesPB[i].getImage().startsWith("{")) {
                String path = mapping.findForward("picture-image").getPath();
                picturesPB[i].setImage(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
            } else {
                picturesPB[i].setImage(getImagePath(storageEntities, picturesPB[i].getImage()));
            }
            if (picturesPB[i].getLink().startsWith("{")) {
                String path = mapping.findForward("picture-link").getPath();
                picturesPB[i].setLink(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
            } else {
                picturesPB[i].setLink(getImagePath(storageEntities, picturesPB[i].getLink()));
            }
            picturesPB[i].setGallery(virtualGalleryId);
        }
        PictureCollectionPB pb = new PictureCollectionPB();
        pb.setPictures(picturesPB);

        Map parameters = new HashMap();
        addParameters(parameters,fb);
        Integer prevStart = getPreviousPage(fb.getStart(), fb.getMax());
        if(prevStart!=null) {
            parameters.put("start",prevStart.toString());
            pb.setPrevLink(ServletParameterHelper.replaceDynamicParameters(mapping.findForward("previous-link").getPath(),parameters));
        }else {
            pb.setPrevLink(null);
        }
        Integer nextStart = getNextPage(fb.getStart(),fb.getMax(),picturesPB.length);
        if(nextStart!=null) {
            parameters.put("start",nextStart.toString());
            pb.setNextLink(ServletParameterHelper.replaceDynamicParameters(mapping.findForward("next-link").getPath(),parameters));
        }else {
            pb.setNextLink(null);
        }
        parameters.put("start","0");
        pb.setSearchLink(ServletParameterHelper.replaceDynamicParameters(mapping.findForward("search-link").getPath(),parameters));
        request.setAttribute("picturesPB", pb);

        GalleryPB galleryPB = new GalleryPB();
        PropertyUtils.copyProperties(galleryPB,gallery);
        galleryPB.setVirtual(Boolean.valueOf(!virtualGalleryId.equals(galleryId)));
        request.setAttribute("galleryPB",galleryPB);
    }

    protected void initRequestParameters(HttpServletRequest request) {
    }

    protected abstract Collection getCategories(ActionForm form);

    protected abstract Collection getPictures(ActionForm form);

    protected abstract String getAllFilter();

    protected abstract String getCategoryTreeFilter();

    private Integer getPreviousPage(Integer start, Integer max) {
        if (max != null && start != null) {
            if (start.intValue() > 0) {
                if (start.intValue() - max.intValue() < 0) {
                    return new Integer(0);
                } else {
                    return new Integer(start.intValue() - max.intValue());
                }
            }
        }
        return null;
    }

    private Integer getNextPage(Integer start, Integer max, int noOfPictures) {
        if (max != null && start != null) {
            if (max.intValue() == noOfPictures) {
                return new Integer(start.intValue() + max.intValue());
            }
        }
        return null;
    }

    private String getImagePath(EntityInterface[] storageEntities, String originalImagePath) {
        if (originalImagePath != null) {
            for (int j = 0; j < storageEntities.length; j++) {
                PictureStorage storage = (PictureStorage) storageEntities[j];
                if (originalImagePath.startsWith(storage.getName())) {
                    originalImagePath = storage.getPath() + originalImagePath.substring(storage.getName().length());
                    break;
                }
            }
        }
        return originalImagePath;
    }

    protected void setFilterAttributes(ActionForm form, QueryFilter filter) {
        //Nothing, may be overridden in sub classes
    }

    protected Category[] getCategoryTree(Integer gallery, Integer category) {
        QueryFilter filter = new QueryFilter("allforgalleryandcategorytree");
        filter.setAttribute("gallery", gallery);
        filter.setAttribute("category", category);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-category").search(filter);
        Category[] categories = new Category[entities.length];
        for (int i = 0; i < entities.length; i++) {
            categories[i] = (Category) entities[i];
        }
        return categories;
    }

    protected Integer getGalleryId() {
        return galleryId;
    }
    protected Integer getVirtualGalleryId() {
        return virtualGalleryId;
    }
    protected Integer getCategoryId() {
        return categoryId;
    }
    private void addParameters(Map map, SelectPictureFB fb) {
        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(fb);
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor property = properties[i];
            try {
                map.put(property.getName(),PropertyUtils.getProperty(fb,property.getName()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
    }
}