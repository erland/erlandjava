package erland.webapp.gallery.act.gallery;

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
import erland.webapp.gallery.fb.gallery.SelectGalleryFB;
import erland.webapp.gallery.fb.gallery.MenuItemPB;
import erland.webapp.gallery.fb.gallery.CategoryMenuItemPB;
import erland.webapp.gallery.fb.gallery.GalleryMenuItemPB;
import erland.webapp.gallery.act.gallery.category.CategoryHelper;
import erland.webapp.gallery.entity.gallery.GalleryInterface;
import erland.webapp.gallery.entity.gallery.category.Category;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class SearchGalleriesAndCategoriesAction extends BaseAction {
    protected Comparator SORT_BY_NAME = new Comparator() {
        public int compare(Object o1, Object o2) {
            if(o1 instanceof MenuItemPB && o2 instanceof MenuItemPB) {
                return ((MenuItemPB)o1).getName().compareTo(((MenuItemPB)o2).getName());
            }
            return 0;
        }
    };
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectGalleryFB fb = (SelectGalleryFB) form;
        String username = request.getRemoteUser();
        if (fb != null) {
            username = fb.getUser();
        }
        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage(getEntityName()).search(filter);
        GalleryMenuItemPB[] pb = new GalleryMenuItemPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            GalleryInterface entity = (GalleryInterface)entities[i];
            pb[i] = new GalleryMenuItemPB();
            Integer gallery = entity.getId();
            if (entity.getReferencedGallery() != null && !entity.getReferencedGallery().equals(new Integer(0))) {
                gallery = entity.getReferencedGallery();
            }
            pb[i].setId(entity.getId());
            pb[i].setName(entity.getTitle());
            pb[i].setUser(username);
            pb[i].setGallery(entity.getId());
            ActionForward galleryForward = mapping.findForward("gallery");
            if(galleryForward!=null) {
                pb[i].setPath(galleryForward.getPath());
            }
            ActionForward categoryForward = mapping.findForward("category");
            String categoryPath = null;
            if(categoryForward!=null) {
                categoryPath = categoryForward.getPath();
            }
            Category[] categories = CategoryHelper.searchCategories(getEnvironment(),gallery,entity.getId(),entity.getTopCategory(),getCategoriesFilter(),getNoTopCategoriesFilter());
            MenuItemPB[] categoriesPB = makeCategoryTree(entity.getId(),username, categories,categoryPath,entity.getTopCategory());
            pb[i].setChilds(categoriesPB);
        }
        request.getSession().setAttribute("menuGalleriesAndCategoriesPB", pb);
    }

    private MenuItemPB[] makeCategoryTree(Integer galleryId, String username, Category[] categories, String categoryPath, Integer topCategory) {
        List result = new ArrayList();
        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];
            CategoryMenuItemPB pb = new CategoryMenuItemPB();
            pb.setId(category.getCategory());
            pb.setName(category.getName());
            pb.setPath(categoryPath);
            pb.setUser(username);
            pb.setGallery(galleryId);
            pb.setCategory(category.getCategory());
            if(category.getParentCategory()==null || category.getParentCategory().equals(topCategory)) {
                result.add(pb);
            }else {
                addChild(result,category.getParentCategory(),pb);
            }
        }
        MenuItemPB[] resultCategories = (MenuItemPB[]) result.toArray(new MenuItemPB[0]);
        Arrays.sort(resultCategories,SORT_BY_NAME);
        return resultCategories;
    }

    private boolean addChild(List list, Integer parent, MenuItemPB category) {
        for(Iterator it=list.iterator();it.hasNext();) {
            MenuItemPB item = (MenuItemPB) it.next();
            if(parent.equals(item.getId())) {
                if(item.getChilds()==null) {
                    item.setChilds(new MenuItemPB[] {category});
                }else {
                    List childs = new ArrayList(Arrays.asList(item.getChilds()));
                    childs.add(category);
                    MenuItemPB[] childCategories = (MenuItemPB[]) childs.toArray(new MenuItemPB[0]);
                    Arrays.sort(childCategories,SORT_BY_NAME);
                    item.setChilds(childCategories);
                }
                return true;
            }else if(item.getChilds()!=null) {
                if(addChild(Arrays.asList(item.getChilds()),parent,category)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String getCategoriesFilter() {
        return "allforgallerywithtopcategory";
    }

    protected String getNoTopCategoriesFilter() {
        return "allforgallery";
    }

    protected String getEntityName() {
        return "gallery-gallery";
    }

    protected String getQueryFilter() {
        return "allforuser";
    }
}
