package erland.webapp.gallery.fb.gallery;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class GalleryFB extends ValidatorForm {
    private Integer id;
    private String title;
    private String description;
    private Boolean official;
    private Integer topCategory;
    private Integer referencedGallery;
    private String[] categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return id!=null?id.toString():null;
    }

    public void setIdDisplay(String idDisplay) {
        try {
            this.id = Integer.valueOf(idDisplay);
        } catch (NumberFormatException e) {
            this.id = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return official!=null?official.toString():null;
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = Boolean.valueOf(officialDisplay);
    }

    public Integer getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategory = topCategory;
    }

    public String getTopCategoryDisplay() {
        return topCategory!=null?topCategory.toString():null;
    }

    public void setTopCategoryDisplay(String topCategoryDisplay) {
        try {
            this.topCategory = Integer.valueOf(topCategoryDisplay);
        } catch (NumberFormatException e) {
            this.topCategory = null;
        }
    }

    public Integer getReferencedGallery() {
        return referencedGallery;
    }

    public void setReferencedGallery(Integer referencedGallery) {
        this.referencedGallery = referencedGallery;
    }

    public String getReferencedGalleryDisplay() {
        return referencedGallery!=null?referencedGallery.toString():null;
    }

    public void setReferencedGalleryDisplay(String referencedGalleryDisplay) {
        try {
            this.referencedGallery = Integer.valueOf(referencedGalleryDisplay);
        } catch (NumberFormatException e) {
            this.referencedGallery = null;
        }
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        categories = null;
        description = null;
        official = Boolean.FALSE;
        referencedGallery = null;
        title = null;
        topCategory = null;
    }
}