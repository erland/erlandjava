package erland.webapp.gallery.act.gallery.importers;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Reader;
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

public class ExternalIMatchImportHelper extends ImportHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ExternalIMatchImportHelper.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean importPictures(Integer galleryId, Reader reader, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations) {
        long orderNoStart = getFirstFreeOrderNo(galleryId);
        Map categories = new HashMap();
        loadCategories(categories, galleryId);

        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(reader);

            if (document != null) {
                List list = document.selectNodes( "/images/image" );
                LOG.debug("Found "+list.size()+" image elements");
                int i = 1;
                for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                    Element element = (Element) iter.next();
                    LOG.debug("Parsing image "+ i++);
                    doImport(categories, galleryId, element, localLinks, filenameAsPictureTitle, filenameAsPictureDescription, clearAssociations, orderNoStart++);
                }
                LOG.debug("All image elements imported, removing unused categories and updating visibility flags...");
                clearUnusedCategories(galleryId);
                updatePictures(galleryId);
                LOG.debug("Visibility flags updated");
                return true;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void doImport(Map previousCategories, Integer gallery, Element element, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations, long orderNoStart) {
        if (element != null) {
            Node node = element.selectSingleNode("last-modified");
            Date date = null;
            if(node!=null) {
                String dateString = node.getText();
                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String oidString = element.valueOf("@oid");
            Integer oid = null;
            if (oidString != null && oidString.length() > 0) {
                oid = Integer.valueOf(oidString);
            }
            String title = null;
            node = element.selectSingleNode("properties/property[@name='Title']");
            if(node!=null) {
                title = node.getText();
            }
            String description = null;
            node = element.selectSingleNode("properties/property[@name='Description']");
            if(node!=null) {
                description = node.getText();
            }
            String filename = null;
            node = element.selectSingleNode("filename");
            if(node!=null) {
                filename = node.getText();
            }
            LOG.trace("filename="+filename);
            LOG.trace("date="+date);
            LOG.trace("oid="+oid);
            LOG.trace("title="+title);
            LOG.trace("description="+description);
            Integer pictureId = createPicture(gallery, filename, date, oid, title, description, localLinks, filenameAsPictureTitle, filenameAsPictureDescription, new Long(orderNoStart));
            if(clearAssociations.booleanValue()) {
                clearPictureAssociations(gallery,pictureId);
            }
            List list = element.selectNodes( "categories/category" );
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();
                String category = e.getText();
                LOG.trace("category="+category);
                Integer categoryId = createCategory(previousCategories, gallery, category);
                createPictureAssociation(gallery, pictureId, categoryId);
            }
        }
    }
}