package erland.webapp.gallery.act.tasks;

import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.act.gallery.importers.ExternalIMatchImportPlugin;
import erland.webapp.gallery.act.gallery.importers.IMatchImportPlugin;
import erland.webapp.gallery.act.loader.GenerateThumbnailPlugin;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.fb.tasks.TaskPB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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

public class ViewTasksStatusAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList taskList = new ArrayList();
        ExternalIMatchImportPlugin externalImportPlugin = ExternalIMatchImportPlugin.getInstance();
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        if(externalImportPlugin!=null) {
            Gallery[] galleries = externalImportPlugin.getActiveImportPictureTasks();
            addTasks(taskList,"gallery.tasks.status.type.externalimport",galleries,request,useEnglish);
        }

        IMatchImportPlugin importPlugin = IMatchImportPlugin.getInstance();
        if(importPlugin!=null) {
            Gallery[] galleries = importPlugin.getActiveImportPictureTasks();
            addTasks(taskList,"gallery.tasks.status.type.webimport",galleries,request,useEnglish);
        }

        GenerateThumbnailPlugin thumbnailPlugin = GenerateThumbnailPlugin.getInstance();
        if(thumbnailPlugin!=null) {
            Gallery[] galleries = thumbnailPlugin.getActiveThumbnailGenerationTasks();
            addTasks(taskList,"gallery.tasks.status.type.thumbnails",galleries,request,useEnglish);
        }
        TaskPB[] tasks = (TaskPB[]) taskList.toArray(new TaskPB[0]);
        request.setAttribute("tasksPB",tasks);
    }

    private void addTasks(ArrayList taskList, String taskType, Gallery[] galleries, HttpServletRequest request, boolean useEnglish) {
        for (int i = 0; i < galleries.length; i++) {
            Gallery gallery = galleries[i];
            TaskPB task = new TaskPB();
            if(filter(gallery,request)) {
                task.setUser(gallery.getUsername());
                task.setTypeKey(taskType);
                if(useEnglish && StringUtil.asNull(gallery.getTitleEnglish())!=null) {
                    task.setDescription(gallery.getTitleEnglish());
                }else {
                    task.setDescription(gallery.getTitle());
                }
            }
            taskList.add(task);
        }
    }
    protected boolean filter(Gallery gallery, HttpServletRequest request) {
        return true;
    }
}