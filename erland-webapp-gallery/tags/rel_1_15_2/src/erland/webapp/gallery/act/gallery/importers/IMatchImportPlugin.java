package erland.webapp.gallery.act.gallery.importers;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.io.*;
import java.util.*;

import erland.util.StringUtil;
import erland.webapp.common.act.BaseTaskPlugin;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.act.gallery.GalleryHelper;

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

public class IMatchImportPlugin extends BaseTaskPlugin{
    private static IMatchImportPlugin me = null;

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        super.init(actionServlet,moduleConfig);
        me = this;
    }
    public static IMatchImportPlugin getInstance() {
        return me;
    }
    public boolean isActive() {
        String active = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("backgroundimport");
        return StringUtil.asBoolean(active,Boolean.FALSE).booleanValue();
    }
    public Gallery[] getActiveImportPictureTasks() {
        Object[] tasks = getActiveTasks();
        ArrayList galleryList = new ArrayList();
        for (int i = 0; i < tasks.length; i++) {
            Integer galleryId = (Integer) tasks[i];
            Gallery gallery = GalleryHelper.getGallery(WebAppEnvironmentPlugin.getEnvironment(),galleryId);
            if(gallery!=null) {
                galleryList.add(gallery);
            }
        }
        return (Gallery[]) galleryList.toArray(new Gallery[0]);
    }

    public boolean importPictures(Integer galleryId, Reader reader, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations) {
        try {
            StringBuffer sb = new StringBuffer(100000);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while(line!=null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            reader = new StringReader(sb.toString());
            return addTask(galleryId,new Task(galleryId,reader,localLinks,filenameAsPictureTitle,filenameAsPictureDescription,clearAssociations));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    private class Task implements Runnable {
        private Integer galleryId;
        private Reader reader;
        private Boolean localLinks;
        private Boolean filenameAsPictureTitle;
        private Boolean filenameAsPictureDescription;
        private Boolean clearAssociations;

        public Task(Integer galleryId, Reader reader, Boolean localLinks, Boolean filenameAsPictureTitle, Boolean filenameAsPictureDescription, Boolean clearAssociations) {
            this.galleryId = galleryId;
            this.reader = reader;
            this.localLinks = localLinks;
            this.filenameAsPictureTitle = filenameAsPictureTitle;
            this.filenameAsPictureDescription = filenameAsPictureDescription;
            this.clearAssociations = clearAssociations;
        }
        public void run() {
            IMatchImportHelper.importPictures(galleryId,reader,localLinks,filenameAsPictureTitle,filenameAsPictureDescription,clearAssociations);
        }
    }
}