package erland.webapp.dirgallery.act.loader;

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

import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.FileMetadataHandler;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.common.image.MetadataHandlerInterface;
import erland.webapp.dirgallery.entity.gallery.picture.Picture;
import erland.webapp.dirgallery.entity.gallery.picture.PictureComment;
import erland.webapp.dirgallery.entity.gallery.Gallery;
import erland.webapp.dirgallery.fb.gallery.picture.PicturePB;
import erland.webapp.dirgallery.fb.loader.MetadataCollectionPB;
import erland.webapp.dirgallery.fb.loader.MetadataImageFB;
import erland.webapp.dirgallery.fb.loader.MetadataPB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoadMetadataAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MetadataImageFB fb = (MetadataImageFB) form;

        Gallery templateGallery = (Gallery) getEnvironment().getEntityFactory().create("dirgallery-gallery");
        templateGallery.setId(fb.getGallery());
        Gallery entityGallery = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-gallery").load(templateGallery);
        if (entityGallery == null) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.loader.gallery-dont-exist"}));
            return;
        }
        Picture template = (Picture) getEnvironment().getEntityFactory().create("dirgallery-picture");
        template.setGallery(fb.getGallery());
        template.setDirectory(getEnvironment().getConfigurableResources().getParameter("basedirectory") + "/" + entityGallery.getUsername() + "/" + entityGallery.getDirectory());
        template.setId(fb.getImage());
        Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picture").load(template);
        if (picture == null) {
            saveErrors(request, Arrays.asList(new String[]{"dirgallery.loader.picture-dont-exist"}));
            return;
        }

        String imageFile = getImageFileName(picture);
        MetadataHandlerInterface handler = null;
        String[] names = new String[0];

        if (fb.getShowSelected().booleanValue() || fb.getShowAll().booleanValue()) {
            if (imageFile.toLowerCase().endsWith(".jpg") || imageFile.toLowerCase().endsWith(".jpeg")) {
                handler = new JPEGMetadataHandler(!fb.getShowAll().booleanValue());
            } else {
                handler = new FileMetadataHandler(!fb.getShowAll().booleanValue());
            }
            handler.load(imageFile);
            names = handler.getNames();
        }

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
        pb.setShowSelected(fb.getShowSelected());

        Map parameters = new HashMap();
        parameters.put("gallery", fb.getGallery());
        parameters.put("picture", fb.getImage());
        if (fb.getWidth() != null) {
            parameters.put("width", fb.getWidth());
        }
        if (entityGallery.getNumberOfMovieThumbnailRows() != null && entityGallery.getNumberOfMovieThumbnailRows().intValue() > 0) {
            parameters.put("movieRows", entityGallery.getNumberOfMovieThumbnailRows());
        }
        if (entityGallery.getNumberOfMovieThumbnailColumns() != null && entityGallery.getNumberOfMovieThumbnailColumns().intValue() > 0) {
            parameters.put("movieCols", entityGallery.getNumberOfMovieThumbnailColumns());
        }
        ActionForward forward = mapping.findForward("show-all-metadata-link");
        if (fb.getShowSelected().booleanValue() && forward != null) {
            pb.setShowAllLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
        }
        forward = mapping.findForward("show-selected-metadata-link");
        if (!fb.getShowSelected().booleanValue() && forward != null) {
            pb.setShowSelectedLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
        }
        forward = mapping.findForward("hide-all-metadata-link");
        if ((fb.getShowAll().booleanValue() || fb.getShowSelected().booleanValue()) && forward != null) {
            pb.setHideAllLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
        }
        request.setAttribute("metadataPB", pb);

        PicturePB picturePB = new PicturePB();
        PropertyUtils.copyProperties(picturePB, picture);
        if (entityGallery.getUseShortPictureNames().booleanValue()) {
            picturePB.setName(picture.getShortName());
        }
        forward = mapping.findForward("picture-image");
        if (forward != null && fb.getWidth() == null) {
            picturePB.setImage(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
        } else if (fb.getWidth() != null) {
            forward = mapping.findForward("picture-resolution-image");
            if (forward != null) {
                picturePB.setImage(ServletParameterHelper.replaceDynamicParameters(forward.getPath(), parameters));
            }
        }
        forward = mapping.findForward("picture-image-small");
        if (forward != null) {
            picturePB.setPathSmallImage(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("picture-image-large");
        if (forward != null) {
            picturePB.setPathLargeImage(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }

        picturePB.setGallery(fb.getGallery());
        picturePB.setComment(getComment(picture));
        request.setAttribute("picturePB", picturePB);
    }

    protected String getImageFileName(Picture picture) {
        return picture.getDirectory() + picture.getFileName();
    }

    private String getComment(Picture picture) {
        String comment = null;
        if (picture != null) {
            PictureComment template = (PictureComment) getEnvironment().getEntityFactory().create("dirgallery-picturecomment");
            template.setId(picture.getFullPath());
            PictureComment pictureComment = (PictureComment) getEnvironment().getEntityStorageFactory().getStorage("dirgallery-picturecomment").load(template);
            if (pictureComment != null) {
                comment = pictureComment.getComment();
            }
        }
        return comment;
    }
}