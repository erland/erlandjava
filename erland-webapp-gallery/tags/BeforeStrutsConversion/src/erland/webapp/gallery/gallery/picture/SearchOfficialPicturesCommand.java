package erland.webapp.gallery.gallery.picture;
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

import erland.webapp.usermgmt.User;
import erland.webapp.gallery.guestaccount.GuestAccountHelper;

import javax.servlet.http.HttpServletRequest;

public class SearchOfficialPicturesCommand extends SearchPicturesCommand {
    private Boolean official;
    protected void initCommand(HttpServletRequest request) {
        official=Boolean.TRUE;
        String username = request.getParameter("user");
        User user = (User) request.getSession().getAttribute("guestuser");
        if(user!=null && username!=null) {
            if(GuestAccountHelper.isGuestUser(getEnvironment(),username,user.getUsername())) {
                official = Boolean.FALSE;
            }
        }
    }

    protected String getAllFilter() {
        if(official.booleanValue()) {
            return "allofficialforgallery";
        }else {
            return super.getAllFilter();
        }
    }

    protected String getCategoryTreeFilter() {
        if(official.booleanValue()) {
            return "allofficialforgalleryandcategorylist";
        }else {
            return super.getCategoryTreeFilter();
        }
    }
}