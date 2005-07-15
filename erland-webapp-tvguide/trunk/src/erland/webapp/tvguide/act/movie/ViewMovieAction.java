package erland.webapp.tvguide.act.movie;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.webapp.tvguide.fb.movie.MovieFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class ViewMovieAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MovieFB fb = (MovieFB) form;
        Movie template = (Movie) getEnvironment().getEntityFactory().create("tvguide-movie");
        template.setTitle(fb.getTitle().toLowerCase());
        Movie movie = (Movie) getEnvironment().getEntityStorageFactory().getStorage("tvguide-movie").load(template);
        if(movie!=null) {
            PropertyUtils.copyProperties(fb, movie);
        }else {
            PropertyUtils.copyProperties(fb, template);
        }
    }
}
