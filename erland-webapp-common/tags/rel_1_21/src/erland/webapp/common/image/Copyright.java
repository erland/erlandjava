package erland.webapp.common.image;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;

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

/**
 * Draws a simple copyright logotype on an image
 */
public class Copyright implements CopyrightCreatorInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(Copyright.class);
    private static final int COPYRIGHT_WIDTH = 250;
    private static final int COPYRIGHT_HEIGHT = COPYRIGHT_WIDTH*3/4;
    private String text;
    private CopyrightPosition position;
    private float transparency = 0.4f;
    private static final int COPYRIGHT_OFFSET = 5;

    public Copyright(String text, CopyrightPosition position) {
        this.text = text;
        this.position = position;
    }
    public Copyright(String text, CopyrightPosition position, float transparency) {
        this.text = text;
        this.position = position;
        this.transparency = transparency;
    }
    public void draw(BufferedImage image) {
        int finalWidth = image.getWidth();
        int finalHeight = image.getHeight();
        if (finalWidth >= COPYRIGHT_WIDTH || finalHeight>= COPYRIGHT_HEIGHT) {
            if(text!=null && text.length()>0) {
                Graphics2D g2= image.createGraphics();
                LOG.trace("Drawing copyright");
                FontMetrics metrics = g2.getFontMetrics();
                Rectangle2D rc = metrics.getStringBounds(text, g2);
                g2.setColor(Color.white);
                int x;
                int y;
                if(position==CopyrightPosition.TOP_LEFT) {
                    x = COPYRIGHT_OFFSET;
                    y = COPYRIGHT_OFFSET;
                }else if(position==CopyrightPosition.TOP_RIGHT) {
                    x = finalWidth - (int) rc.getWidth() - COPYRIGHT_OFFSET * 3;
                    y = COPYRIGHT_OFFSET;
                }else if(position==CopyrightPosition.BOTTOM_LEFT) {
                    x = COPYRIGHT_OFFSET;
                    y = finalHeight - (int) rc.getHeight() - COPYRIGHT_OFFSET * 3;
                }else {
                    x = finalWidth - (int) rc.getWidth() - COPYRIGHT_OFFSET * 3;
                    y = finalHeight - (int) rc.getHeight() - COPYRIGHT_OFFSET * 3;
                }
                g2.draw3DRect(x, y, (int) rc.getWidth() + COPYRIGHT_OFFSET * 2, (int) rc.getHeight() + COPYRIGHT_OFFSET * 2, true);
                g2.setColor(new Color(0f, 0f, 0f, transparency));
                g2.fillRect(x+1, y+1, (int) rc.getWidth() + COPYRIGHT_OFFSET * 2-1, (int) rc.getHeight() + COPYRIGHT_OFFSET * 2-1);
                g2.setColor(Color.white);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.drawString(text, x + COPYRIGHT_OFFSET, y + (int) rc.getHeight() + COPYRIGHT_OFFSET - metrics.getDescent());
                LOG.trace("Copyright drawed");
            }
        }
    }
}
