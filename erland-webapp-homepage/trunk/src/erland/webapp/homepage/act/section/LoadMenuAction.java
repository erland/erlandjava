package erland.webapp.homepage.act.section;

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

import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.BaseAction;
import erland.webapp.homepage.entity.section.Section;
import erland.webapp.homepage.fb.section.MenuItemPB;
import erland.webapp.usermgmt.User;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class LoadMenuAction extends BaseAction {
    protected Comparator SORT_BY_NAME = new Comparator() {
        public int compare(Object o1, Object o2) {
            if (o1 instanceof MenuItemPB && o2 instanceof MenuItemPB) {
                return ((MenuItemPB) o1).getName().compareTo(((MenuItemPB) o2).getName());
            }
            return 0;
        }
    };

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("user");
        if (StringUtil.asNull(username)==null) {
            username = request.getRemoteUser();
        }
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));

        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("homepage-section").search(filter);
        ActionForward sectionForward = mapping.findForward("section");
        String sectionPath = null;
        if (sectionForward != null) {
            sectionPath = sectionForward.getPath();
        }
        ActionForward newSectionForward = mapping.findForward("section-new");
        String newSectionPath = null;
        if (newSectionForward!=null) {
            newSectionPath = newSectionForward.getPath();
        }
        MenuItemPB[] sectionsPB = makeMenuTree(username, (Section[]) Arrays.asList(entities).toArray(new Section[0]), sectionPath, useEnglish);
        if(newSectionPath!=null) {
            for (int i = 0; i < sectionsPB.length; i++) {
                MenuItemPB menuItemPB = sectionsPB[i];
                addNewSectionMenuItem(menuItemPB,newSectionPath);
            }
            MenuItemPB newMenu = new MenuItemPB();
            newMenu.setId(new Integer(0));
            newMenu.setNameKey("homepage.menu.section-new");
            newMenu.setPath(newSectionPath);
            List sectionList = new ArrayList(Arrays.asList(sectionsPB));
            sectionList.add(newMenu);
            sectionsPB = (MenuItemPB[]) sectionList.toArray(new MenuItemPB[0]);
        }
        request.getSession().setAttribute("menuSectionsPB", sectionsPB);
    }

    protected void addNewSectionMenuItem(MenuItemPB section,String path) {
        MenuItemPB[] childs = section.getChilds();
        if(childs!=null && childs.length>0) {
            for (int i = 0; i < childs.length; i++) {
                MenuItemPB child = childs[i];
                addNewSectionMenuItem(child,path);
            }
        }
        List childList = null;
        if(childs!=null) {
            childList = new ArrayList(Arrays.asList(childs));
        }else {
            childList = new ArrayList();
        }
        MenuItemPB newMenu = new MenuItemPB();
        newMenu.setId(section.getId());
        newMenu.setNameKey("homepage.menu.section-new");
        newMenu.setPath(path);
        childList.add(newMenu);
        section.setChilds((MenuItemPB[]) childList.toArray(new MenuItemPB[0]));
    }
    protected String getQueryFilter() {
        return "allforuser";
    }

    private MenuItemPB[] makeMenuTree(String username, Section[] sections, String sectionPath, boolean useEnglish) {
        List result = new ArrayList();
        for (int i = 0; i < sections.length; i++) {
            Section section = sections[i];
            MenuItemPB pb = new MenuItemPB();
            pb.setId(section.getId());
            pb.setName(section.getName());
            if (useEnglish && StringUtil.asNull(section.getNameEnglish()) != null) {
                pb.setName(section.getNameEnglish());
            }
            pb.setPath(sectionPath);
            pb.setUser(username);
            if (section.getParent() == null || section.getParent().equals(new Integer(0))) {
                result.add(pb);
            } else {
                addChild(result, section.getParent(), pb);
            }
        }
        MenuItemPB[] resultCategories = (MenuItemPB[]) result.toArray(new MenuItemPB[0]);
        //Arrays.sort(resultCategories, SORT_BY_NAME);
        return resultCategories;
    }

    private boolean addChild(List list, Integer parent, MenuItemPB section) {
        for (Iterator it = list.iterator(); it.hasNext();) {
            MenuItemPB item = (MenuItemPB) it.next();
            if (parent.equals(item.getId())) {
                if (item.getChilds() == null) {
                    item.setChilds(new MenuItemPB[]{section});
                } else {
                    List childs = new ArrayList(Arrays.asList(item.getChilds()));
                    childs.add(section);
                    MenuItemPB[] childCategories = (MenuItemPB[]) childs.toArray(new MenuItemPB[0]);
                    //Arrays.sort(childCategories, SORT_BY_NAME);
                    item.setChilds(childCategories);
                }
                return true;
            } else if (item.getChilds() != null) {
                if (addChild(Arrays.asList(item.getChilds()), parent, section)) {
                    return true;
                }
            }
        }
        return false;
    }
}
