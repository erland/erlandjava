package erland.webapp.help.act.application;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.help.entity.application.Application;
import erland.webapp.help.entity.application.ApplicationVersion;
import erland.webapp.help.entity.chapter.Chapter;
import erland.webapp.help.entity.chapter.ChapterAttribute;
import erland.webapp.help.fb.application.ApplicationFB;
import erland.webapp.help.fb.application.ApplicationVersionFB;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class EditApplicationVersionAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApplicationVersionFB fb = (ApplicationVersionFB) form;

        ApplicationVersion template = (ApplicationVersion) getEnvironment().getEntityFactory().create("help-applicationversion");
        PropertyUtils.copyProperties(template,fb);
        getEnvironment().getEntityStorageFactory().getStorage("help-applicationversion").store(template);
        if(StringUtil.asNull(fb.getPreviousVersion())!=null) {
            QueryFilter filter = new QueryFilter("allforapplicationandversion");
            filter.setAttribute("application",fb.getApplication());
            filter.setAttribute("version",fb.getVersion());
            getEnvironment().getEntityStorageFactory().getStorage("help-chapter").delete(filter);
            getEnvironment().getEntityStorageFactory().getStorage("help-chapterattribute").delete(filter);

            filter = new QueryFilter("allforapplicationandversion");
            filter.setAttribute("application",fb.getApplication());
            filter.setAttribute("version",fb.getPreviousVersion());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("help-chapter").search(filter);
            for (int i = 0; i < entities.length; i++) {
                Chapter chapter = (Chapter) entities[i];
                chapter.setId(null);
                chapter.setVersion(fb.getVersion());
                getEnvironment().getEntityStorageFactory().getStorage("help-chapter").store(chapter);
            }
            entities = getEnvironment().getEntityStorageFactory().getStorage("help-chapterattribute").search(filter);
            for (int i = 0; i < entities.length; i++) {
                ChapterAttribute chapter = (ChapterAttribute) entities[i];
                chapter.setId(null);
                chapter.setVersion(fb.getVersion());
                getEnvironment().getEntityStorageFactory().getStorage("help-chapterattribute").store(chapter);
            }
        }
    }
}