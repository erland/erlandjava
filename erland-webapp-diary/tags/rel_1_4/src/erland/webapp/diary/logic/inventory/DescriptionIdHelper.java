package erland.webapp.diary.logic.inventory;
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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.diary.entity.inventory.DescriptionId;
import erland.webapp.diary.entity.inventory.DescriptionId;

import java.util.Map;
import java.util.HashMap;

public class DescriptionIdHelper {
    private static Map map = new HashMap();

    public static synchronized DescriptionId[] getDescriptionIdList(String entity) {
        DescriptionId[] idList = (DescriptionId[]) map.get(entity);
        if(idList==null) {
            EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage(entity).search(new QueryFilter("all"));
            idList = new DescriptionId[entities.length];
            for (int i = 0; i < entities.length; i++) {
                idList[i] = (DescriptionId) entities[i];
            }
            map.put(entity,idList);
        }
        return idList;
    }

    public static String getDescription(String entity, Integer id) {
        DescriptionId[] idList = getDescriptionIdList(entity);
        for (int i = 0; i < idList.length; i++) {
            DescriptionId descriptionId = idList[i];
            if(descriptionId.getId().equals(id)) {
                return descriptionId.getDescription();
            }
        }
        return null;
    }
}
