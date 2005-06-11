package erland.webapp.tvguide.act.movie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.image.ImageWriteHelper;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.webapp.tvguide.fb.movie.SelectMovieFB;
import erland.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

public class LoadMovieCoverAction extends BaseAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(LoadMovieCoverAction.class);
    private final static String IMAGE_FILE = LoadMovieCoverAction.class+"-imageFile";

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectMovieFB fb = (SelectMovieFB) form;
        String cacheDir = StringUtil.asNull(getEnvironment().getConfigurableResources().getParameter("cover.cache"));
        if(cacheDir!=null) {
            setImageFile(request,cacheDir+"/"+fb.getId().toLowerCase().replaceAll(":","-")+".jpg");
        }
    }

    protected void setImageFile(HttpServletRequest request, String imageFile) {
        request.setAttribute(IMAGE_FILE,imageFile);
    }

    public String getImageFile(HttpServletRequest request) {
        return (String)request.getAttribute(IMAGE_FILE);
    }

    protected ActionForward findSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            if (!ImageWriteHelper.writeImage(getEnvironment(), getImageFile(request), response.getOutputStream())) {
                return findFailure(mapping,form,request,response);
            }
        } catch (IOException e) {
            LOG.error("Unable to read file "+getImageFile(request),e);
        }
        return null;
    }
}
