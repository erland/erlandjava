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
import erland.webapp.common.EntityInterface;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.fb.channel.ChannelFB;
import erland.webapp.tvguide.fb.movie.MovieFB;
import erland.webapp.tvguide.logic.movie.MovieHelper;
import erland.webapp.tvguide.logic.program.ProgramHelper;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class RefreshAllMoviesAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-movie").search(new QueryFilter("all"));
        for (int i = 0; i < entities.length; i++) {
            Movie entity = (Movie) entities[i];
            if(StringUtil.asNull(entity.getLink())!=null) {
                MovieHelper.refreshMovie(getEnvironment(),entity.getTitle(),entity.getLink());
                Movie movieUpdated = MovieHelper.getMovie(getEnvironment(),entity.getTitle());

                QueryFilter filter;
                filter = new QueryFilter("allforreviewlinkandname");
                filter.setAttribute("reviewlink",movieUpdated.getLink());
                filter.setAttribute("name",movieUpdated.getTitle());

                Program program = (Program) getEnvironment().getEntityFactory().create("tvguide-program");
                if(movieUpdated!=null) {
                    program.setReview(movieUpdated.getReview());
                    program.setReviewLink(movieUpdated.getLink());
                    program.setCategory(movieUpdated.getCategory());
                }else {
                    program.setReview(new Integer(0));
                    program.setReviewLink("");
                    program.setCategory("");
                }
                getEnvironment().getEntityStorageFactory().getStorage("tvguide-program").update(filter,program);
            }
        }
    }
}
