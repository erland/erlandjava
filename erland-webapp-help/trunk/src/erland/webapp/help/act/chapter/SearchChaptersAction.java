package erland.webapp.help.act.chapter;

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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.help.entity.chapter.Chapter;
import erland.webapp.help.fb.chapter.ChapterPB;
import erland.webapp.help.fb.application.SelectApplicationVersionFB;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

public class SearchChaptersAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectApplicationVersionFB fb = (SelectApplicationVersionFB) form;

        boolean useEnglish = true;
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        QueryFilter filter = new QueryFilter("allforapplicationandversion");
        filter.setAttribute("application",fb.getApplication());
        filter.setAttribute("version",fb.getVersion());
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("help-chapter").search(filter);

        Map parameters = new HashMap();
        parameters.put("application",fb.getApplication());
        parameters.put("version",fb.getVersion());

        ChapterPB[] pb = new ChapterPB[entities.length];
        for (int i = 0; i < pb.length; i++) {
            Chapter chapter = (Chapter) entities[i];

            pb[i] = new ChapterPB();
            PropertyUtils.copyProperties(pb[i],chapter);
            if(useEnglish && StringUtil.asNull(pb[i].getTitleEnglish())!=null) {
                pb[i].setTitle(pb[i].getTitleEnglish());
            }else {
                pb[i].setTitle(pb[i].getTitleNative());
            }
            if(useEnglish && StringUtil.asNull(pb[i].getHeaderEnglish())!=null) {
                pb[i].setHeader(pb[i].getHeaderEnglish());
            }else {
                pb[i].setHeader(pb[i].getHeaderNative());
            }
            if(useEnglish && StringUtil.asNull(pb[i].getFooterEnglish())!=null) {
                pb[i].setFooter(pb[i].getFooterEnglish());
            }else {
                pb[i].setFooter(pb[i].getFooterNative());
            }
            parameters.put("chapter",chapter.getChapter());
            parameters.put("chapterid",chapter.getId());
            ActionForward forward = mapping.findForward("chapter-update-link");
            if(forward!=null) {
                pb[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("chapter-remove-link");
            if(forward!=null) {
                pb[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
        }
    }
}
