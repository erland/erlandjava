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
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IMatchImportAction extends BaseAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(IMatchImportAction.class);
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImportFB fb = (ImportFB) form;
        try {
            if (fb.getClearCategories().booleanValue()) {
                ImportHelper.clearCategories(fb.getGallery());
            }
            if (fb.getClearPictures().booleanValue()) {
                ImportHelper.clearPictures(fb.getGallery());
            }
            Reader reader = null;
            try {
                reader = new FileReader(fb.getFile());
            } catch (FileNotFoundException e) {
                reader = new InputStreamReader(new URL(fb.getFile()).openStream());
            }
            IMatchImportPlugin plugin = IMatchImportPlugin.getInstance();
            if(plugin!=null && plugin.isActive()) {
                if(!plugin.importPictures(fb.getGallery(),reader,fb.getLocalLinks(),fb.getFilenameAsPictureTitle(),fb.getFilenameAsPictureDescription(),Boolean.valueOf(!fb.getClearPictures().booleanValue()))) {
                    saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.parse-failure"}));
                    return;
                }
            }else if(!IMatchImportHelper.importPictures(getEnvironment(),fb.getGallery(),reader,fb.getLocalLinks(),fb.getFilenameAsPictureTitle(),fb.getFilenameAsPictureDescription(),Boolean.valueOf(!fb.getClearPictures().booleanValue()))) {
                saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.parse-failure"}));
                return;
            }
        } catch (IOException e) {
            LOG.error("Error while reading file "+fb.getFile(),e);
        }
    }
}