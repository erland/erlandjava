package erland.webapp.gallery.act.gallery.importers;

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
import erland.webapp.gallery.fb.gallery.importers.ExternalImportFB;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.usermgmt.User;
import erland.util.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Action that imports pictures posted as a XML document with the following layout:
 * <br>&lt;images>
 * <br>     &lt;image oid="5561"&gt;
 * <br>         &lt;filename&gt;C:\My Pictures\PICT0001.JPG&lt;/filename&gt;
 * <br>         &lt;last-modified&gt;2004-05-07 13:39:12&lt;/last-modified&gt;
 * <br>         &lt;categories&gt;
 * <br>             &lt;category&gt;My Categories.Category1&lt;/category&gt;
 * <br>             &lt;category&gt;My Categories.Category2&lt;/category&gt;
 * <br>         &lt;/categories&gt;
 * <br>         &lt;properties&gt;
 * <br>         &lt;property name="Title"&gt;Picture1&lt;/property&gt;
 * <br>         &lt;property name="Description"&gt;A nice picture&lt;/property&gt;
 * <br>         &lt;/properties&gt;
 * <br>     &lt;/image&gt;
 * <br>&lt;/images&gt;
 */
public class ExternalIMatchImportAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExternalImportFB fb = (ExternalImportFB) form;
        Log.println(this,"External import started...");
        Log.println(this,"clearCategories="+fb.getClearCategories());
        Log.println(this,"clearPictures="+fb.getClearPictures());
        Log.println(this,"localLinks="+fb.getLocalLinks());
        Log.println(this,"filenameAsPictureDescription="+fb.getFilenameAsPictureDescription());
        Log.println(this,"filenameAsPictureTitle="+fb.getFilenameAsPictureTitle());
        Log.println(this,"user="+fb.getUser());
        Log.println(this,"pass="+fb.getPass());
        Log.println(this,"gallery="+fb.getGallery());
        Log.println(this,"Parsing xml document...");

        Integer galleryId = null;
        if(!validateUser(fb.getUser(),fb.getPass())) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.unauthorized-access"}));
            return;
        }
        galleryId = getGalleryByTitle(fb.getUser(),fb.getGallery());
        if(galleryId==null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.gallery-dont-exist"}));
            return;
        }

        if (fb.getClearCategories().booleanValue()) {
            ImportHelper.clearCategories(galleryId);
        }
        if (fb.getClearPictures().booleanValue()) {
            ImportHelper.clearPictures(galleryId);
        }
        ExternalIMatchImportPlugin plugin = ExternalIMatchImportPlugin.getInstance();
        if(plugin!=null && plugin.isActive()) {
            if(!plugin.importPictures(galleryId,request.getReader(),fb.getLocalLinks(),fb.getFilenameAsPictureTitle(),fb.getFilenameAsPictureDescription(),Boolean.valueOf(!fb.getClearPictures().booleanValue()))) {
                saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.parse-failure"}));
                return;
            }
        }else if(!ExternalIMatchImportHelper.importPictures(galleryId,request.getReader(),fb.getLocalLinks(),fb.getFilenameAsPictureTitle(),fb.getFilenameAsPictureDescription(),Boolean.valueOf(!fb.getClearPictures().booleanValue()))){
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.import.parse-failure"}));
            return;
        }
    }

    private boolean validateUser(String username, String password) {
        User user = (User)getEnvironment().getEntityFactory().create("usermgmt-user");
        user.setUsername(username);
        user.setPassword(password);
        if(user.login("gallery")) {
            return true;
        }
        return false;
    }

    private Integer getGalleryByTitle(String username, String title) {
        QueryFilter filter = new QueryFilter("allforuserandtitle");
        filter.setAttribute("username",username);
        filter.setAttribute("title",title);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-gallery").search(filter);
        if(entities.length==1) {
            return ((Gallery)entities[0]).getId();
        }
        return null;
    }
}