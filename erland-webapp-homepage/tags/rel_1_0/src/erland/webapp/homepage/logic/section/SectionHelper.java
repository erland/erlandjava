package erland.webapp.homepage.logic.section;

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

import erland.webapp.homepage.fb.section.SectionPB;
import erland.webapp.homepage.fb.section.SectionFB;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class SectionHelper {
    public static SectionPB[] searchSections(String filterName, String username, boolean useEnglish) {
        QueryFilter filter = new QueryFilter(filterName);
        filter.setAttribute("username", username);
        EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("homepage-section").search(filter);
        SectionPB[] pb = new SectionPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new SectionPB();
            try {
                PropertyUtils.copyProperties(pb[i], entities[i]);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            if(useEnglish && StringUtil.asNull(pb[i].getNameEnglish())!=null) {
                pb[i].setName(pb[i].getNameEnglish());
            }
            if(useEnglish && StringUtil.asNull(pb[i].getTitleEnglish())!=null) {
                pb[i].setTitle(pb[i].getTitleEnglish());
            }
            if(useEnglish && StringUtil.asNull(pb[i].getDescriptionEnglish())!=null) {
                pb[i].setDescription(pb[i].getDescriptionEnglish());
            }
        }
        return pb;
    }
}