package erland.webapp.cdcollection.logic.collection;

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

import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.util.Arrays;

public class CollectionHelper {
    public static Collection[] searchCollections(WebAppEnvironmentInterface environment, String entityName, String username, String filterName) {
        QueryFilter filter = new QueryFilter(filterName);
        filter.setAttribute("username", username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entityName).search(filter);
        return (Collection[]) Arrays.asList(entities).toArray(new Collection[0]);
    }
}