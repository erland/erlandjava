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

public class IMatchImportAction extends BaseImportAction {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImportFB fb = (ImportFB) form;
        try {
            if (fb.getClearCategories().booleanValue()) {
                clearCategories(fb.getGallery());
            }
            if (fb.getClearPictures().booleanValue()) {
                clearPictures(fb.getGallery());
            }
            long orderNoStart = getFirstFreeOrderNo(fb.getGallery());
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

                updatePictures(fb.getGallery());
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
}