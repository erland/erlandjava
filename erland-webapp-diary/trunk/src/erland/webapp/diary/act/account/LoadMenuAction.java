package erland.webapp.diary.act.account;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.diary.DiaryFB;
import erland.webapp.diary.entity.account.UserAccount;
import erland.webapp.diary.entity.diary.Diary;
import erland.webapp.diary.entity.gallery.Gallery;
import erland.webapp.diary.entity.container.Container;
import erland.webapp.gallery.fb.gallery.MenuItemPB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.lang.reflect.InvocationTargetException;

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

public class LoadMenuAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = getUsername(request,fb);

        QueryFilter filter = new QueryFilter(getDiaryQueryFilter());
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-diary").search(filter);
        MenuItemPB[] pb = new MenuItemPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new MenuItemPB();
            pb[i].setId(((Diary)entities[i]).getId());
            pb[i].setName(((Diary)entities[i]).getTitle());
            pb[i].setUser(username);
            ActionForward forward = mapping.findForward("view-diary-link");
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuDiariesPB",pb);

        filter = new QueryFilter(getGalleryQueryFilter());
        filter.setAttribute("username", username);
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-gallery").search(filter);
        pb = new MenuItemPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new MenuItemPB();
            pb[i].setId(((Gallery)entities[i]).getId());
            pb[i].setName(((Gallery)entities[i]).getTitle());
            pb[i].setUser(username);
            ActionForward forward = mapping.findForward("view-gallery-link");
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuGalleriesPB",pb);

        filter = new QueryFilter(getContainerQueryFilter());
        filter.setAttribute("username", username);
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-container").search(filter);
        pb = new MenuItemPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new MenuItemPB();
            pb[i].setId(((Container)entities[i]).getId());
            pb[i].setName(((Container)entities[i]).getName());
            pb[i].setUser(username);
            ActionForward forward = mapping.findForward("view-container-link");
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
        }
        request.getSession().setAttribute("menuContainersPB",pb);
    }

    protected String getDiaryQueryFilter() {
        return "allforuser";
    }

    protected String getGalleryQueryFilter() {
        return "allforuser";
    }

    protected String getContainerQueryFilter() {
        return "allforuser";
    }

    protected String getUsername(HttpServletRequest request, SelectUserFB fb) {
        String username = request.getRemoteUser();
        if(username==null) {
            username = fb.getUser();
        }
        return username;
    }
}