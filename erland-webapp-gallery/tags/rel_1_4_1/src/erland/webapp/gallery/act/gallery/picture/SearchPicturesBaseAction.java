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
import erland.webapp.gallery.entity.gallery.picture.Resolution;
import erland.webapp.gallery.fb.gallery.picture.PictureCollectionPB;
import erland.webapp.gallery.fb.gallery.picture.PicturePB;
import erland.webapp.gallery.fb.gallery.picture.SelectPictureFB;
import erland.webapp.gallery.fb.gallery.picture.ResolutionPB;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.util.Log;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public abstract class SearchPicturesBaseAction extends BaseAction {
    private final static String GALLERY_ID = SearchPicturesBaseAction.class + "-galleryId";
    private final static String VIRTUAL_GALLERY_ID = SearchPicturesBaseAction.class + "-virtualGalleryId";
    private final static String CATEGORY_ID = SearchPicturesBaseAction.class + "-categoryId";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectPictureFB fb = (SelectPictureFB) form;
        Integer virtualGalleryId = fb.getGallery();
        setVirtualGalleryId(request,virtualGalleryId);
        GalleryInterface gallery = GalleryHelper.getGallery(getEnvironment(), virtualGalleryId);
        if(gallery==null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.gallery-not-found"}));
            return;
        }
        Integer galleryId = GalleryHelper.getGalleryId(gallery);
        setGalleryId(request,galleryId);
        Integer categoryId = fb.getCategory();
        setCategoryId(request,categoryId);
        Log.println(this,"Search pictures: "+fb.getGallery()+","+fb.getCategory());
        initRequestParameters(request);
        Collection categories = getCategories(request, form);
        Collection picturesAllowed = getPictures(request, form);
        QueryFilter filter;
        if (fb.getStart() != null && fb.getMax() != null) {
            if (picturesAllowed != null) {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter(request) + "andpicturelist" + "withlimit");
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter(request) + "andpicturelist" + "withlimit");
                }
                filter.setAttribute("pictures", picturesAllowed);
            } else {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter(request) + "withlimit");
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter(request) + "withlimit");
                }
            }
            filter.setAttribute("start", fb.getStart());
            filter.setAttribute("max", fb.getMax());
        } else {
            if (picturesAllowed != null) {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter(request) + "andpicturelist");
                   filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter(request) + "andpicturelist");
                }
                filter.setAttribute("pictures", picturesAllowed);
            } else {
                if (categories != null) {
                    filter = new QueryFilter(getCategoryTreeFilter(request));
                    filter.setAttribute("categories", categories);
                } else {
                    filter = new QueryFilter(getAllFilter(request));
                }
            }
        }
        filter.setAttribute("gallery", galleryId);
        setFilterAttributes(request,form, filter);
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

        if(gallery.getMaxWidth()!=null && gallery.getMaxWidth().intValue()>0) {
            filter = new QueryFilter("all-smaller-than");
            filter.setAttribute("width",gallery.getMaxWidth());
        }else {
            filter = new QueryFilter("all");
        }
        EntityInterface[] resolutionEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-resolution").search(filter);
        ResolutionPB[] resolutionsPB = null;
        Integer defaultResolution = null;
        if(resolutionEntities.length>0) {
            for (int i = 0; i < resolutionEntities.length; i++) {
                if(((Resolution)resolutionEntities[i]).getId().equalsIgnoreCase(gallery.getDefaultResolution())) {
                    defaultResolution=((Resolution)resolutionEntities[i]).getWidth();
                    break;
                }
            }
            resolutionsPB = new ResolutionPB[resolutionEntities.length];
            for (int i = 0; i < resolutionsPB.length; i++) {
                resolutionsPB[i] = new ResolutionPB();
                try {
                    PropertyUtils.copyProperties(resolutionsPB[i],resolutionEntities[i]);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                } catch (NoSuchMethodException e) {
                }
            }
        }

        Map pictureParameters = new HashMap();
        PicturePB[] picturesPB = new PicturePB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            picturesPB[i] = new PicturePB();
            PropertyUtils.copyProperties(picturesPB[i], entities[i]);
            pictureParameters.put("gallery",virtualGalleryId);
            pictureParameters.put("picture",picturesPB[i].getId());
            if(defaultResolution!=null) {
                pictureParameters.put("width",defaultResolution.toString());
            }
            if (picturesPB[i].getImage().startsWith("{")) {
                String path = mapping.findForward("picture-image").getPath();
                picturesPB[i].setImage(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
            } else {
                picturesPB[i].setImage(getImagePath(storageEntities, picturesPB[i].getImage()));
            }
            if (gallery.getUsername().equals(request.getRemoteUser())) {
                ActionForward forward = mapping.findForward("picture-update-link");
                if(forward!=null) {
                    String path = forward.getPath();
                    picturesPB[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
                }
                forward = mapping.findForward("picture-remove-link");
                if(forward!=null) {
                    String path = forward.getPath();
                    picturesPB[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
                }
            }else {
                ActionForward forward = mapping.findForward("picture-resolution-link");
                if(forward!=null) {
                    picturesPB[i].setResolutionLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),pictureParameters));
                    picturesPB[i].setResolutions(resolutionsPB);
                }
            }
            if (picturesPB[i].getLink().startsWith("{")) {
                String path = null;
                if(defaultResolution!=null) {
                    ActionForward forward = mapping.findForward("picture-resolution-link-complete");
                    if(forward!=null) {
                        path = forward.getPath();
                    }
                }
                if(path==null) {
                    path = mapping.findForward("picture-link").getPath();
                }
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

    protected abstract Collection getCategories(HttpServletRequest request, ActionForm form);

    protected abstract Collection getPictures(HttpServletRequest request, ActionForm form);

    protected abstract String getAllFilter(HttpServletRequest request);

    protected abstract String getCategoryTreeFilter(HttpServletRequest request);

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

    protected void setFilterAttributes(HttpServletRequest request, ActionForm form, QueryFilter filter) {
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

    protected Integer getGalleryId(HttpServletRequest request) {
        return (Integer) request.getAttribute(GALLERY_ID);
    }
    protected void setGalleryId(HttpServletRequest request, Integer galleryId) {
        request.setAttribute(GALLERY_ID,galleryId);
    }
    protected Integer getVirtualGalleryId(HttpServletRequest request) {
        return (Integer) request.getAttribute(VIRTUAL_GALLERY_ID);
    }
    protected void setVirtualGalleryId(HttpServletRequest request, Integer virtualGalleryId) {
        request.setAttribute(VIRTUAL_GALLERY_ID,virtualGalleryId);
    }
    protected Integer getCategoryId(HttpServletRequest request) {
        return (Integer) request.getAttribute(CATEGORY_ID);
    }
    protected void setCategoryId(HttpServletRequest request, Integer categoryId) {
        request.setAttribute(CATEGORY_ID,categoryId);
    }
    private void addParameters(Map map, SelectPictureFB fb) {
        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(fb);
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor property = properties[i];
            try {
                map.put(property.getName(),PropertyUtils.getProperty(fb,property.getName()));
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }
    }
}