package erland.webapp.dirgallery.loader;

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