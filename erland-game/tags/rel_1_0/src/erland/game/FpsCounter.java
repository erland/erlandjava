package erland.game;
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

import java.awt.*;

/**
 * Represents a simple class that calculates and draw
 * a frame rate counter
 * @author Erland Isaksson
 */
public class FpsCounter {
    /** The last calculated frame rate */
    private String fps;
    /** Number of frames since last frame rate calculation */
    private int frameCount;
    /** Number of frames between calculations */
    private int interval;
    /** The time when the last frame rate was calculated */
    private long lastTime;


    /**
     * Creates a new frame rate calculation object
     * @param interval Number of frames between frame rate calculations
     */
    public FpsCounter(int interval)
    {
        this.interval = interval;
        lastTime = System.currentTimeMillis();
        frameCount=0;
        fps="??";
    }

    /**
     * This method should be called each time a new frame is drawn
     */
    public void update()
    {
        frameCount++;
        if(frameCount>=interval) {
            long time = System.currentTimeMillis();
            fps = Long.toString(frameCount*1000/(time-lastTime));
            frameCount=0;
            lastTime = time;
        }
    }

    /**
     * Draw the last calculated frame rate to the screen
     * @param g Graphics object to draw on
     * @param c Color of frame rate counter
     * @param x X position of the frame rate counter
     * @param y Y position of the frame rate counter
     */
    public void draw(Graphics g,Color c, int x, int y)
    {
        Color old = g.getColor();
        g.setColor(c);
        g.drawString(fps,x,y);
        g.setColor(old);
    }

    public String getFps() {
        return fps;
    }
}
