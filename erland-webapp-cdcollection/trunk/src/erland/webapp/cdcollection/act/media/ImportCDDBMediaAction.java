package erland.webapp.cdcollection.act.media;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.cdcollection.fb.media.MediaCDDBSearchFB;
import erland.webapp.cdcollection.entity.media.disc.Disc;
import erland.webapp.cdcollection.entity.media.disc.DiscTrack;
import erland.webapp.cdcollection.logic.media.disc.DiscHelper;
import erland.webapp.cdcollection.logic.media.MediaHelper;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.antelmann.cddb.*;
import com.antelmann.util.Settings;

public class ImportCDDBMediaAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MediaCDDBSearchFB fb = (MediaCDDBSearchFB) form;
        int mediaId = MediaHelper.importMedia(fb.getCategory(),fb.getDiscId());
        request.setAttribute("media",new Integer(mediaId));
    }
}