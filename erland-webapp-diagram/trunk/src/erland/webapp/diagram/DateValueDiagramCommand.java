package erland.webapp.diagram;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.CommandResponseInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.diagram.DateValueDiagram;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Iterator;


public class DateValueDiagramCommand implements CommandInterface, CommandResponseInterface {
    BufferedImage img;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            ImageIO.write(img,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
    private BufferedImage createImage(int width, int height) {
        // Create image
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);    // Image object to paint with
        Graphics2D g = img.createGraphics();    // Get graphics context
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);     // Anti-alias the painting
        // First set background color to white by painting a filled rectangle
        g.setPaint(Color.white);
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, height);
        g.fill(rectangle);
        g.dispose();
        return img;
    }
    private int getNumberFromString(String str,int defaultValue) {
        if(str!=null) {
            try {
                defaultValue = Integer.valueOf(str).intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        return defaultValue;
    }
    public String execute(HttpServletRequest request) {
        CommandInterface cmd = environment.getCommandFactory().create(request.getParameter("dataproducer"));
        if(cmd!=null && cmd instanceof DateValueSeriesContainerInterface) {
            cmd.execute(request);

            Iterator series = ((DateValueSeriesContainerInterface) cmd).getSeries();
            DateValueDiagram diagram = new DateValueDiagram();
            while(series.hasNext()) {
                DateValueSerieInterface serie = (DateValueSerieInterface) series.next();
                diagram.addPlotData(serie.getName(),serie.getSerie(DateValueSerieType.get(request.getParameter("type"))));
            }

            int width = getNumberFromString(request.getParameter("width"),800);
            int height = getNumberFromString(request.getParameter("height"),200);

            img = createImage(width,height);
            diagram.plot(img);
        }
        return null;
    }
}
