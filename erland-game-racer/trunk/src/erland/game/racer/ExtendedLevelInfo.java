package erland.game.racer;

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;

/**
 * Contains additional information about the track
 * For example information about the position and direction of the start grid
 */
public class ExtendedLevelInfo implements ParameterSerializable {
    /** The direction of the start grid of the track */
    protected int startDir;
    /** The x position of the start grid of the track */
    protected int startPosX;
    /** THe y position of the start grid of the track */
    protected int startPosY;

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("startdir",String.valueOf(startDir));
        out.setParameter("startposx",String.valueOf(startPosX));
        out.setParameter("startposy",String.valueOf(startPosY));
    }

    public void read(ParameterValueStorageExInterface in) {
        try {
            startDir = Integer.valueOf(in.getParameter("startdir")).intValue();
            startPosX = Integer.valueOf(in.getParameter("startposx")).intValue();
            startPosY = Integer.valueOf(in.getParameter("startposy")).intValue();
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Set the X block coordinate of the start grid of the track
     * @param x The X block coordinate of the start grid of the track
     */
    public void setStartPosX(int x)
    {
        startPosX = x;
    }
    /**
     * Set the Y block coordinate of the start grid of the track
     * @param y The Y block coordinate of the start grid of the track
     */
    public void setStartPosY(int y)
    {
        startPosY = y;
    }
    /**
     * Set the direction of the start grid of the track
     * @param direction The direction of the start grid of the track
     */
    public void setStartDirection(int direction)
    {
        startDir = direction;
    }
    /**
     * Get the X block coordinate of the start grid of the track
     * @return The X block coordinate of the start grid of the track
     */
    public int getStartPosX()
    {
        return startPosX;
    }
    /**
     * Get the Y block coordinate of the start grid of the track
     * @return The Y block coordinate of the start grid of the track
     */
    public int getStartPosY()
    {
        return startPosY;
    }
    /**
     * Get the direction of the start grid of the track
     * @return The direction of the start grid of the track
     */
    public int getStartDirection()
    {
        return startDir;
    }
}
