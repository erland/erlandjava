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

import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.entity.gallery.picturestorage.PictureStorage;
import erland.webapp.gallery.entity.skin.Skin;
import erland.webapp.gallery.fb.gallery.picturestorage.PictureStorageFB;
import erland.webapp.gallery.fb.skin.SkinFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveSkinAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SkinFB fb = (SkinFB) form;
        Skin skin = (Skin) getEnvironment().getEntityFactory().create("gallery-skin");
        skin.setId(fb.getId());
        skin = (Skin) getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").load(skin);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").delete(skin);
    }
}