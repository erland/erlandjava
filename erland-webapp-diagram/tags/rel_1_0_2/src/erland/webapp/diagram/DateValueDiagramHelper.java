package erland.webapp.diagram;

import erland.webapp.common.image.ImageWriteHelper;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;
import java.io.IOException;

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

public class DateValueDiagramHelper {
    public static void drawDiagram(DateValueSerieInterface series[], OutputStream output) throws IOException {
        drawDiagram(series,800,200, null, output);
    }
    public static void drawDiagram(DateValueSerieInterface series[], int width, int height, DateValueSerieType type, OutputStream output) throws IOException {
        DateValueDiagram diagram = new DateValueDiagram();
        for (int i = 0; i < series.length; i++) {
            DateValueSerieInterface serie = (DateValueSerieInterface) series[i];
            diagram.addPlotData(serie.getName(),serie.getSerie(type));
        }

        BufferedImage img = createImage(width,height);
        diagram.plot(img);
        ImageWriteHelper.writeImageToOutput(img,output);
    }

    private static BufferedImage createImage(int width, int height) {
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
}