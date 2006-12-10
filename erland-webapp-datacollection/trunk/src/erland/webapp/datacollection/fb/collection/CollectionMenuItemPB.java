package erland.webapp.datacollection.fb.collection;

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

public class CollectionMenuItemPB extends MenuItemPB {
    private String collectionDisplay;

    public Integer getCollection() {
        return StringUtil.asInteger(collectionDisplay,null);
    }

    public void setCollection(Integer collection) {
        this.collectionDisplay = StringUtil.asString(collection,null);
    }

    public String getCollectionDisplay() {
        return collectionDisplay;
    }

    public void setCollectionDisplay(String collectionDisplay) {
        this.collectionDisplay = collectionDisplay;
    }
}