package erland.webapp.help.act.application;

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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.help.entity.application.Application;
import erland.webapp.help.entity.application.ApplicationVersion;
import erland.webapp.help.entity.chapter.Chapter;
import erland.webapp.help.fb.application.MenuItemPB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadMenuAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        boolean useEnglish = true;
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("user",request.getRemoteUser());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("help-application").search(filter);
        MenuItemPB[] pb = new MenuItemPB[entities.length];
        ActionForward forward = mapping.findForward("view-application");
        ActionForward versionForward = mapping.findForward("view-applicationversion");
        ActionForward chapterForward = mapping.findForward("view-chapter");
        ActionForward newVersionForward = mapping.findForward("new-applicationversion");
        ActionForward newChapterForward = mapping.findForward("new-chapter");
        for (int i = 0; i < entities.length; i++) {
            Application application = (Application) entities[i];
            pb[i] = new MenuItemPB();
            pb[i].setId(application.getName());
            pb[i].setApplication(application.getName());
            if(useEnglish && StringUtil.asNull(application.getTitleEnglish())!=null) {
                pb[i].setName(application.getTitleEnglish());
            }else {
                pb[i].setName(application.getTitleNative());
            }
            pb[i].setUser(request.getRemoteUser());
            if(forward!=null) {
                pb[i].setPath(forward.getPath());
            }
            MenuItemPB[] versionsPB = getVersions(request.getRemoteUser(),application.getName(),versionForward,chapterForward,newVersionForward, newChapterForward, useEnglish);
            pb[i].setChilds(versionsPB);
        }
        request.getSession().setAttribute("menuUserApplicationsPB", pb);
    }
    private MenuItemPB[] getVersions(String user, String application, ActionForward versionForward, ActionForward chapterForward, ActionForward newVersionForward, ActionForward newChapterForward, boolean useEnglish) {
        QueryFilter versionFilter = new QueryFilter("allforapplication");
        versionFilter.setAttribute("application",application);
        EntityInterface[] versionEntities = getEnvironment().getEntityStorageFactory().getStorage("help-applicationversion").search(versionFilter);
        MenuItemPB[] versionsPB = new MenuItemPB[versionEntities.length];
        int start = 0;
        if(newVersionForward!=null) {
            versionsPB = new MenuItemPB[versionEntities.length+1];
            versionsPB[0] = new MenuItemPB();
            versionsPB[0].setId("newversion");
            versionsPB[0].setNameKey("help.menu.applications.version.new");
            versionsPB[0].setUser(user);
            versionsPB[0].setApplication(application);
            versionsPB[0].setPath(newVersionForward.getPath());
            start++;
        }
        for (int i = start; i < versionsPB.length; i++) {
            ApplicationVersion version = (ApplicationVersion) versionEntities[i-start];
            versionsPB[i] = new MenuItemPB();
            versionsPB[i].setId(version.getVersion());
            versionsPB[i].setName(version.getVersion());
            versionsPB[i].setUser(user);
            versionsPB[i].setApplication(version.getApplication());
            versionsPB[i].setVersion(version.getVersion());
            if(versionForward!=null) {
                versionsPB[i].setPath(versionForward.getPath());
            }
            MenuItemPB[] chaptersPB = getChapters(user,application,version.getVersion(),chapterForward,newChapterForward, useEnglish);
            versionsPB[i].setChilds(chaptersPB);
        }
        return versionsPB;
    }
    protected MenuItemPB[] getChapters(String user, String application, String version, ActionForward chapterForward, ActionForward newChapterForward, boolean useEnglish) {
        QueryFilter chapterFilter = new QueryFilter("allforapplicationandversion");
        chapterFilter.setAttribute("application",application);
        chapterFilter.setAttribute("version",version);
        EntityInterface[] chapterEntities = getEnvironment().getEntityStorageFactory().getStorage("help-chapter").search(chapterFilter);
        MenuItemPB[] chaptersPB = new MenuItemPB[chapterEntities.length];
        int start = 0;
        if(newChapterForward!=null) {
            chaptersPB = new MenuItemPB[chapterEntities.length+1];
            chaptersPB[0] = new MenuItemPB();
            chaptersPB[0].setId("newchapter");
            chaptersPB[0].setNameKey("help.menu.chapter.new");
            chaptersPB[0].setUser(user);
            chaptersPB[0].setApplication(application);
            chaptersPB[0].setVersion(version);
            chaptersPB[0].setPath(newChapterForward.getPath());
            start++;
        }
        for (int i = start; i < chaptersPB.length; i++) {
            Chapter chapter = (Chapter) chapterEntities[i-start];
            chaptersPB[i] = new MenuItemPB();
            chaptersPB[i].setId(chapter.getChapter());
            if(useEnglish && StringUtil.asNull(chapter.getTitleEnglish())!=null) {
                chaptersPB[i].setName(chapter.getTitleEnglish());
            }else {
                chaptersPB[i].setName(chapter.getTitleNative());
            }
            chaptersPB[i].setUser(user);
            chaptersPB[i].setApplication(chapter.getApplication());
            chaptersPB[i].setVersion(chapter.getVersion());
            chaptersPB[i].setChapter(chapter.getChapter());
            if(chapterForward!=null) {
                chaptersPB[i].setPath(chapterForward.getPath());
            }
        }
        return chaptersPB;
    }
}