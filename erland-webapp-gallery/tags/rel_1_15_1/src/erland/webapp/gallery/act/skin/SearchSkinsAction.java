package erland.webapp.gallery.act.skin;

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
import erland.webapp.gallery.fb.gallery.picturestorage.PictureStoragePB;
import erland.webapp.gallery.fb.skin.SkinFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchSkinsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter filter = new QueryFilter("all");
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").search(filter);
        SkinFB[] pb = new SkinFB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new SkinFB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.setAttribute("skinsPB", pb);
    }
}