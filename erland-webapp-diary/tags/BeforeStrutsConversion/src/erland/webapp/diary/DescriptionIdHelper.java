package erland.webapp.diary;
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

import java.util.Map;
import java.util.HashMap;

public class DescriptionIdHelper {
    private static DescriptionIdHelper me;
    private WebAppEnvironmentInterface environment;
    private Map map = new HashMap();

    private DescriptionIdHelper() {}

    public static DescriptionIdHelper getInstance() {
        if(me==null) {
            me = new DescriptionIdHelper();
        }
        return me;
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public DescriptionId[] getDescriptionIdList(String entity) {
        DescriptionId[] idList = (DescriptionId[]) map.get(entity);
        if(idList==null) {
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entity).search(new QueryFilter("all"));
            idList = new DescriptionId[entities.length];
            for (int i = 0; i < entities.length; i++) {
                idList[i] = (DescriptionId) entities[i];
            }
            map.put(entity,idList);
        }
        return idList;
    }

    public String getDescription(String entity, Integer id) {
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
