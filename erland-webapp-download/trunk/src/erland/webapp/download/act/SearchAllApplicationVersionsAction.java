package erland.webapp.download.act;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.download.fb.ApplicationVersionPB;
import erland.webapp.download.fb.ApplicationIdFB;
import erland.webapp.download.fb.ApplicationFileFB;
import erland.webapp.download.entity.ApplicationVersion;

import java.util.*;

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

public class SearchAllApplicationVersionsAction extends SearchApplicationVersionsAction {
    protected QueryFilter getFilter(ActionForm actionForm) {
        ApplicationIdFB fb = (ApplicationIdFB) actionForm;
        QueryFilter filter = new QueryFilter("all");
        String mainDir = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("basedirectory");
        filter.setAttribute("directory",mainDir);
        filter.setAttribute("extensions", ".zip,.exe");
        filter.setAttribute("tree",Boolean.TRUE);
        return filter;
    }
}