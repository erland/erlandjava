package erland.util;

/**
 * Specifies the interface to implement when you want regular ping calls
 * @see Timer
 * @author Erland Isaksson
 */
public interface TimerListenerInterface {
    /*
     * Timer tick signal, will be called when timer exipres
     */
    public void tick();
}
