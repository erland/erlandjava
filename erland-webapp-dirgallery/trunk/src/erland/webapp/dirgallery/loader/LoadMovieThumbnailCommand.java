package erland.webapp.dirgallery.loader;
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

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LoadMovieThumbnailCommand extends LoadThumbnailCommand {
    protected BufferedImage createThumbnail(HttpServletRequest request, URL url, int requestedWidth, BufferedImage thumbnail) throws IOException {
        MovieThumbnail thumbnailGenerator = new MovieThumbnail();
        String noOfColsString = request.getParameter("cols");
        Integer noOfCols = new Integer(2);
        if (noOfColsString != null && noOfColsString.length() > 0) {
            noOfCols = Integer.valueOf(noOfColsString);
        }
        String noOfRowsString = request.getParameter("rows");
        Integer noOfRows = new Integer(2);
        if (noOfRowsString != null && noOfRowsString.length() > 0) {
            noOfRows = Integer.valueOf(noOfRowsString);
        }
        return thumbnailGenerator.create(url, noOfCols.intValue(), noOfRows.intValue(), requestedWidth);
    }
}