package erland.util;

/**
 * Timer class that calls your object at regular intervals
 * @author Erland Isaksson
 */
public class Timer implements Runnable
{
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
        Log.println(this,"Timer started");
        while(t==Thread.currentThread()) {
            try{
                Thread.sleep(delay);
            }catch(InterruptedException ie){}

            listener.tick();
        }
        Log.println(this,"Timer stopped");
    }
}