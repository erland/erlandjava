package erland.webapp.common;
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

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.PropertyUtils;
import erland.webapp.common.fb.DescriptionIdPB;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.StringUtil;

public class DescriptionIdHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(DescriptionIdHelper.class);
    private static DescriptionIdHelper me;
    private Map map = new HashMap();

    private DescriptionIdHelper() {}

    public static DescriptionIdHelper getInstance() {
        if(me==null) {
            me = new DescriptionIdHelper();
        }
        return me;
    }

    public DescriptionId[] getDescriptionIdList(String entity) {
        return getDescriptionIdList(entity,true);
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

    public String getDescriptionEnglish(String entity, Integer id) {
        DescriptionId[] idList = getDescriptionIdList(entity);
        for (int i = 0; i < idList.length; i++) {
            DescriptionId descriptionId = idList[i];
            if(descriptionId.getId().equals(id)) {
                return descriptionId.getDescriptionEnglish();
            }
        }
        return null;
    }

    public void setDescription(String entity, Integer id, String description) {
        DescriptionId template = (DescriptionId) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create(entity);
        template.setType(entity);
        template.setId(id);
        template.setDescription(description);
        WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage(entity).store(template);
        // Make sure we reload the cache also
        getDescriptionIdList(entity,false);
    }

    private DescriptionId[] getDescriptionIdList(String entity, boolean useCached) {
        DescriptionId[] idList = null;
        if(useCached) {
            idList = (DescriptionId[]) map.get(entity);
        }
        if(idList==null) {
            QueryFilter filter = new QueryFilter("allfortype");
            filter.setAttribute("type",entity);
            EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage(entity).search(filter);
            idList = new DescriptionId[entities.length];
            for (int i = 0; i < entities.length; i++) {
                idList[i] = (DescriptionId) entities[i];
                LOG.debug("Load descriptionid "+idList[i].getType()+ " " + idList[i].getId() + " " + idList[i].getDescription());
            }
            map.put(entity,idList);
        }
        return idList;
    }

    public DescriptionIdPB[] getDescriptionIdPBList(String entity, boolean useEnglish, boolean useCached) {
        DescriptionId[] idList = getDescriptionIdList(entity,useCached);
        if(idList!=null) {
            DescriptionIdPB[] pbList = new DescriptionIdPB[idList.length];
            for (int i = 0; i < pbList.length; i++) {
                pbList[i] = new DescriptionIdPB();
                try {
                    PropertyUtils.copyProperties(pbList[i],idList[i]);
                } catch (IllegalAccessException e) {
                    LOG.error("Unable to copy properties",e);
                } catch (InvocationTargetException e) {
                    LOG.error("Unable to copy properties",e);
                } catch (NoSuchMethodException e) {
                    LOG.error("Unable to copy properties",e);
                }
                if(useEnglish && StringUtil.asNull(idList[i].getDescriptionEnglish())!=null) {
                    pbList[i].setDescription(idList[i].getDescriptionEnglish());
                }
            }
            return pbList;
        }else {
            return null;
        }
    }
}