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

import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.help.entity.chapter.Chapter;
import erland.webapp.help.entity.chapter.ChapterAttribute;
import erland.webapp.help.fb.chapter.ChapterAttributePB;
import erland.webapp.help.fb.chapter.ChapterPB;
import erland.webapp.help.fb.chapter.SelectChapterFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewChapterInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectChapterFB fb = (SelectChapterFB) form;
        QueryFilter filter = new QueryFilter("allforapplicationandversionandchapter");
        filter.setAttribute("application",fb.getApplication());
        filter.setAttribute("version",fb.getVersion());
        filter.setAttribute("chapter",fb.getChapter());
        EntityInterface[] chapterEntities = getEnvironment().getEntityStorageFactory().getStorage("help-chapter").search(filter);
        if(chapterEntities.length==0) {
            saveErrors(request, Arrays.asList(new String[]{"help.chapter.view.chapter-dont-exist"}));
            return;
        }
        Chapter chapter = (Chapter) chapterEntities[0];

        boolean useEnglish = true;
        String nativeLanguage = getEnvironment().getConfigurableResources().getParameter("nativelanguage");
        if(nativeLanguage!=null && nativeLanguage.equals(getLocale(request).getLanguage())) {
            useEnglish = false;
        }

        ChapterPB pb = new ChapterPB();
        PropertyUtils.copyProperties(pb,chapter);
        if(useEnglish && StringUtil.asNull(pb.getTitleEnglish())!=null) {
            pb.setTitle(pb.getTitleEnglish());
        }else {
            pb.setTitle(pb.getTitleNative());
        }
        if(useEnglish && StringUtil.asNull(pb.getHeaderEnglish())!=null) {
            pb.setHeader(pb.getHeaderEnglish());
        }else {
            pb.setHeader(pb.getHeaderNative());
        }
        if(useEnglish && StringUtil.asNull(pb.getFooterEnglish())!=null) {
            pb.setFooter(pb.getFooterEnglish());
        }else {
            pb.setFooter(pb.getFooterNative());
        }
        Map parameters = new HashMap();
        parameters.put("application",fb.getApplication());
        parameters.put("version",fb.getVersion());
        parameters.put("chapter",fb.getChapter());
        parameters.put("chapterid",chapter.getId());
        ActionForward forward = mapping.findForward("chapter-update-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("chapter-remove-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("chapter-newattribute-link");
        if(forward!=null) {
            pb.setNewAttributeLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        filter=new QueryFilter("allforapplicationandversionandchapter");
        filter.setAttribute("application",chapter.getApplication());
        filter.setAttribute("version",chapter.getVersion());
        filter.setAttribute("chapter",chapter.getChapter());

        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("help-chapterattribute").search(filter);
        ChapterAttributePB[] attributesPB = new ChapterAttributePB[entities.length];
        ActionForward updateForward = mapping.findForward("chapterattribute-update-link");
        ActionForward removeForward = mapping.findForward("chapterattribute-remove-link");
        for (int i = 0; i < attributesPB.length; i++) {
            ChapterAttribute attribute = (ChapterAttribute) entities[i];
            attributesPB[i] = new ChapterAttributePB();
            PropertyUtils.copyProperties(attributesPB[i],attribute);
            if(useEnglish && StringUtil.asNull(attributesPB[i].getNameEnglish())!=null) {
                attributesPB[i].setName(attributesPB[i].getNameEnglish());
            }else {
                attributesPB[i].setName(attributesPB[i].getNameNative());
            }
            if(useEnglish && StringUtil.asNull(attributesPB[i].getDescriptionEnglish())!=null) {
                attributesPB[i].setDescription(attributesPB[i].getDescriptionEnglish());
            }else {
                attributesPB[i].setDescription(attributesPB[i].getDescriptionNative());
            }
            parameters.put("attributeid",attribute.getId());
            if(updateForward!=null) {
                attributesPB[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(updateForward.getPath(),parameters));
            }
            if(removeForward!=null) {
                attributesPB[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(removeForward.getPath(),parameters));
            }
        }
        pb.setAttributes(attributesPB);
        request.setAttribute("chapterPB",pb);
    }
}
