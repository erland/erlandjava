package erland.webapp.gallery;
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

public class DescriptionTagHelper {
    private static DescriptionTagHelper me;
    private WebAppEnvironmentInterface environment;
    private Map map = new HashMap();

    private DescriptionTagHelper() {}

    public static DescriptionTagHelper getInstance() {
        if(me==null) {
            me = new DescriptionTagHelper();
        }
        return me;
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public DescriptionTag[] getDescriptionTagList(String entity) {
        DescriptionTag[] tagList = (DescriptionTag[]) map.get(entity);
        if(tagList==null) {
            QueryFilter filter = new QueryFilter("allfortype");
            filter.setAttribute("type",entity);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(entity).search(filter);
            tagList = new DescriptionTag[entities.length];
            for (int i = 0; i < entities.length; i++) {
                tagList[i] = (DescriptionTag) entities[i];
            }
            map.put(entity,tagList);
        }
        return tagList;
    }

    public String getDescription(String entity, String tag) {
        DescriptionTag[] tagList = getDescriptionTagList(entity);
        for (int i = 0; i < tagList.length; i++) {
            DescriptionTag descriptionTag = tagList[i];
            if(descriptionTag.getTag().equals(tag)) {
                return descriptionTag.getDescription();
            }
        }
        return null;
    }
}