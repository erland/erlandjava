/*
 * Specifies the interface to implement when you want regular ping calls
 */
package erland.util;

public interface TimerListenerInterface {
    /*
     * Timer tick signal
     */
    public void tick();
}
