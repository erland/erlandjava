package erland.webapp.gallery.act.loader;

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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.image.*;
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.filter.GalleryFilter;
import erland.webapp.gallery.entity.gallery.filter.Filter;
import erland.webapp.gallery.fb.loader.ThumbnailImageFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.awt.image.ImageFilter;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.reflect.InvocationTargetException;


public class LoadThumbnailAction extends LoadImageAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(LoadThumbnailAction.class);
    private static final int THUMBNAIL_WIDTH = 150;

    protected String getImageFileName(Picture picture) {
        return picture.getImage().substring(1, picture.getImage().length() - 1);
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ThumbnailImageFB fb = (ThumbnailImageFB) form;
        Integer width = fb.getWidth();
        Integer height = fb.getHeight();
        Gallery gallery = getGallery(request);
        if(getGallery(request).getMaxWidth()!=null && getGallery(request).getMaxWidth().intValue()>0 && width!=null && getGallery(request).getMaxWidth().compareTo(width)<0) {

           width = gallery.getMaxWidth();
        }
        Float compression = fb.getCompression();
        if(compression==null || compression.floatValue()==0.0f) {
            if(width==null || width.floatValue()<=THUMBNAIL_WIDTH) {
                compression = gallery.getThumbnailCompression();
            }else {
                compression = gallery.getCompression();
            }
        }
        Boolean antialias = null;
        if(width==null || width.floatValue()<=THUMBNAIL_WIDTH) {
            antialias = gallery.getThumbnailAntialias();
        }else {
            antialias = gallery.getAntialias();
        }
        Boolean useCache = fb.getUseCache();
        if(useCache==null) {
            if(width==null||width.floatValue()<=THUMBNAIL_WIDTH) {
                useCache=Boolean.TRUE;
            }else {
                useCache=gallery.getUseCacheLargeImages();
            }
        }
        ImageThumbnail thumbnailCreator = null;
        if((gallery.getUseExifThumbnails()!=null && gallery.getUseExifThumbnails().booleanValue())&& (width==null || width.floatValue()<=THUMBNAIL_WIDTH)) {
            thumbnailCreator = new MetadataImageThumbnail(antialias, gallery.getScaleExifThumbnails());
        }else {
            thumbnailCreator = new ImageThumbnail(antialias);
        }
        try {
            response.setContentType("image/jpeg");
            if (!ImageWriteHelper.writeThumbnail(getEnvironment(), width, height,useCache, compression , getUsername(request), getImageFile(request), getCopyrightText(getUsername(request),getGallery(request),getPicture(request)), thumbnailCreator, gallery.getId().toString(),new FilterContainer(gallery.getId(),GalleryFilter.TYPE_PREFILTER),new FilterContainer(gallery.getId(),GalleryFilter.TYPE_POSTFILTER),gallery.getCacheDate(),response.getOutputStream())) {
                return findFailure(mapping,form,request,response);
            }
        } catch (IOException e) {
            LOG.error("Unable to read file "+getImageFile(request),e);
        }
        return null;
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

    private String getPictureInfo(String parameter, Picture picture) {
        return ServletParameterHelper.replaceDynamicParameters(parameter, new PictureParameterStorage(picture.getFile(),picture,null,new JPEGMetadataHandler(false),null));
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
                            LOG.error("Unable to create filter class "+filter.getCls(),e);
                        } catch (InstantiationException e) {
                            LOG.error("Unable to create filter class "+filter.getCls(),e);
                        } catch (IllegalAccessException e) {
                            LOG.error("Unable to create filter class "+filter.getCls(),e);
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
                            LOG.error("Unable to set property "+name,e);
                        } catch (InvocationTargetException e) {
                            LOG.error("Unable to set property "+name,e);
                        }
                    }
                }
            }
        }
    }
}