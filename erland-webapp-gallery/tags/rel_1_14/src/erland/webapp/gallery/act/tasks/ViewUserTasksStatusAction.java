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

public class ViewUserTasksStatusAction extends ViewTasksStatusAction {
    protected boolean filter(Gallery gallery, HttpServletRequest request) {
        if(gallery.getUsername().equals(request.getRemoteUser())) {
            return true;
        }else {
            return false;
        }
    }
}