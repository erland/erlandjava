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
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.common.image.MetadataHandlerInterface;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.fb.loader.MetadataImageFB;
import erland.webapp.gallery.fb.loader.MetadataPB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoadMetadataAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MetadataImageFB fb = (MetadataImageFB) form;
        Integer gallery = GalleryHelper.getGalleryId(getEnvironment(), fb.getGallery());
        Picture template = (Picture) getEnvironment().getEntityFactory().create("gallery-picture");
        template.setGallery(gallery);
        template.setId(fb.getImage());
        Picture picture = (Picture) getEnvironment().getEntityStorageFactory().getStorage("gallery-picture").load(template);
        if (picture == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.loader.picture-dont-exist"}));
            return;
        }
        Gallery templateGallery = (Gallery) getEnvironment().getEntityFactory().create("gallery-gallery");
        templateGallery.setId(gallery);
        Gallery entity = (Gallery) getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").load(templateGallery);
        if (entity == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.loader.gallery-dont-exist"}));
            return;
        }
        String username = entity.getUsername();
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
        MetadataHandlerInterface handler = new JPEGMetadataHandler(!fb.getShowAll().booleanValue());
        handler.load(imageFile);
        String[] names = handler.getNames();
        MetadataPB[] pb = new MetadataPB[names.length];
        for (int i = 0; i < pb.length; i++) {
            pb[i] = new MetadataPB();
            pb[i].setId(names[i]);
            pb[i].setDescription(handler.getDescription(names[i]));
            pb[i].setValue(handler.getValue(names[i]));
        }
        request.setAttribute("metadataPB", pb);
    }

    protected String getImageFileName(Picture picture) {
        return picture.getLink().substring(1, picture.getLink().length() - 1);
    }
}