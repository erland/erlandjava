package erland.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

/**
 * Timer class that calls your object at regular intervals
 * @author Erland Isaksson
 */
public class Timer implements Runnable
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(Timer.class);
    /** Reference to object to call at each interval */
    private TimerListenerInterface listener;
    /** Number of milliseconds between two ping calls */
    private long delay;
    /** Timer thread that does the callback calls */
    private Thread t;

    /**
     * Creates a new timer object which calls the specified listener at
     * the specified interval
     * @param delay Number of milliseconds between calls
     * @param listener Listener object that should be called
     */
    public Timer(long delay, TimerListenerInterface listener)
    {
        this.delay = delay;
        this.listener = listener;
    }

    /**
     * Checks if the timer is running
     */
    public boolean isRunning()
    {
        return t!=null && t.isAlive();
    }
    /**
     * Stops the timer
     */
    public void stop()
    {
        t=null;
    }

    /**
     * Starts the timer after it has been stopped
     */
    public void start()
    {
        if(t==null) {
            t = new Thread(this);
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();
        }
    }
    /**
     * Sets the delay between timer ticks
     * @param delay Number of milliseconds between each timer tick
     */
    public void setDelay(long delay)
    {
        this.delay = delay;
    }
    /**
     * Gets the current delay between timer tics
     * @return Number of milliseconds between each timer tick
     */
    public long getDelay() {
        return delay;
    }
    /**
     * Main loop
     */
    public void run()
    {
        LOG.debug("Timer started");
        while(t==Thread.currentThread()) {
            try{
                Thread.sleep(delay);
            }catch(InterruptedException ie){}

            listener.tick();
        }
        LOG.debug("Timer stopped");
    }
}