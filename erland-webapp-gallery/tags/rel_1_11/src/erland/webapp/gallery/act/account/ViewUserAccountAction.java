package erland.webapp.gallery.act.account;

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
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.gallery.entity.account.UserAccount;
import erland.webapp.gallery.entity.gallery.Gallery;
import erland.webapp.gallery.fb.account.AccountFB;
import erland.webapp.gallery.fb.gallery.GalleryPB;
import erland.webapp.gallery.fb.skin.SkinFB;
import erland.webapp.gallery.act.gallery.GalleryHelper;
import erland.webapp.usermgmt.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ViewUserAccountAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountFB fb = (AccountFB) form;
        String username = getUsername(request,fb);
        UserAccount template = (UserAccount) getEnvironment().getEntityFactory().create("gallery-useraccount");
        template.setUsername(username);
        UserAccount account = (UserAccount) getEnvironment().getEntityStorageFactory().getStorage("gallery-useraccount").load(template);
        PropertyUtils.copyProperties(fb, account);
        PropertyUtils.copyProperties(fb, getUser(username));

        Gallery[] entities = GalleryHelper.searchGalleries(getEnvironment(),"gallery-gallery",username,"allforuser");
        GalleryPB[] pb = new GalleryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new GalleryPB();
            PropertyUtils.copyProperties(pb[i], entities[i]);
        }
        request.getSession().setAttribute("galleriesPB", pb);

        EntityInterface[] skins = getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").search(new QueryFilter("all"));
        SkinFB[] pbSkins = new SkinFB[skins.length];
        for (int i = 0; i < pbSkins.length; i++) {
            pbSkins[i] = new SkinFB();
            PropertyUtils.copyProperties(pbSkins[i], skins[i]);
        }
        request.getSession().setAttribute("skinsPB",pbSkins);
    }

    protected String getUsername(HttpServletRequest request, AccountFB fb) {
        return request.getRemoteUser();
    }
    public User getUser(String username) {
        User template = (User) getEnvironment().getEntityFactory().create("usermgmt-userinfo");
        template.setUsername(username);
        User user = (User) getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
        return user;
    }
}
