package erland.game;

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
