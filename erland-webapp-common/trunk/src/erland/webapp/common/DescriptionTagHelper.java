package erland.webapp.common;
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
import erland.webapp.common.fb.DescriptionTagPB;
import erland.util.StringUtil;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.PropertyUtils;

public class DescriptionTagHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(DescriptionTagHelper.class);
    private static DescriptionTagHelper me;
    private Map map = new HashMap();

    private DescriptionTagHelper() {}

    public static DescriptionTagHelper getInstance() {
        if(me==null) {
            me = new DescriptionTagHelper();
        }
        return me;
    }

    public DescriptionTag[] getDescriptionTagList(String entity) {
        return getDescriptionTagList(entity,true);
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

    public String getDescriptionEnglish(String entity, String tag) {
        DescriptionTag[] tagList = getDescriptionTagList(entity);
        for (int i = 0; i < tagList.length; i++) {
            DescriptionTag descriptionTag = tagList[i];
            if(descriptionTag.getTag().equals(tag)) {
                return descriptionTag.getDescriptionEnglish();
            }
        }
        return null;
    }
    public void setDescription(String entity, String tag, String description) {
        DescriptionTag template = (DescriptionTag) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create(entity);
        template.setType(entity);
        template.setTag(tag);
        template.setDescription(description);
        WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage(entity).store(template);
        // Make sure we reload the cache also
        getDescriptionTagList(entity,false);
    }

    public DescriptionTag[] getDescriptionTagList(String entity, boolean useCached) {
        DescriptionTag[] tagList = null;
        if(useCached) {
            tagList = (DescriptionTag[]) map.get(entity);
        }
        if(tagList==null) {
            QueryFilter filter = new QueryFilter("allfortype");
            filter.setAttribute("type",entity);
            EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage(entity).search(filter);
            tagList = new DescriptionTag[entities.length];
            for (int i = 0; i < entities.length; i++) {
                tagList[i] = (DescriptionTag) entities[i];
                LOG.debug("Load descriptiontag "+tagList[i].getType()+ " " + tagList[i].getTag() + " " + tagList[i].getDescription());
            }
            map.put(entity,tagList);
        }
        return tagList;
    }
    public DescriptionTagPB[] getDescriptionTagPBList(String entity, boolean useEnglish, boolean useCached) {
        DescriptionTag[] tagList = getDescriptionTagList(entity,useCached);
        if(tagList!=null) {
            DescriptionTagPB[] pbList = new DescriptionTagPB[tagList.length];
            for (int i = 0; i < pbList.length; i++) {
                pbList[i] = new DescriptionTagPB();
                try {
                    PropertyUtils.copyProperties(pbList[i],tagList[i]);
                } catch (IllegalAccessException e) {
                    LOG.error("Unable to copy properties",e);
                } catch (InvocationTargetException e) {
                    LOG.error("Unable to copy properties",e);
                } catch (NoSuchMethodException e) {
                    LOG.error("Unable to copy properties",e);
                }
                if(useEnglish && StringUtil.asNull(tagList[i].getDescriptionEnglish())!=null) {
                    pbList[i].setDescription(tagList[i].getDescriptionEnglish());
                }
            }
            return pbList;
        }else {
            return null;
        }
    }
}