package erland.webapp.gallery.act.gallery.picture;

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

import erland.webapp.gallery.act.guestaccount.GuestAccountHelper;
import erland.webapp.gallery.fb.gallery.picture.SelectPictureFB;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdvancedSearchOfficialGuestPicturesAction extends AdvancedSearchPicturesAction {
    public final static String OFFICIAL = AdvancedSearchOfficialGuestPicturesAction.class + "-official";

    protected void preProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SelectPictureFB fb = (SelectPictureFB) form;
        Boolean official = Boolean.TRUE;
        String username = request.getRemoteUser();
        if (fb != null) {
            username = fb.getUser();
        }
        if (GuestAccountHelper.isGuestUser(getEnvironment(), username, fb.getGuestUser())) {
            official = Boolean.FALSE;
        }
        setOfficial(request,official);
    }

    protected String getAllFilter(HttpServletRequest request) {
        if (getOfficial(request).booleanValue()) {
            return getOfficialFilterPrefix()+"forgallerybetweendates";
        } else {
            return super.getAllFilter(request);
        }
    }

    protected String getCategoryTreeFilter(HttpServletRequest request) {
        if (getOfficial(request).booleanValue()) {
            if (!getAllCategories(request).booleanValue()) {
                return getOfficialFilterPrefix()+"forgalleryandcategorylistbetweendates";
            } else {
                return getOfficialFilterPrefix()+"forgalleryandcategorylistallrequiredbetweendates";
            }
        } else {
            return super.getCategoryTreeFilter(request);
        }
    }
    public Boolean getOfficial(HttpServletRequest request) {
        return (Boolean) request.getAttribute(OFFICIAL);
    }

    public void setOfficial(HttpServletRequest request, Boolean official) {
        request.setAttribute(OFFICIAL,official);
    }

    protected String getOfficialFilterPrefix() {
        return "allofficialguest";
    }
}