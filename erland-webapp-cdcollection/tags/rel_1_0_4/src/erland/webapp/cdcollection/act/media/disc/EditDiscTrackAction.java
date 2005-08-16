package erland.webapp.cdcollection.act.media.disc;

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

import erland.webapp.cdcollection.entity.media.disc.Disc;
import erland.webapp.cdcollection.entity.media.disc.DiscTrack;
import erland.webapp.cdcollection.fb.media.disc.DiscFB;
import erland.webapp.cdcollection.fb.media.disc.DiscTrackFB;
import erland.webapp.common.act.BaseAction;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditDiscTrackAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiscTrackFB fb = (DiscTrackFB) form;
        DiscTrack disc = (DiscTrack) getEnvironment().getEntityFactory().create("cdcollection-disctrack");
        PropertyUtils.copyProperties(disc, fb);
        getEnvironment().getEntityStorageFactory().getStorage("cdcollection-disctrack").store(disc);
    }
}
