package erland.webapp.tvguide.act.movie;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.QueryFilter;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.fb.channel.ChannelFB;
import erland.webapp.tvguide.fb.movie.MovieFB;
import erland.webapp.tvguide.logic.movie.MovieHelper;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class EditMovieAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MovieFB fb = (MovieFB) form;

        Movie movie = MovieHelper.getMovie(getEnvironment(),fb.getTitle());
        String reviewLink = movie.getLink();
        MovieHelper.refreshMovie(getEnvironment(),fb.getTitle(),fb.getLink());
        Movie movieUpdated = MovieHelper.getMovie(getEnvironment(),fb.getTitle());

        QueryFilter filter = new QueryFilter("allforreviewlink");
        filter.setAttribute("reviewlink",reviewLink);
        Program program = (Program) getEnvironment().getEntityFactory().create("tvguide-program");
        if(movieUpdated!=null) {
            program.setReview(movieUpdated.getReview());
            program.setReviewLink(movieUpdated.getLink());
        }else {
            program.setReview(new Integer(0));
            program.setReviewLink("");
        }
        getEnvironment().getEntityStorageFactory().getStorage("tvguide-program").update(filter,program);
    }
}
