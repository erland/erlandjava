package erland.util;

/**
 * Timer class that calls your object at regular intervals
 */
public class Timer implements Runnable
{
    /** Reference to object to call at each interval */
    protected TimerListenerInterface listener;
    /** Number of milliseconds between two ping calls */
    protected long delay;
    /** Indicates that the timer should be stopped */
    protected boolean bQuit = false;
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
        t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
    }

    /**
     * Checks if the timer is running
     */
    public boolean isRunning()
    {
        return t.isAlive() && !bQuit;
    }
    /**
     * Stops the timer
     */
    public void stop()
    {
        bQuit = true;
    }

    /**
     * Starts the timer after it has been stopped
     */
    public void start()
    {
        bQuit = false;
        t.start();
    }
    /**
     * Sets the delay between timer ticks
     * @param delay Number of milliseconds between each timer tick
     */
    public void setDelay(int delay)
    {
        this.delay = delay;
    }
    /**
     * Main loop
     */
    public void run()
    {
        while(!bQuit) {
            try{
                Thread.sleep(delay);
            }catch(InterruptedException ie){}

            listener.tick();
        }
    }
}