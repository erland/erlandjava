package erland.webapp.gallery.act.gallery.importers;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

public class IMatchImportHelper extends ImportHelper {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean importPictures(Integer galleryId, Reader reader, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations) {
        long orderNoStart = getFirstFreeOrderNo(galleryId);
        Map categories = new HashMap();
        loadCategories(categories, galleryId);
        if (reader != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(reader);
                //Ignore first line
                bufferedReader.readLine();
                String line = bufferedReader.readLine();
                while (line != null) {
                    doImport(categories, galleryId, line, localLinks, filenameAsPictureTitle, filenameAsPictureDescription, clearAssociations, orderNoStart++);
                    line = bufferedReader.readLine();
                }

                clearUnusedCategories(galleryId);
                updatePictures(galleryId);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private static void doImport(Map previousCategories, Integer gallery, String line, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations, long orderNoStart) {
        StringTokenizer tokenizer = new StringTokenizer(line, "\t", true);
        if (tokenizer.countTokens() >= 8) {
            String picture = tokenizer.nextToken();
            // Discard all pictures that is not a jpeg
            if(picture==null||!picture.toLowerCase().matches(".*\\.jpeg|.*\\.jpg")) {
                return;
            }
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
            if(clearAssociations.booleanValue()) {
                clearPictureAssociations(gallery,pictureId);
            }
            for (StringTokenizer it = new StringTokenizer(categories.substring(1, categories.length() - 1), ","); it.hasMoreTokens();) {
                String category = it.nextToken();
                Integer categoryId = createCategory(previousCategories, gallery, category);
                createPictureAssociation(gallery, pictureId, categoryId);
            }
        }
    }
}