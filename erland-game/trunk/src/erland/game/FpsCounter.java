package erland.game;

import java.awt.*;

public class FpsCounter {
    String fps;
    int frameCount;
    int interval;
    long lastTime;


    public FpsCounter(int interval)
    {
        this.interval = interval;
        lastTime = System.currentTimeMillis();
        frameCount=0;
        fps="??";
    }

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

    public void draw(Graphics g,Color c, int x, int y)
    {
        Color old = g.getColor();
        g.setColor(c);
        g.drawString(fps,x,y);
        g.setColor(old);
    }
}
