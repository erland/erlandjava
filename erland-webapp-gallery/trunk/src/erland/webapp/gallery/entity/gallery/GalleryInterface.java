package erland.webapp.gallery.entity.gallery;

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

public interface GalleryInterface {
    Integer getId();

    void setId(Integer id);

    String getUsername();

    void setUsername(String username);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Boolean getOfficial();

    void setOfficial(Boolean official);

    Integer getTopCategory();

    void setTopCategory(Integer topCategory);

    Integer getReferencedGallery();

    void setReferencedGallery(Integer referencedGallery);
}