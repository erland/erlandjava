package erland.webapp.gallery.fb.skin;

import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class SkinFB extends BaseFB {
    private String id;
    private String layout;
    private String layoutSingle;
    private String header;
    private String menu;
    private String viewPictures;
    private String viewPicture;
    private String search;
    private String stylesheet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayoutSingle() {
        return layoutSingle;
    }

    public void setLayoutSingle(String layoutSingle) {
        this.layoutSingle = layoutSingle;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getViewPictures() {
        return viewPictures;
    }

    public void setViewPictures(String viewPictures) {
        this.viewPictures = viewPictures;
    }

    public String getViewPicture() {
        return viewPicture;
    }

    public void setViewPicture(String viewPicture) {
        this.viewPicture = viewPicture;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        layout = null;
        layoutSingle = null;
        menu = null;
        header = null;
        search = null;
        viewPicture = null;
        viewPictures = null;
        stylesheet = null;
    }
}