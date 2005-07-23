package erland.webapp.cdcollection.act.media;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.common.image.ImageThumbnail;
import erland.webapp.cdcollection.fb.media.MediaFB;
import erland.webapp.cdcollection.fb.media.CoverFB;
import erland.webapp.cdcollection.entity.media.Media;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URL;

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

public class LoadMediaCoverAction extends BaseAction {
    private final static String COVER_FILE = LoadMediaCoverAction.class+"-coverFile";
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoverFB fb = (CoverFB) form;
        String coverDir = getEnvironment().getConfigurableResources().getParameter("cover.cache");
        String coverFile = null;
        Media media = (Media) getEnvironment().getEntityFactory().create("cdcollection-media");
        media.setId(fb.getId());
        media = (Media) getEnvironment().getEntityStorageFactory().getStorage("cdcollection-media").load(media);
        if(media==null) {
            saveErrors(request, Arrays.asList(new String[]{"cdcollection.media.media-dont-exist"}));
            return;
        }
        if(StringUtil.asNull(media.getUniqueMediaId())!=null) {
            coverFile = coverDir+"/"+media.getUniqueMediaId().replace(' ','_')+".jpg";
            File file = new File(coverFile);
            if(!file.exists()) {
                coverFile = null;
            }
        }
        if(coverFile==null) {
            coverFile = coverDir+"/"+media.getId()+".jpg";
            File file = new File(coverFile);
            if(!file.exists()) {
                coverFile = null;
            }
        }
        if(coverFile==null && StringUtil.asNull(media.getCoverUrl())!=null) {
            if(StringUtil.asNull(media.getUniqueMediaId())!=null) {
                coverFile = coverDir+"/"+media.getUniqueMediaId().replace(' ','_')+".jpg";
            }else {
                coverFile = coverDir+"/"+media.getId()+".jpg";
            }
            OutputStream output = new FileOutputStream(coverFile);
            if(!ImageWriteHelper.writeImage(getEnvironment(),media.getCoverUrl(),output)) {
                saveErrors(request, Arrays.asList(new String[]{"cdcollection.media.media-cover-dont-exist"}));
                output.close();
                return;
            }
            output.close();
        }
        setCoverFile(request,coverFile);
    }
    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        CoverFB fb = (CoverFB) form;
        ImageThumbnail thumbnailCreator = null;
        thumbnailCreator = new ImageThumbnail(Boolean.TRUE);
        Integer coverWidth = fb.getWidth();
        Integer coverHeight = null;
        if(coverWidth==null) {
            coverWidth = StringUtil.asInteger(getEnvironment().getConfigurableResources().getParameter("cover.width"),null);
            coverHeight = StringUtil.asInteger(getEnvironment().getConfigurableResources().getParameter("cover.height"),null);
        }
        Float coverCompression = StringUtil.asFloat(getEnvironment().getConfigurableResources().getParameter("cover.compression"),null);
        try {
            response.setContentType("image/jpeg");
            if (!ImageWriteHelper.writeThumbnail(getEnvironment(), coverWidth, coverHeight,Boolean.TRUE, coverCompression, "", getCoverFile(request), null, thumbnailCreator, response.getOutputStream())) {
                return findFailure(mapping,form,request,response);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }
    protected String getCoverFile(HttpServletRequest request) {
        return (String)request.getAttribute(COVER_FILE);
    }
    protected void setCoverFile(HttpServletRequest request,String coverFile) {
        request.setAttribute(COVER_FILE,coverFile);
    }
}