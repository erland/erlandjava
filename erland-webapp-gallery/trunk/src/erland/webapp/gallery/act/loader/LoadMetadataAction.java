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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.common.image.MetadataHandlerInterface;
import erland.webapp.common.image.FileMetadataHandler;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.act.gallery.picture.SearchPicturesBaseAction;
import erland.webapp.gallery.act.skin.SkinHelper;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.picture.Resolution;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.fb.loader.MetadataImageFB;
import erland.webapp.gallery.fb.loader.MetadataPB;
import erland.webapp.gallery.fb.loader.MetadataCollectionPB;
import erland.webapp.gallery.fb.gallery.picture.ResolutionPB;
import erland.webapp.gallery.fb.gallery.picture.PicturePB;
import erland.webapp.gallery.fb.skin.SkinFB;
import erland.util.StringUtil;
import erland.util.ObjectStorageInterface;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

public class LoadMetadataAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MetadataImageFB fb = (MetadataImageFB) form;
        Gallery gallery = GalleryHelper.getGallery(getEnvironment(),fb.getGallery());
        Integer galleryId = GalleryHelper.getGalleryId(gallery);
        Picture template = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        template.setGallery(galleryId);
        template.setId(fb.getImage());
        Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").load(template);
        if (picture == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.loader.picture-dont-exist"}));
            return;
        }
        if (gallery == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.loader.gallery-dont-exist"}));
            return;
        }
        String username = gallery.getUsername();
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", username);
        EntityInterface[] storageEntities = getEnvironment().getEntityStorageFactory().getStorage("gallery-picturestorage").search(filter);

        String imageFile = getImageFileName(picture);
        for (int j = 0; j < storageEntities.length; j++) {
            PictureStorage storage = (PictureStorage) storageEntities[j];
            if (imageFile.startsWith(storage.getName())) {
                imageFile = storage.getPath() + imageFile.substring(storage.getName().length());
                break;
            }
        }
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        MetadataHandlerInterface handler = new JPEGMetadataHandler(!fb.getShowAll().booleanValue(),request.getLocale().getLanguage());
        handler.load(imageFile);
        String[] names = handler.getNames();
        MetadataPB[] metadata = new MetadataPB[names.length];
        for (int i = 0; i < metadata.length; i++) {
            metadata[i] = new MetadataPB();
            metadata[i].setId(names[i]);
            metadata[i].setDescription(handler.getDescription(names[i]));
            metadata[i].setValue(handler.getValue(names[i]));
        }
        MetadataCollectionPB pb = new MetadataCollectionPB();
        pb.setItems(metadata);
        pb.setShowAll(fb.getShowAll());

        Map parameters = new HashMap();
        parameters.put("gallery",fb.getGallery());
        parameters.put("picture",fb.getImage());
        if(fb.getWidth()!=null) {
            parameters.put("width",fb.getWidth());
        }
        if(StringUtil.asNull(fb.getSkin())!=null) {
            parameters.put("skin",fb.getSkin());
        }
        ActionForward forward = mapping.findForward("show-all-metadata-link");
        if(forward!=null) {
            pb.setShowAllLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("show-selected-metadata-link");
        if(forward!=null) {
            pb.setShowSelectedLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        request.setAttribute("metadataPB", pb);

        Map pictureParameters = new HashMap();
        PicturePB picturePB = new PicturePB();
        PropertyUtils.copyProperties(picturePB, picture);
        if(useEnglish && StringUtil.asNull(picture.getTitleEnglish())!=null) {
            picturePB.setTitle(picture.getTitleEnglish());
        }
        if(useEnglish && StringUtil.asNull(picture.getDescriptionEnglish())!=null) {
            picturePB.setDescription(picture.getDescriptionEnglish());
        }
        pictureParameters.put("gallery",fb.getGallery());
        pictureParameters.put("picture",picture.getId());
        if(fb.getWidth()!=null) {
            pictureParameters.put("width",fb.getWidth());
        }

        if (picturePB.getImage().startsWith("{")) {
            String path = mapping.findForward("picture-image").getPath();
            picturePB.setImage(ServletParameterHelper.replaceDynamicParameters(path,pictureParameters));
        } else {
            picturePB.setImage(imageFile);
        }
        picturePB.setGallery(fb.getGallery());
        if(picturePB.getTitle()!=null && gallery.getUseShortPictureNames()!=null && gallery.getUseShortPictureNames().booleanValue()) {
            picturePB.setTitle(picture.getShortTitle());
        }
        if(StringUtil.asNull(gallery.getPictureTitle())!=null) {
            picturePB.setTitle(getPictureInfo(gallery.getPictureTitle(),picture,picturePB,new JPEGMetadataHandler(false),new FileMetadataHandler(false)));
        }
        if(gallery.getShowPictureTitle()!=null && !gallery.getShowPictureTitle().booleanValue()) {
            picturePB.setTitle(null);
        }
        if(picturePB.getTitle()!=null && gallery.getCutLongPictureTitles()!=null && gallery.getCutLongPictureTitles().booleanValue()) {
            if (picturePB.getTitle() != null && picturePB.getTitle().length() > 30) {
                picturePB.setTitle("..." + picturePB.getTitle().substring(picturePB.getTitle().length() - 27));
            }
        }
        request.setAttribute("picturePB",picturePB);

        String skin = gallery.getSkin();
        if(StringUtil.asNull(fb.getSkin())!=null) {
            skin = fb.getSkin();
        }
        if(mapping.getParameter()!=null && mapping.getParameter().equals("useskin")) {
            SkinFB previous = (SkinFB) request.getSession().getAttribute("skinPB");
            if(StringUtil.asNull(skin)==null) {
                UserAccount templateAccount = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
                templateAccount.setUsername(username);
                UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").load(templateAccount);
                skin = account.getSkin();
            }
            if(previous==null || previous.getId()==null || !previous.getId().equals(skin)) {
                SkinFB pbSkin = SkinHelper.loadSkin(mapping,skin);
                request.getSession().setAttribute("skinPB",pbSkin);
            }
        }
        SkinFB pbSkin = (SkinFB) request.getSession().getAttribute("skinPB");
        if(pbSkin==null || StringUtil.asNull(pbSkin.getStylesheet())==null) {
            if(gallery!=null&&gallery.getStylesheet()!=null&&gallery.getStylesheet().length()>0) {
                request.getSession().setAttribute("stylesheetPB",gallery.getStylesheet());
            }else {
                request.getSession().removeAttribute("stylesheetPB");
            }
        }else {
            request.getSession().setAttribute("stylesheetPB",pbSkin.getStylesheet());
        }
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1, picture.getLink().length() - 1);
    }
    private String getPictureInfo(String parameter, Picture picture, PicturePB pb,JPEGMetadataHandler jpegHandler, FileMetadataHandler fileHandler) {
        return ServletParameterHelper.replaceDynamicParameters(parameter, new PictureParameterStorage(picture.getFile(),picture,pb,jpegHandler,fileHandler));
    }
}